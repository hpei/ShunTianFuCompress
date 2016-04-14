package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import common.Constants;

/**
 * Created by xinqi on 2016/3/23
 */
public class ResultData {
	private boolean success;
	private int code;
	private String reason;
	private Object jsonObject;
	private byte[] data;

	public ResultData() {
	}

	public ResultData(Map map) {
		success = Boolean.valueOf((String) map.get("success"));
		code = Integer.valueOf((String) map.get("code"));
		reason = (String) map.get("msg");
		jsonObject = (JSONObject) map.get("resultObject");
	}

	public ResultData(JSONObject jsonObject) throws JSONException {
		setJsonData(jsonObject);
	}

	public void setJsonData(JSONObject jsonObject) throws JSONException {
		this.success = jsonObject.optBoolean("success");
		this.code = jsonObject.optInt("code");
		this.reason = jsonObject.optString("msg");
		this.jsonObject = jsonObject.opt("resultObject");
	}

	public void setCodeReason(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	//default local error code = -a
	public void setLocalError(String error) {
		this.code = Constants.LOCAL_ERROR;
		this.reason = error;
	}

	public int getCode() {
		return code;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getReason() {
		return reason;
	}

	public Object getJsonObject() {
		return jsonObject;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}

