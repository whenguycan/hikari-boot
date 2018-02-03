package com.lepus.hikariboot.app.enums;

import java.util.ArrayList;
import java.util.List;

import com.lepus.hikariboot.utils.EnumsUtils.MyEnum;

/**
 * 
 * @author whenguycan
 * @date 2018年2月3日 下午5:08:11
 */
public class Checker {

	private String code;
	private String text;
	
	public static List<Checker> create(MyEnum[] mes){
		List<Checker> list = new ArrayList<Checker>();
		if(mes != null){
			for(MyEnum me : mes){
				list.add(new Checker(me.code(), me.text()));
			}
		}
		return list;
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
