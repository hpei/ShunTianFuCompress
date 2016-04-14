package business;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import common.AppContext;
import common.Constants;
import model.Compressor;
import model.CompressorData;
import shuntianfu.com.shuntianfucompressor.R;
import utils.CheckNetUtil;
import utils.DataHandler;
import utils.HttpRequestUtil;
import utils.HttpServiceHandler;
import utils.JSONUtils;
import utils.Log;
import utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2016/4/1.
 */
public class MainManager {
	private final String TAG = "MainManager";

	private MainManager() {
	}

	static class MainManagerHolder {
		static MainManager mainManager = new MainManager();
	}

	public static MainManager getInstance() {
		return MainManagerHolder.mainManager;
	}

	public void getCompressorList(final Context context, final DataHandler dataHandler) {
		if (!CheckNetUtil.checkNetWork(context)) {
			if (dataHandler != null) {
				dataHandler.onData(Constants.Error.NETWORK_IS_UNREACHABLE, context.getString(R.string.error_net_unconnect), null);
			}
			return;
		}
		String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
		android.util.Log.i(TAG, "token==" + token);
		String url = Constants.SERVER_HOST + Constants.COMPRESSOR_LIST + "?token=" + token;
		HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);

	}

	/**
	 * @param context
	 * @param dataHandler startTime=1459334211297&endTime=1459335116745&compressorName=压缩机1
	 */
	public void realTimeList(final Context context, long startTime, long endTime, String compressorName, final DataHandler dataHandler) {
		android.util.Log.d(TAG, "startTime==" + startTime + "end==" + endTime + "compressorName=" + compressorName);
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
					Type listType = new TypeToken<LinkedList<CompressorData>>() {
					}.getType();
					Gson gson = new Gson();
					List<CompressorData> compressorDatas = gson.fromJson(msgData.toString(), listType);
					android.util.Log.d(TAG, compressorDatas.toString());
					dataHandler.onData(code, reason, compressorDatas);
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


//        String url = null;
		try {
//            url = Constants.SERVER_HOST + Constants.REAL_TIME_LIST + "?startTime="
//                    + startTime + "&endTime=" + endTime + "&compressorName=" + URLEncoder.encode(compressorName, "UTF-8");
//            android.util.Log.e(TAG, "url==" + url);
			String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
			android.util.Log.i(TAG, "token==" + token);
			RequestParams params = new RequestParams();
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("compressorName", compressorName);
			params.put("token", token);

			HttpRequestUtil.sendFormRequestAsync(Constants.SERVER_HOST + Constants.REAL_TIME_LIST, params, context, httpServiceHandler);
//            HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void getAlarm(final Context context, final DataHandler dataHandler) {
		if (!CheckNetUtil.checkNetWork(context)) {
			if (dataHandler != null) {
				dataHandler.onData(Constants.Error.NETWORK_IS_UNREACHABLE, context.getString(R.string.error_net_unconnect), null);
			}
			return;
		}
		String token = SharedPreferencesUtils.getString(context, Constants.USER_TOKEN, "");
		android.util.Log.i(TAG, "token==" + token);
		String url = Constants.SERVER_HOST + Constants.GET_ALARM_FAZHI + "?token=" + token;
		HttpRequestUtil.sendSimpleRequestAsyncGet(url, context, dataHandler);

	}
}
