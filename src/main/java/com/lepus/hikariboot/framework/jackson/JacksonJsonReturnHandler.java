package com.lepus.hikariboot.framework.jackson;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * @author wangchenyu
 * @date 2018-1-5
 */
public class JacksonJsonReturnHandler implements HandlerMethodReturnValueHandler{

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(Json.class) != null;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		Annotation anno = returnType.getMethodAnnotation(Json.class);
		CustomJacksonSerializer jacksonSerializer = new CustomJacksonSerializer();
		if(anno instanceof Json){
			Filter[] filters = ((Json) anno).filters();
			if(filters != null){
				for(Filter filter : filters){
					jacksonSerializer.filter(filter.type(), filter.include(), filter.exclude());
				}
			}
		}
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(jacksonSerializer.toJson(returnValue));
	}

}
