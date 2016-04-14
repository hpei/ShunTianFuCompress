package shuntianfu.com.shuntianfucompressor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;

import business.CompressorDataManager;
import business.LoginManager;
import common.AppContext;
import common.Constants;
import model.Threshold;
import model.User;
import utils.CustomToast;
import utils.DataHandler;
import utils.JSONUtils;
import utils.Log;
import utils.SharedPreferencesUtils;

public class WelcomeActivity extends AppCompatActivity {

	private static final String TAG = "WelcomeActivity";

	final long startTime = System.currentTimeMillis();

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case Constants.AUTHSUCESS: {

					Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);

					WelcomeActivity.this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
					finish();

					break;
				}
				case Constants.AUTHERRO: {
					startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
					WelcomeActivity.this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

					finish();
					break;
				}
				default: {
					break;
				}
			}
		}
	};
	private DataHandler loginDataHandler = new DataHandler() {
		@Override
		public void onData(int code, String msg, Object obj) {
			Log.d(TAG, "code=" + code);
			if (code == Constants.SERVER_SUCCESS) {
				long diffTime = System.currentTimeMillis() - startTime;
				long delayedTime = diffTime > 2000 ? 0 : 2000 - diffTime;
				Log.d(TAG, "delayedTime==" + delayedTime);

				Message msg1 = Message.obtain();
				msg1.what = Constants.AUTHSUCESS;

				handler.sendMessageDelayed(msg1, delayedTime);

				User user = JSONUtils.json2Bean(obj.toString(), User.class);
				SharedPreferencesUtils.saveString(WelcomeActivity.this, Constants.USER_TOKEN, user.getToken());
				//判断是否是管理员
				if (2 == user.getRole()) {
					AppContext.getInstance().setAdmin(true);
//						CustomToast.showToast(context, "2", 2000);
				}
			} else {
				long diffTime = System.currentTimeMillis() - startTime;
				long delayedTime = diffTime > 2000 ? 0 : 2000 - diffTime;
				Message msg1 = Message.obtain();
				msg1.what = Constants.AUTHERRO;
				handler.sendMessageDelayed(msg1, delayedTime);
				//清空密码
				SharedPreferencesUtils.saveString(WelcomeActivity.this, Constants.USER_PASSWORD, null);

				Log.d(TAG, "自动登录失败 ");
//                CustomToast.showToast(WelcomeActivity.this, "登录失败", 2000);
			}
		}
	};
	private DataHandler faZhiDataHandler = new DataHandler() {
		@Override
		public void onData(int code, String msg, Object obj) {
			Log.d(TAG, "code=" + code);
			if (code == Constants.SERVER_SUCCESS) {

				AppContext.getInstance().setGetFazhi(true);
				Threshold threshold = JSONUtils.json2Bean(obj.toString(), Threshold.class);
				Log.d(TAG, "threshold=" + threshold.toString());

				Message message = Message.obtain();
				message.what = Constants.GETFAZHISUCESS;

				message.obj = threshold;

				if (null != AppContext.getInstance().getGetFazhiHandler()) {

					AppContext.getInstance().getGetFazhiHandler().sendMessage(message);
				}


			} else {

				AppContext.getInstance().setGetFazhi(false);
				Log.d(TAG, "阀值请求失败 ");
//                CustomToast.showToast(WelcomeActivity.this, "登录失败", 2000);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);


		//请求设置阀值
		requestFaZhiAndSet();

		//自动登录
		authLogin();

	}

	private void requestFaZhiAndSet() {

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch (msg.what) {

					case Constants.GETFAZHISUCESS: {

						Threshold threshold = (Threshold) msg.obj;

						if (null != AppContext.getInstance().getListener()) {
							AppContext.getInstance().getListener().onSucess(threshold);
						}
						break;
					}
				}

			}
		};

		AppContext.getInstance().setGetFazhiHandler(handler);

		CompressorDataManager.getInstance().requstFazhi(this, faZhiDataHandler);

	}

	private void authLogin() {
		//检查Sp是否存储有登录信息
		String userName = SharedPreferencesUtils.getString(this, Constants.USER_NAME, null);
		String userPassword = SharedPreferencesUtils.getString(this, Constants.USER_PASSWORD, null);
		String userToken = SharedPreferencesUtils.getString(this, Constants.USER_TOKEN, null);

		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)
				|| TextUtils.isEmpty(userToken)) {
			//进入登录界面
			long diffTime = System.currentTimeMillis() - startTime;
			long delayedTime = diffTime > 2000 ? 0 : 2000 - diffTime;
			Log.d(TAG, "delayedTime==" + delayedTime);
			Message msg1 = Message.obtain();
			msg1.what = Constants.AUTHERRO;
			handler.sendMessageDelayed(msg1, delayedTime);

		} else {
			//认证登录

			LoginManager.getInstance().login(this, userName, userPassword, loginDataHandler);


		}


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}
}
