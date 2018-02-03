package com.lepus.hikariboot;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 下午2:07:24
 */
@WebListener
public class HikariBootListener implements ServletContextListener{
	
	@Value("${server.context-path}")
	private String contextPath;

	public void contextDestroyed(ServletContextEvent event){
		
	}

	public void contextInitialized(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		context.setAttribute("root", contextPath);
	}
	
}
