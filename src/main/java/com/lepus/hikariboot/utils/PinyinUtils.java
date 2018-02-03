package com.lepus.hikariboot.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 
 * @author wangchenyu
 * @date 2017-11-12
 */
public class PinyinUtils {
	
	public static String getPre(String name){
		if(StringUtils.isBlank(name))
			return "";
		char ch = name.charAt(0);
		String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch);
		if(arr == null)
			return name.substring(0, 1).toLowerCase();
		return arr[0].substring(0, 1);
	}
	
}
