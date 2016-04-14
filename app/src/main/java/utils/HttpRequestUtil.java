package utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import business.LoginManager;
import common.AppContext;
import common.Constants;

public class HttpRequestUtil {

    private static final String TAG = "HttpRequestUtil";

    public static void sendFormRequestAsyncGet(String url, RequestParams params, Context context, final HttpServiceHandler httpHandler) {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
            BasicClientCookie newCookie = new BasicClientCookie("myCookieStore", "awesome");
            newCookie.setAttribute("", "");
            client.setCookieStore(myCookieStore);
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            client.addHeader("Accept-LANGUAGE", Constants.LANGUAGE);
            if (null != params) {
                client.get(context, url, params, getAsyncResponseHandler(httpHandler));
            } else {
                client.get(url, getAsyncResponseHandler(httpHandler));
            }

        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }

    }

    public static void sendFormRequestAsync(String url, RequestParams params, Context context, final HttpServiceHandler httpHandler) {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            params.setContentEncoding("UTF-8");
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            client.addHeader("Accept-LANGUAGE", Constants.LANGUAGE);
//			client.setEnableRedirects(true);

            if (null != params) {
                client.post(context, url, params, getAsyncResponseHandler(httpHandler));
            } else {
                client.post(url, getAsyncResponseHandler(httpHandler));
            }

        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }

    }

    /**
     * 异步应用级参数加密、返回数据不解密
     *
     * @param url
     * @param params
     * @param context
     * @param httpHandler
     */
    public static void sendFormRequestAsyncEncryptedResponseNoDecrypted(String url, RequestParams params, Context context, final HttpServiceHandler httpHandler) {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            client.addHeader("Accept-LANGUAGE", Constants.LANGUAGE);

            if (null != params) {
                client.post(context, url, params, getAsyncResponseHandler(httpHandler));
            } else {
                client.post(url, getAsyncResponseHandler(httpHandler));
            }

        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }

    }

    public static ResultData sendFormRequestSync(String url, RequestParams params) {
        ResultData map = new ResultData();
        params.setContentEncoding("UTF-8");
        try {
            SyncHttpClient client = new SyncHttpClient();
            client.setTimeout(Constants.SYNC_TIMEOUT);
            if (null != params) {
                client.post(url, params, getSyncResponseHandler(map));
            } else {
                client.post(url, getSyncResponseHandler(map));
            }

        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
            map.setLocalError(ex.getMessage());
        }
        return map;
    }

    public static void sendSimpleRequestAsync(String url, JSONObject reqJson, Context context, final DataHandler<JSONObject> dataHandler) {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            StringEntity stringEntity = new StringEntity(reqJson.toString(), "utf-8");
            client.post(context, url, stringEntity, "application/json", getAsyncResponseHandler(dataHandler));
        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }
    }

    public static void sendSimpleRequestAsyncGet(String url, Context context, final DataHandler<JSONObject> dataHandler) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            client.get(context, url, getAsyncResponseHandler(dataHandler));
        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }
    }

    public static void sendRequestAsyncNew(String url, JSONObject reqJson, Context context, final HttpServiceHandler httpHandler) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            client.addHeader("Accept-LANGUAGE", Constants.LANGUAGE);

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("user_id", "zhang_san"));

            HttpEntity entity = new UrlEncodedFormEntity(parameters);

            if (null != entity) {
                client.post(context, url, entity, "application/json", getAsyncResponseHandler(httpHandler));
            } else {
                client.post(url, getAsyncResponseHandler(httpHandler));
            }

        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }

    }

    public static void sendRequestAsync(String url, JSONObject reqJson, Context context, final HttpServiceHandler httpHandler) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(Constants.REQUEST_TIMEOUT);
            client.addHeader("Accept-LANGUAGE", Constants.LANGUAGE);

            if (null != reqJson) {
                StringEntity stringEntity = new StringEntity(reqJson.toString(), "utf-8");
                client.post(context, url, stringEntity, "application/json", getAsyncResponseHandler(httpHandler));
            } else {
                client.post(url, getAsyncResponseHandler(httpHandler));
            }

        } catch (Exception ex) {
            Log.e(ex.toString(), ex.toString());
        }

    }


    public static AsyncHttpResponseHandler getAsyncResponseHandler(final HttpServiceHandler httpHandler) {

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int reasonCode, Header[] headers, byte[] bytes, Throwable throwable) {
                try {
                    Log.d(TAG, "reasonCode:" + reasonCode);//0 ip写错

                    String errorReason = throwable.getMessage();
                    httpHandler.onError(Constants.SERVER_ERROR, "", throwable);
                } catch (Exception e) {
                    Log.e(TAG, throwable.toString());
                    //by changLiu
                    if (throwable.toString().equals("java.net.SocketTimeoutException")) {
                        CustomToast.showToast(AppContext.getInstance(), "网络不稳定，请稍候重试", 2000);
                        return;
                    }
                    httpHandler.onError(Constants.SERVER_ERROR, "", throwable);
                }
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String recvData = new String(bytes, "utf-8");

                    Log.d(TAG, "recvData:" + recvData.toString());
                    ResultData map = null;
                    JSONObject recvJson = new JSONObject(recvData);
                    map = new ResultData(recvJson);

                    showExceptionCode(map);

                    httpHandler.onResponse(map.getCode(), map.getReason(), map.getJsonObject());

                } catch (Exception ex) {
                    Log.e(ex.toString(), ex.toString());
                    httpHandler.onError(Constants.SERVER_ERROR, ex.toString(), ex);
                }
            }
        };
        return responseHandler;
    }

    private static void showExceptionCode(ResultData map) {
        int exceptionInfo = 0;
        switch (map.getCode()) {
            /*case Constants.PICKDELETED:
				exceptionInfo=R.string.cardDeleted;
                break;
            case Constants.VOTESELF:
                exceptionInfo=R.string.votedSelf;
                break;
            case Constants.REPLACEVOTE:
                exceptionInfo=R.string.cardreVoted;
                break;
            case Constants.HASEXPIRED:
                exceptionInfo=R.string.cardExpried;
                break;
            case Constants.VOTEPICK:
                exceptionInfo=R.string.cardvoted;
                break;*/
            default:
                break;
        }
        if (exceptionInfo == 0)
            return;
        //  CustomToast.showToast(AppContext.getAppContext().getCurrentActivity(),exceptionInfo, 2000);
        // TipsToast.getInstance().makeTextRaw(AppContext.getAppContext().getCurrentActivity(),exceptionInfo,2000);
//        tipsToast.setDuration(Toast.LENGTH_SHORT);
//        tipsToast.show();
        return;
    }


    public static AsyncHttpResponseHandler getSyncResponseHandler(final ResultData map) {

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(int reasonCode, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                String errorReason = throwable.getMessage();
                //xinqi
                try {
                    Log.d(TAG, "reasonCode:" + reasonCode);//0 ip写错
                    Log.d(TAG, "errorReason:" + errorReason);//0 ip写错
                    map.setCodeReason(reasonCode, errorReason);
                } catch (Exception e) {
                    Log.e(TAG, throwable.toString());
                    if (throwable.toString().equals("java.net.SocketTimeoutException")) {
                        CustomToast.showToast(AppContext.getInstance(), "网络不稳定，请稍候重试", 2000);
                        return;
                    }
                    map.setCodeReason(reasonCode, errorReason);
                }

            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String recvData = new String(bytes, "utf-8");
                    JSONObject recvJson = new JSONObject(recvData);
                    map.setJsonData(recvJson);
                } catch (Exception ex) {
                    Log.e(ex.toString(), ex.toString());
                    map.setLocalError(ex.getMessage());
                }
            }
        };
        return responseHandler;
    }


    private static AsyncHttpResponseHandler getAsyncResponseHandler(final DataHandler dataHandler) {

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(int reasonCode, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                try {
                    String errorReson = throwable.getMessage();
                    Log.d(TAG, "recvData:" + errorReson.toString());
                    dataHandler.onData(Constants.SERVER_ERROR, "", throwable);
                } catch (Exception e) {
                    Log.e(TAG, throwable.toString());
                    //by chengl
                    if (throwable.toString().equals("java.net.SocketTimeoutException")) {
                        CustomToast.showToast(AppContext.getInstance(), "网络不稳定，请稍候重试", 2000);
                        return;
                    }
                    dataHandler.onData(Constants.SERVER_ERROR, "", throwable);
                }
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String recvData = new String(bytes, "utf-8");
                    Log.d(TAG, "recvData:" + recvData.toString());

                    JSONObject recvJson = new JSONObject(recvData);


                    ResultData map = new ResultData(recvJson);
//                    Log.d(TAG, "recvData:isSuccess=" + map.isSuccess());
//                    Log.d(TAG, "recvData:getCode=" + map.getCode());
//                    Log.d(TAG, "recvData:getReason=" + map.getReason());
//                    Log.d(TAG, "recvData:getJsonObject=" + map.getJsonObject());
                    if (map.getCode() == 101000002) {
                        LoginManager.getInstance().clearLoginInfo();

                    }


                    showExceptionCode(map);
                    dataHandler.onData(map.getCode(), map.getReason(), map.getJsonObject());
                } catch (Exception ex) {
                    Log.e(ex.toString(), ex.toString());
                }
            }
        };
        return responseHandler;
    }






}
