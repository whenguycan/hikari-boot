package com.lepus.hikariboot.app.enums;

import java.util.ArrayList;
import java.util.List;

import com.lepus.hikariboot.utils.EnumsUtils.MyEnum;
import com.lepus.hikariboot.utils.StringUtils;

/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 下午5:08:11
 */
public class Checker {

	private String code;
	private String text;
	
	public static List<Checker> create(MyEnum[] mes, String... excludes){
		List<Checker> list = new ArrayList<Checker>();
		if(mes != null){
			for(MyEnum me : mes){
				if(!isExclude(me.code(), excludes)){
					list.add(new Checker(me.code(), me.text()));
				}
			}
		}
		return list;
	}
	
	private static boolean isExclude(String code, String... excludes){
		if(StringUtils.isBlank(code))
			return true;
		if(excludes == null || excludes.length == 0)
			return true;
		boolean find = false;
		for(String exclude : excludes){
			if(code.equals(exclude)){
				find = true;
				break;
			}
		}
		return find;
	}
	
	public Checker(String code, String text){
		this.code = code;
		this.text = text;
	}
	
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getText(){
		return text;
	}
	public void setText(String text){
		this.text = text;
	}
	
}
