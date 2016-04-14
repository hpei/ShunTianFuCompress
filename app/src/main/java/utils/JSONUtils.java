package utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONUtils {

	public static <T> T json2Bean(String result, Class<T> clz) {
		Gson gson = new Gson();
		T t = gson.fromJson(result, clz);
		return t;
	}

	public static Map<String, String> jsonObject2Map(JSONObject jsonObj) {
		Map<String, String> retMap = new HashMap<String, String>();
		Iterator keyit = jsonObj.keys();
		while (keyit.hasNext()) {
			String key = (String) keyit.next();
			retMap.put(key, jsonObj.optString(key));
		}
		return retMap;
	}

	public static JSONObject addJSONParam(JSONObject reqJSON, String name, String value) {
		try {
			if (value == null) {
				value = "";
			}
			reqJSON.put(name, value);
			return reqJSON;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONArray addJSONParam(JSONArray array, int index, String value) {
		try {
			if (value == null) {
				value = "";
			}
			array.put(index, value);
			return array;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONObject addJSONParam(JSONObject reqJSON, String name, int value) {
		try {
			reqJSON.put(name, value);
			return reqJSON;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONObject addJSONParam(JSONObject reqJSON, String name, long value) {
		try {
			reqJSON.put(name, value);
			return reqJSON;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONObject addJSONParam(JSONObject reqJSON, String name, List value) {
		try {
			reqJSON.put(name, value);
			return reqJSON;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONObject addJSONParam(JSONObject reqJSON, String name, JSONObject value) {
		try {
			reqJSON.put(name, value);
			return reqJSON;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONObject addJSONParam(JSONObject reqJSON, String name, JSONArray value) {
		try {
			reqJSON.put(name, value);
			return reqJSON;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static JSONObject changeKey(JSONObject jsonObject) {
		JSONObject msgData = new JSONObject();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();
			String changeKey = UpperStrUtil.convertString(key);

			if (jsonObject.opt(key) instanceof JSONArray) {
				JSONArray array = jsonObject.optJSONArray(key);
				//用来存储修改完的jsonArray
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < array.length(); i++) {
					try {
						JSONObject childJsonObject = array.optJSONObject(i);
						// LOG.error("传入的json原数据 集合对象" + childJsonObject.toString());
						//递归获取jsonArray中的单个修改完的jsonObject 存入新的jsonArray中
						JSONObject jsonObject1 = changeKey(childJsonObject);
						jsonArray.put(jsonObject1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//此处存放的是新的jsonArray
				addJSONParam(msgData, changeKey, jsonArray);

			} else if (jsonObject.opt(key) instanceof JSONObject) {
				//LOG.error("传入的json原数据 对象时" + jsonObject.toString());
				JSONObject object = jsonObject.optJSONObject(key);
				//存入修改完的jsonObject
				JSONObject jsonObject1 = changeKey(object);
				addJSONParam(msgData, changeKey, jsonObject1);

			} else {
				String object = jsonObject.optString(key);
				// LOG.error("传入的json原数据 字符串时" + object);
				addJSONParam(msgData, changeKey, object);
			}
		}

		return msgData;

	}
}
