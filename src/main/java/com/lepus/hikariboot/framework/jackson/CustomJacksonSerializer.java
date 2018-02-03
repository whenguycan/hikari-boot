package com.lepus.hikariboot.framework.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author wangchenyu
 * @date 2018-1-5
 */
public class CustomJacksonSerializer {

	ObjectMapper mapper = new ObjectMapper();
	JacksonFilterProvider jacksonProvider = new JacksonFilterProvider();
	
	public void filter(Class<?> type, String include, String exclude){
		if(type == null)
			return;
		if(include != null && !"".equals(include)){
			jacksonProvider.include(type, include.split(","));
		}
		if(exclude != null && !"".equals(exclude)){
			jacksonProvider.exclude(type, exclude.split(","));
		}
		mapper.addMixIn(type, jacksonProvider.getClass());
	}
	
	public String toJson(Object o) throws JsonProcessingException{
		mapper.setFilterProvider(jacksonProvider);
		return mapper.writeValueAsString(o);
	}
	
}
