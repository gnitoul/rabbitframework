package com.rabbitframework.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rabbitframework.commons.exceptions.DataParseException;

public class JsonUtils {

	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static String toJsonString(Object obj, SerializerFeature... features) {
		return JSON.toJSONString(obj, features);
	}

	public static String toJsonString(Object obj, SerializeFilter filter, SerializerFeature... features) {
		return JSON.toJSONString(obj, filter, features);
	}

	public static <T> T getObject(String jsonString, Class<T> cls) {
		try {
			return JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			throw new DataParseException(e);
		}
	}

	public static <T> List<T> getListObject(String jsonString, Class<T> cls) {
		try {
			List<T> resultData = JSON.parseArray(jsonString, cls);
			if (resultData == null) {
				resultData = new ArrayList<T>();
			}
			return resultData;
		} catch (Exception e) {
			throw new DataParseException(e);
		}
	}

	public static List<Map<String, String>> getKeyStringMap(String jsonString) {
		List<Map<String, String>> list;
		try {
			list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
			});
		} catch (Exception e) {
			throw new DataParseException(e);
		}
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
		}
		return list;
	}

	public static List<Map<Long, String>> getKeyLongMap(String jsonString) {
		List<Map<Long, String>> list;
		try {
			list = JSON.parseObject(jsonString, new TypeReference<List<Map<Long, String>>>() {
			});
		} catch (Exception e) {
			throw new DataParseException(e);
		}
		if (list == null) {
			list = new ArrayList<Map<Long, String>>();
		}
		return list;
	}
}
