package com.lepus.hikariboot.framework.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * @author wangchenyu
 * @date 2018-1-2
 */
public class String2DateArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter methodparameter) {
		return Date.class.isAssignableFrom(methodparameter.getParameterType());
	}

	public Object resolveArgument(MethodParameter methodparameter, ModelAndViewContainer modelandviewcontainer, NativeWebRequest nativewebrequest, WebDataBinderFactory webdatabinderfactory) throws Exception {
		modelandviewcontainer.setRequestHandled(true);
		String name = methodparameter.getParameterName();
		String parameter = nativewebrequest.getParameter(name);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(parameter);
	}

}
