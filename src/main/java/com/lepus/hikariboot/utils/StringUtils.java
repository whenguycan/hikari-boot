package com.lepus.hikariboot.utils;

/**
 * 
 * @author whenguycan
 * @date 2017年11月4日 下午11:22:39
 */
public class StringUtils {

	public static boolean isBlank(String str){
		return str==null||"".equals(str)?true:false;
	}
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
}
