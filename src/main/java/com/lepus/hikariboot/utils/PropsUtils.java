package com.lepus.hikariboot.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author wangchenyu@cit
 * @date   2017-9-14
 */
public class PropsUtils {

	public static Properties props = new Properties();
	
	public static Properties init(String path){
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + path);
			props.load(is);
		} catch (Exception e) {
			System.out.println("loading error : " + path);
			e.printStackTrace();
		}
		return props;
	}
	
	public static Properties reload(String path){
		props.clear();
		return init(path);
	}
	
}
