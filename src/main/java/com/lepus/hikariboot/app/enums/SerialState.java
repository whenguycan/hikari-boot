package com.lepus.hikariboot.app.enums;

import com.lepus.hikariboot.utils.EnumsUtils.MyEnum;

/**
 * 
 * @author whenguycan
 * @date 2017年11月4日 下午9:58:57
 */
public enum SerialState implements MyEnum{
	
	INIT("0", "初始化"),
	ING("1", "连载中"),
	END("2", "已完结");
	
	private String code;
	private String text;
	
	private SerialState(String code, String text){
		this.code = code;
		this.text = text;
	}
	
	@Override
	public String code() {
		return this.code;
	}

	@Override
	public String text() {
		return this.text;
	}

	@Override
	public int codeInt() {
		return Integer.parseInt(this.code);
	}

}
