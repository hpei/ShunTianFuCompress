package shuntianfu.com.shuntianfucompressor;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import business.LoginManager;
import business.MainManager;
import common.AppContext;
import common.Constants;
import model.User;
import ui.ClearEditText;
import utils.CustomToast;
import utils.DataHandler;
import utils.JSONUtils;
import utils.Log;
import utils.SharedPreferencesUtils;
import utils.ViewUtil;

/**
 * Created by xinqi on 2016/3/30
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ClearEditText.ChecTextkNull {
	private String TAG = "LoginActivity";
	private LinearLayout back;
	private TextView titleCenter;

	private Context context;

	private ClearEditText loginName;
	private ClearEditText loginPwd;
	private Button loginBtn;

	private String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		context = this;

		userName = SharedPreferencesUtils.getString(context, Constants.USER_NAME, null);
		initView();
	}

	private void initView() {
		back = (LinearLayout) findViewById(R.id.common_title_left);
		back.setVisibility(View.GONE);
		titleCenter = (TextView) findViewById(R.id.common_title_center);
		titleCenter.setText("登录");
		loginName = (ClearEditText) findViewById(R.id.user_login_name);
		loginPwd = (ClearEditText) findViewById(R.id.user_login_pwd);
		loginBtn = (Button) findViewById(R.id.user_login_btn);

		if (null != userName) {
			loginName.setText(userName);
			loginPwd.setFocusable(true);
			loginPwd.requestFocus();
			//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
					ViewUtil.showSoftInput(LoginActivity.this, loginPwd);
				}
			}, 200);
		}

		loginName.setChecTextkNull(this);
		loginPwd.setChecTextkNull(this);
		loginBtn.setOnClickListener(this);
		back.setOnClickListener(this);


		loginBtn.setEnabled(false);



//		NotificationManager manger = (NotificationManager)
//				getSystemService(Context.NOTIFICATION_SERVICE);
//		Notification notification = new Notification();
//
//		notification.sound= Uri.parse("android.resource://"
//				+ getPackageName() + "/" +R.raw.alarm);
//
//		notification.defaults=Notification.DEFAULT_SOUND;
//		notification.flags = Notification.FLAG_INSISTENT;
//		manger.notify(1, notification);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.common_title_left: {

				finish();
				overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);


				break;
			}
			case R.id.user_login_btn: {
				login();


				break;
			}
			default:
				break;
		}
	}

	private void login() {


		final String name = loginName.getText().toString().trim();
		final String pwd = loginPwd.getText().toString().trim();

		LoginManager.getInstance().login(context, name, pwd, new DataHandler() {
			@Override
			public void onData(int code, String str, Object obj) {
				Log.d(TAG, "code=" + code);
				if (code == Constants.SERVER_SUCCESS) {
					//存储登录成功的用户名 、密码
					SharedPreferencesUtils.saveString(context, Constants.USER_NAME, name);
					SharedPreferencesUtils.saveString(context, Constants.USER_PASSWORD, pwd);

					User user = JSONUtils.json2Bean(obj.toString(), User.class);
					SharedPreferencesUtils.saveString(context, Constants.USER_TOKEN, user.getToken());

					//判断是否是管理员
					if (2 == user.getRole()) {
						AppContext.getInstance().setAdmin(true);
//						CustomToast.showToast(context, "2", 2000);
					}

					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
					finish();
				} else {
					CustomToast.showToast(context, "登录失败", 2000);
				}

			}
		});


	}

	@Override
	public void checkNull() {
		if (!loginName.isNull() && !loginPwd.isNull()) {
			loginBtn.setEnabled(true);
		} else {
			loginBtn.setEnabled(false);
		}
	}

	private long lastBackKeyTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - lastBackKeyTime) > 2000) {
				android.util.Log.e("TAG", "System.currentTimeMillis()==" + System.currentTimeMillis() + "lastBackKeyTime==" + lastBackKeyTime);
				android.util.Log.e("TAG", "System.currentTimeMillis() - lastBackKeyTime" + (System.currentTimeMillis() - lastBackKeyTime));
				CustomToast.showToast(context, "再按一次退出应用", 1000);
				lastBackKeyTime = System.currentTimeMillis();
			} else {
				finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
			}
			return true;
		}


		return super.onKeyDown(keyCode, event);
	}
}
