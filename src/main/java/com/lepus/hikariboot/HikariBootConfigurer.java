package com.lepus.hikariboot;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lepus.hikariboot.framework.date.String2DateArgumentResolver;
import com.lepus.hikariboot.framework.jackson.JacksonJsonReturnHandler;

/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 下午1:47:36
 */
@Configuration
public class HikariBootConfigurer extends WebMvcConfigurerAdapter{

	public void addInterceptors(InterceptorRegistry registry){
		super.addInterceptors(registry);
	}
	
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		argumentResolvers.add(new String2DateArgumentResolver());
		super.addArgumentResolvers(argumentResolvers);
	}
	
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers){
		returnValueHandlers.add(new JacksonJsonReturnHandler());
		super.addReturnValueHandlers(returnValueHandlers);
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:C:/Users/Administrator/Desktop/");
		super.addResourceHandlers(registry);
	}
	
}
