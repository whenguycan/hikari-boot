package com.lepus.hikariboot.framework.build;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.lepus.hikariboot.utils.StringUtils;

/**
 * 
 * @author wangchenyu
 * @date 2017-11-3
 */
public class BaseController {
	
	protected Gson gson = new Gson();
	
	protected Map<String, String> getInterceptoredParams(HttpServletRequest req){
		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, String[]> params = req.getParameterMap();
		for(Entry<String, String[]> entry : params.entrySet()){
			String pattern = entry.getKey();
			String[] arr = pattern.split("_");
			if("s".equalsIgnoreCase(arr[0]) || "sa".equalsIgnoreCase(arr[0])){
				String[] valueArr = entry.getValue();
				if(valueArr != null && valueArr.length != 0 && StringUtils.isNotBlank(valueArr[0])){
					paramMap.put(pattern, valueArr[0]);
				}
			}
		}
		return paramMap;
	}
	
	protected Map<String, Object> getSuccessResponse(String msg, Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", msg);
		map.put("data", data);
		return map;
	}
	
	protected Map<String, Object> getFailedResponse(String msg, Exception e){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("msg", msg);
		map.put("e", e!=null?e.getStackTrace():null);
		return map;
	}

}
