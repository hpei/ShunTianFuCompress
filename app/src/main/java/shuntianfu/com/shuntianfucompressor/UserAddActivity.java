package shuntianfu.com.shuntianfucompressor;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import business.LoginManager;
import common.Constants;
import ui.ClearEditText;
import utils.CustomToast;
import utils.DataHandler;
import utils.ViewUtil;

public class UserAddActivity extends AppCompatActivity implements View.OnClickListener, ClearEditText.ChecTextkNull {

	private LinearLayout back;
	private TextView titleCenter;
	private ClearEditText addName;
	private ClearEditText addPwd;
	private ClearEditText addPwdConfirm;
	private CheckBox checkBox;

	private Button addBtn;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_add);
		context = this;
		initView();
	}

	private void initView() {
		back = (LinearLayout) findViewById(R.id.common_title_left);
		back.setVisibility(View.VISIBLE);
		titleCenter = (TextView) findViewById(R.id.common_title_center);
		titleCenter.setText("添加用户");
		addName = (ClearEditText) findViewById(R.id.user_add_name);
//		addName.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				checkBox.setText("添加" + s.toString() + "为管理员");
//			}
//		});
		addPwd = (ClearEditText) findViewById(R.id.user_add_pwd);
		addPwdConfirm = (ClearEditText) findViewById(R.id.user_add_pwd_confirm);
		checkBox = (CheckBox) findViewById(R.id.user_add_check);
		addBtn = (Button) findViewById(R.id.user_add_btn);

		addName.setChecTextkNull(this);
		addPwd.setChecTextkNull(this);
		addPwdConfirm.setChecTextkNull(this);
		addBtn.setOnClickListener(this);
		back.setOnClickListener(this);

		addBtn.setEnabled(false);

		//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
				ViewUtil.showSoftInput(UserAddActivity.this, addName);
			}
		}, 200);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.common_title_left: {
				finish();
				overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
				break;
			}
			case R.id.user_add_btn: {
				final String name = addName.getText().toString().trim();
				final String pwd = addPwd.getText().toString().trim();
				final String pwdConfirm = addPwdConfirm.getText().toString().trim();
				final int role = checkBox.isChecked() ? 2 : 1;
				String roleLevel = role == 1 ? "普通" : "管理";

				//联网请求 完成后自动关闭
				if (!TextUtils.equals(pwd, pwdConfirm)) {
					CustomToast.showToast(this, "两次输入密码不同", 2000);
					return;
				}
				if (pwdConfirm.length() < 6) {
					CustomToast.showToast(this, "密码最少6位", 2000);
					return;
				}
				if (pwdConfirm.length() > 16) {
					CustomToast.showToast(this, "密码最长16位", 2000);
					return;
				}
				if (!ModifyPasswordActivity.checkPassword(pwdConfirm)) {
					CustomToast.showToast(this, "密码不符合规范", 2000);
					return;
				}

				add(name, pwdConfirm, role, roleLevel);

				break;
			}
			default:
				break;
		}
	}

	private void add(final String name, final String pwd, final int role, String roleLevel) {

		new AlertDialog.Builder(context).setMessage("确定添加 " + roleLevel + "用户 " + name + "？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						LoginManager.getInstance().userAdd(context, name, pwd, role, new DataHandler() {
							@Override
							public void onData(int code, String str, Object obj) {
								if (code == Constants.SERVER_SUCCESS) {
									continueDialog(name);
								} else {
									CustomToast.showToast(context, "添加失败，请您重试", 2000);
								}
							}
						});
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create().show();


	}

	private void continueDialog(String name) {
		new AlertDialog.Builder(context).setMessage("添加 " + name + " 成功 是否继续添加新用户？")
				.setPositiveButton("继续添加", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						addName.getText().clear();
						addPwd.getText().clear();
						addPwdConfirm.getText().clear();
						checkBox.setChecked(false);

						addName.setFocusable(true);
						addName.requestFocus();
						//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								//可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘
								ViewUtil.showSoftInput(UserAddActivity.this, addName);
							}
						}, 200);

					}
				})
				.setNegativeButton("不了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				})
				.create().show();
	}


	@Override
	public void checkNull() {
		if (!addName.isNull() && !addPwd.isNull() && !addPwdConfirm.isNull()) {
			addBtn.setEnabled(true);
		} else {
			addBtn.setEnabled(false);
		}
	}
}
