package business;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import common.Constants;
import model.CompressorData;
import model.Threshold;
import shuntianfu.com.shuntianfucompressor.R;
import utils.CheckNetUtil;
import utils.DataHandler;
import utils.HttpRequestUtil;
import utils.HttpServiceHandler;
import utils.SharedPreferencesUtils;

/**
 * Created by haopei on 2016/3/31.
 */
public class CompressorDataManager {

	private static final String TAG = "CompressorDataManager";

	private CompressorDataManager() {
	}

	static class CompreesorDatamanagerHolder {

		static CompressorDataManager compressorDataManager = new CompressorDataManager();
	}

	public static CompressorDataManager getInstance() {
		return CompreesorDatamanagerHolder.compressorDataManager;
	}


	public void requstFazhi(final Context context, final DataHandler dataHandler) {
		if (!CheckNetUtil.checkNetWork(context)) {
			if (dataHandler != null) {
				dataHandler.onData(Constants.Error.NETWORK_IS_UNREACHABLE, context.getString(R.string.error_net_unconnect), null);
			}
			return;
		}


		String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
		Log.i(TAG, "token==" + token);
		String url = Constants.SERVER_HOST + Constants.QUERY_THRESHOLD + "?token=" + token;

		HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);

	}

	/**
	 * 更新阀值
	 *
	 * @param context
	 * @param threshold
	 * @param dataHandler
	 */
	public void updateFazhi(final Context context, Threshold threshold, final DataHandler dataHandler) {


		if (!CheckNetUtil.checkNetWork(context)) {
			if (dataHandler != null) {
				dataHandler.onData(Constants.Error.NETWORK_IS_UNREACHABLE, context.getString(R.string.error_net_unconnect), null);
			}
			return;
		}


		HttpServiceHandler httpServiceHandler = new HttpServiceHandler(dataHandler) {


			@Override
			public void onResponse(int code, String reason, Object msgData) {
				if (code == Constants.SERVER_SUCCESS) {
					Log.d(TAG, "msgData="+msgData.toString());
					dataHandler.onData(code, reason, msgData);
				} else {
					if (null != getDataHandler()) {
						getDataHandler().onData(code, reason, msgData);
					}
				}
			}

			@Override
			public void onError(int code, String reason, Throwable e) {
				super.onError(code, reason, e);
				if (null != getDataHandler()) {
					getDataHandler().onData(code, reason, null);
				}
			}
		};

		String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
		android.util.Log.i(TAG, "token==" + token);
		RequestParams params = new RequestParams();
		params.put("dianyaUpper", threshold.getDianyaUpper());
		params.put("dianliuUpper", threshold.getDianliuUpper());
		params.put("wenduUpper", threshold.getWenduUpper());
		params.put("gaoyaUpper", threshold.getGaoyaUpper());
		params.put("diyaUpper", threshold.getDiyaUpper());

		params.put("dianyaLower", threshold.getDianyaLower());
		params.put("dianliuLower", threshold.getDianliuLower());
		params.put("wenduLower", threshold.getWenduLower());
		params.put("gaoyaLower", threshold.getGaoyaLower());
		params.put("diyaLower", threshold.getDiyaLower());
		params.put("token", token);

		HttpRequestUtil.sendFormRequestAsync(Constants.SERVER_HOST
				+ Constants.UPDATE_THRESHOLD, params, context, httpServiceHandler);






//		String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
//		Log.i(TAG, "token==" + token);
//		Log.d(TAG, "请求 threshold=" + threshold.toString());
//		String url = Constants.SERVER_HOST + Constants.UPDATE_THRESHOLD
//				+ "?dianyaThreshold=" + threshold.getDianyaThreshold()
//				+ "&dianliuThreshold=" + threshold.getDianliuThreshold()
//				+ "&wenduThreshold=" + threshold.getWenduThreshold()
//				+ "&gaoyaThreshold=" + threshold.getGaoyaThreshold()
//				+ "&diyaThreshold=" + threshold.getDiyaThreshold()
//				+ "&token="+token;
//
//		HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);

	}
}
