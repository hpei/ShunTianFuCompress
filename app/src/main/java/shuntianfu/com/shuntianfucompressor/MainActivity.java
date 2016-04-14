package shuntianfu.com.shuntianfucompressor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.CompressorDataManager;
import business.MainManager;
import common.AppContext;
import common.Constants;
import model.Compressor;
import model.Threshold;
import service.MyServiceOne;
import ui.NoScrollViewPager;
import utils.AlarmUtil;
import utils.CustomToast;
import utils.DataHandler;
import utils.JSONUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = "MainActivity";

	private IndicatorViewPager indicatorViewPager;
	private LayoutInflater inflate;
	private ScrollIndicatorView indicator;
	private Context context;

	private LinkedList<Compressor> compressorList = new LinkedList<>();

	private LinearLayout settingLinear;
	private LinearLayout refresh;
	private List<String> compressorNames = new ArrayList<>();//存储获取到的所有压缩机的名称

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//            super.handleMessage(msg);

			switch (msg.what) {
				case Constants.GETCPListSUCESS: {
					initIndicatorData();
					//显示第一个的电压数据
					break;
				}
				default: {
					break;
				}

			}

		}
	};

	private void initIndicatorData() {

		if (compressorNames.size() != 0) {
			compressorNames.clear();
		}

		for (int i = 0; i < compressorList.size(); i++) {
			if (null != compressorList.get(i).getCompressorName()) {
				compressorNames.add(compressorList.get(i).getCompressorName());
			} else {
				compressorNames.add("未知");
			}
		}
		indicatorViewPager.getAdapter().notifyDataSetChanged();
	}

	private DataHandler compressorListDataHandler = new DataHandler() {
		@Override
		public void onData(int code, String msg, Object obj) {
			utils.Log.d(TAG, "code=" + code);
			if (code == Constants.SERVER_SUCCESS) {

				Type listType = new TypeToken<LinkedList<Compressor>>() {
				}.getType();
				Gson gson = new Gson();

				if (compressorList.size() != 0) {
					compressorList.clear();
				}
				compressorList = gson.fromJson(obj.toString(), listType);

				Log.d(TAG, compressorList.toString());

				handler.sendEmptyMessage(Constants.GETCPListSUCESS);


			} else {
				CustomToast.showToast(context, "压缩机列表请求失败，刷新试试", 2000);
			}

		}
	};
	private DataHandler faZhiDataHandler = new DataHandler() {
		@Override
		public void onData(int code, String msg, Object obj) {
			utils.Log.d(TAG, "code=" + code);
			if (code == Constants.SERVER_SUCCESS) {

				AppContext.getInstance().setGetFazhi(true);
				Threshold threshold = JSONUtils.json2Bean(obj.toString(), Threshold.class);
				utils.Log.d(TAG, "threshold=" + threshold.toString());

				Message message = Message.obtain();
				message.what = Constants.GETFAZHISUCESS;

				message.obj = threshold;

				if (null != AppContext.getInstance().getGetFazhiHandler()) {
					AppContext.getInstance().getGetFazhiHandler().sendMessage(message);
				}

			} else {

				AppContext.getInstance().setGetFazhi(false);
				utils.Log.d(TAG, "阀值请求失败 ");
				CustomToast.showToast(context, "阀值数据获取失败", 1000);
//                CustomToast.showToast(WelcomeActivity.this, "登录失败", 2000);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		settingLinear = (LinearLayout) findViewById(R.id.title_setting_layout);
		refresh = (LinearLayout) findViewById(R.id.title_refresh);
		settingLinear.setOnClickListener(this);
		refresh.setOnClickListener(this);

		try {

			startService(new Intent(context, MyServiceOne.class));
			if (!AppContext.getInstance().isGetFazhi()) {

				CompressorDataManager.getInstance().requstFazhi(this, faZhiDataHandler);
			}

			getCompressorList();

			initViewpagerIndicator();

			Log.e(TAG, compressorList.toString());
		} catch (Exception ex) {
			Log.e(TAG, ex.toString());
		}


//        AlarmUtil.runAlarm(context, R.raw.a);

	}

	/**
	 * 获取压缩机列表
	 */
	private void getCompressorList() {
		MainManager.getInstance().getCompressorList(context, compressorListDataHandler);
	}

	private void initViewpagerIndicator() {

		NoScrollViewPager viewPager = (NoScrollViewPager) findViewById(R.id.moretab_viewPager);
		indicator = (ScrollIndicatorView) findViewById(R.id.moretab_indicator);
		indicator.setScrollBar(new ColorBar(this, Color.RED, 5));

		indicator.setSplitAuto(true);

		// 设置滚动监听
		int selectColorId = R.color.tab_top_text_2;
		int unSelectColorId = R.color.tab_top_text_1;
		indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));

		viewPager.setOffscreenPageLimit(2);
		indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
		inflate = LayoutInflater.from(getApplicationContext());
		indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

		indicatorViewPager.setOnIndicatorPageChangeListener(pageChangeListener);
	}

	IndicatorViewPager.OnIndicatorPageChangeListener pageChangeListener = new IndicatorViewPager.OnIndicatorPageChangeListener() {
		@Override
		public void onIndicatorPageChange(int preItem, int currentItem) {
//            Toast.makeText(context, "currtItem" + currentItem, Toast.LENGTH_LONG).show();

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.title_setting_layout:
				gotoSetting();
				break;
			case R.id.title_refresh:
				refresh();
				break;
			default:
				break;
		}
	}

	/**
	 * 刷新
	 */
	private void refresh() {
		getCompressorList();
	}

	private void gotoSetting() {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);

		this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}


	private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public int getCount() {
			return compressorNames.size() == 0 ? 0 : compressorNames.size();
		}

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {
			if (convertView == null) {
				convertView = inflate.inflate(R.layout.tab_top, container, false);
			}
			TextView textView = (TextView) convertView;
			textView.setText(compressorNames.get(position));
			textView.setPadding(20, 0, 20, 0);
			return convertView;
		}

		@Override
		public Fragment getFragmentForPage(int position) {
			CompressorLineFragment fragment = new CompressorLineFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(CompressorLineFragment.INTENT_INT_INDEX, position);
			bundle.putParcelable(Constants.NOWCOMPRESSOR, compressorList.get(position));
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getItemPosition(Object object) {
			return FragmentListPageAdapter.POSITION_NONE;
		}

	}

	private long lastBackKeyTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - lastBackKeyTime) > 2000) {
				Log.e("TAG", "System.currentTimeMillis()==" + System.currentTimeMillis() + "lastBackKeyTime==" + lastBackKeyTime);
				Log.e("TAG", "System.currentTimeMillis() - lastBackKeyTime" + (System.currentTimeMillis() - lastBackKeyTime));
				CustomToast.showToast(context, "再按一次退出应用", 1000);
				lastBackKeyTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}


		return super.onKeyDown(keyCode, event);
	}
}
