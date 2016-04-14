package business;

import android.content.Context;
import android.content.Intent;

import common.AppContext;
import common.Constants;
import shuntianfu.com.shuntianfucompressor.LoginActivity;
import shuntianfu.com.shuntianfucompressor.R;
import utils.CheckNetUtil;
import utils.DataHandler;
import utils.HttpRequestUtil;
import utils.HttpServiceHandler;
import utils.Log;
import utils.SharedPreferencesUtils;

/**
 * Created by xinqi on 2016/3/30
 */
public class LoginManager {
    private final String TAG = "LoginManager";

    private LoginManager() {
    }

    static class LoginManagerHolder {
        static LoginManager loginManager = new LoginManager();
    }

    public static LoginManager getInstance() {
        return LoginManagerHolder.loginManager;
    }

    public void login(final Context context, String name, String pwd, final DataHandler dataHandler) {
        if (!CheckNetUtil.checkNetWork(context)) {
            if (dataHandler != null) {
                dataHandler.onData(Constants.Error.NETWORK_IS_UNREACHABLE, context.getString(R.string.error_net_unconnect), null);
            }
            return;
        }
        // TODO: 2016/3/30
        final HttpServiceHandler httpServiceHandler = new HttpServiceHandler(dataHandler) {
            @Override
            public void onResponse(int code, String reason, Object msgData) {
				if (code == Constants.SERVER_SUCCESS) {
					try {
						if (null != dataHandler) {
							Log.d(TAG, "code=" + code + "reason=" + reason);
							if (msgData != null) {
								Log.d(TAG, "msgData=" + msgData);

							}
//                    JSONObject newMsgData = JSONUtils.changeKey(msgData);
//                    cartDetail = JSONUtils.json2Bean(newMsgData.toString(), CartDetail.class);
//                    dataHandler.onData(code, reason, cartDetail);
//
						}

					} catch (Exception e) {
//                logger.error(e.toString(), e);
					}
				} else {
					if (null != dataHandler) {
//                dataHandler.onData(code, reason, cartDetail);
					}
				}
            }

            @Override
            public void onError(int code, String reason, Throwable e) {
                super.onError(code, reason, e);
                Log.e(TAG, "onError code=" + String.valueOf(code) + ",reason=" + reason);
                if (null != getDataHandler()) {
                    getDataHandler().onData(code, reason, null);
                }
            }
        };

//        HttpRequestUtil.sendFormRequestAsync(Constants.SERVER_HOST, params, context, httpServiceHandler);

        String url = Constants.SERVER_HOST + Constants.USER_LOGIN
                + "?userName=" + name + "&password=" + pwd;

        Log.e(TAG,url);
        HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);

    }


    public void userAdd(final Context context, String name, String pwd, int role,  final DataHandler dataHandler) {
        if (!CheckNetUtil.checkNetWork(context)) {
            if (dataHandler != null) {
                dataHandler.onData(Constants.Error.NETWORK_IS_UNREACHABLE, context.getString(R.string.error_net_unconnect), null);
            }
            return;
        }
		String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
		android.util.Log.i(TAG, "token==" + token);
        String url = Constants.SERVER_HOST + Constants.USER_ADD
                +"?userName="+name+"&password="+pwd+"&role="+role+"&token="+token;
        HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);

    }


    public void clearLoginInfo(){
        AppContext context = AppContext.getInstance();
        SharedPreferencesUtils.saveString(context, Constants.USER_PASSWORD, "");
        SharedPreferencesUtils.saveString(context, Constants.USER_TOKEN, "");

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
