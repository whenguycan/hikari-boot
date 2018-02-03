package com.lepus.hikariboot.utils;

import java.util.HashMap;
import java.util.Map;

public class EnumsConverter {
	
	public static Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
	
	public static void init(Map<String, Map<String, Object>> data){
		if(data != null){
			for(String key : data.keySet()){
				map.put(key, data.get(key));
			}
		}
	}
	
	public static String convert(String tag, String code){
		if(!map.containsKey(tag))
			return "ERROR TAG";
		Map<String, Object> m = map.get(tag);
		if(m == null || m.isEmpty())
			return "EMPTY TAG";
		if(!m.containsKey(code))
			return "ERROR CODE";
		Object value = m.get(code);
		if(value == null)
			return "EMPTY CODE";
		return String.valueOf(value);
	}

}
