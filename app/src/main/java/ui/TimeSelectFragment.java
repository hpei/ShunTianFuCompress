package ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import shuntianfu.com.shuntianfucompressor.R;

public class TimeSelectFragment extends DialogFragment {
	private MyWheelView pickerView;

	private List<String> list;

	private MyWheelView.OnWheelSelectListener callback;

	private int index;

	private String currentTime;

	private TextView centerTip;

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (MyWheelView.OnWheelSelectListener) getTargetFragment();
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.time_picker_sth, container, false);
		pickerView = (MyWheelView) view.findViewById(R.id.data_picker_view);
		centerTip = (TextView) view.findViewById(R.id.btn_center);
//		centerTip.setText(currentTime);

		pickerView.setOffset(2);
		pickerView.setItems(list);

		if (currentTime != null) {
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				if (TextUtils.equals(list.get(i), currentTime)) {
					pickerView.setSeletion(i);
					break;
				}
				count++;
			}
			if (count == list.size()) {
				pickerView.setSeletion(0);
			}
		} else {
			pickerView.setSeletion(0);
		}
		pickerView.setOnWheelViewListener(selectListener);

		view.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		view.findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onSelected(index, currentTime);
				dismiss();
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	MyWheelView.OnWheelSelectListener selectListener = new MyWheelView.OnWheelSelectListener() {
		@Override
		public void onSelected(int selectedIndex, String item) {
			index = selectedIndex;
			currentTime = item;
			//动态改变选中的属性
//			centerTip.setText(currentTime);
		}
	};

	@Override
	public void onDetach() {
		super.onDetach();
		callback = null;
	}
}