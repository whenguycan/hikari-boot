package com.lepus.hikariboot.app.enums;

import com.lepus.hikariboot.utils.EnumsUtils.MyEnum;

/**
 * 
 * @author whenguycan
 * @date 2017年11月4日 下午9:58:57
 */
public enum WatchState implements MyEnum{
	
	INIT("0", "初始化"),
	ING("1", "未完"),
	END("2", "补完");
	
	private String code;
	private String text;
	
	private WatchState(String code, String text){
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
