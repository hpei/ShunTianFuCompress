package shuntianfu.com.shuntianfucompressor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import ui.ClearEditText;
import utils.CustomToast;
import utils.ViewUtil;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener, ClearEditText.ChecTextkNull {

	private LinearLayout back;
	private ClearEditText oldPassword;
	private ClearEditText newPassword;
	private ClearEditText againNewPassword;

	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_password);

		initView();

	}

	private void initView() {
		back = (LinearLayout) findViewById(R.id.common_title_left);
		back.setVisibility(View.VISIBLE);
		oldPassword = (ClearEditText) findViewById(R.id.setting_old_password);
		newPassword = (ClearEditText) findViewById(R.id.setting_new_password);
		againNewPassword = (ClearEditText) findViewById(R.id.setting_again_password);
		submit = (Button) findViewById(R.id.btn_submit_new_password);

		submit.setEnabled(false);

		oldPassword.setChecTextkNull(this);
		newPassword.setChecTextkNull(this);
		againNewPassword.setChecTextkNull(this);
		back.setOnClickListener(this);
		submit.setOnClickListener(this);

		//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
				ViewUtil.showSoftInput(ModifyPasswordActivity.this, oldPassword);
			}
		}, 200);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.common_title_left:
				finish();
				overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
				break;
			case R.id.btn_submit_new_password:
				submit();
				break;
			default:
				break;
		}
	}

	private void submit() {
		//联网请求 完成后自动关闭
		if (!TextUtils.equals(newPassword.getText().toString(), againNewPassword.getText().toString())) {
			CustomToast.showToast(this, "两次输入密码不同", 2000);
			return;
		}
		if (newPassword.getText().toString().trim().length() < 6) {
			CustomToast.showToast(this, "密码最少6位", 2000);
			return;
		}
		if (newPassword.getText().toString().trim().length() > 16) {
			CustomToast.showToast(this, "密码最长16位", 2000);
			return;
		}
		if (!checkPassword(newPassword.getText().toString().trim())) {
			CustomToast.showToast(this, "密码不符合规范", 2000);
			return;
		}



		finish();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}

	@Override
	public void checkNull() {
		if (!oldPassword.isNull() && !newPassword.isNull() && !againNewPassword.isNull()) {
			submit.setEnabled(true);
		} else {
			submit.setEnabled(false);
		}
	}


	public static boolean checkPassword(String strPassword) {
		if (strPassword != null) {
			Pattern pattern = Pattern
					.compile("^([A-Za-z0-9!@#$%^&*.~/{}|()'\\\"?><,.`+-=_:;\\\\\\\\[]]\\\\\\\\\\\\\\[]{6,16})$");
			return pattern
					.matcher(strPassword).matches();
		}
		return false;
	}
}
