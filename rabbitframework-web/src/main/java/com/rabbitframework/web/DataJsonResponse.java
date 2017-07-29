package com.rabbitframework.web;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class DataJsonResponse {
	/* 200成功状态 */
	public static final int SC_OK = 200;
	/* 授权失败,401 */
	public static final int SC_UNAUTHORIZED = 401;
	/* 404错误 */
	public static final int SC_NOT_FOUND = 404;
	/* 用户认证失败 */
	public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
	/* 500 服务器内部错误 */
	public static final int SC_INTERNAL_SERVER_ERROR = 500;
	/* 后台逻辑错误 */
	public static final int FAIL = -1;
	/* 数据验证错误 */
	public static final int SC_VALID_ERROR = -2;

	public static final String MESSAGE = "message";
	private Map<String, Object> json = new HashMap<String, Object>();

	private Map<String, Object> data;

	public DataJsonResponse set(String key, Object value) {
		json.put(key, value);
		return this;
	}

	public DataJsonResponse setData(String key, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
		return this;
	}

	public DataJsonResponse setData(Object value) {
		json.put("data", value);
		return this;
	}

	public DataJsonResponse setMessage(String success) {
		json.put("message", success);
		return this;
	}

	public DataJsonResponse setStatus(boolean status, String message) {
		setStatus(status);
		json.put("message", message);
		return this;
	}

	public DataJsonResponse setStatus(boolean status) {
		json.put("status", status ? SC_OK : FAIL);
		return this;
	}

	public DataJsonResponse setStatus(int status) {
		json.put("status", status);
		return this;
	}

	public DataJsonResponse setStatus(int status, String message) {
		json.put("status", status);
		json.put("message", message);
		return this;
	}

	/**
	 * 空值自动转换
	 * 
	 * @return
	 */
	public String toJson() {
		if (data != null && !json.containsKey("data")) {
			json.put("data", data);
		}
		return JSON.toJSONString(json, SerializerFeature.BrowserCompatible, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullBooleanAsFalse);
	}

	/**
	 * 忽略空值
	 * 
	 * @return
	 */
	public String toJsonNoNull() {
		if (data != null && !json.containsKey("data")) {
			json.put("data", data);
		}
		return JSON.toJSONString(json, SerializerFeature.BrowserCompatible);
	}
}