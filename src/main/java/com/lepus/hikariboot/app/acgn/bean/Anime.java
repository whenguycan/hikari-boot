package com.lepus.hikariboot.app.acgn.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lepus.hikariboot.app.enums.SerialState;
import com.lepus.hikariboot.app.enums.WatchState;
import com.lepus.hikariboot.framework.build.BaseEntity;
import com.lepus.hikariboot.utils.PinyinUtils;

/**
 * 
 * @author wangchenyu
 * @date 2017-11-3
 */
@Entity
@Table(name = "t_e_anime")
public class Anime extends BaseEntity{
	private static final long serialVersionUID = 4104312292631474689L;
	
	@Column(columnDefinition = "varchar(64) not null")
	private String name;
	
	@Column(columnDefinition = "varchar(32)")
	private String pre;
	
	@Column(columnDefinition = "varchar(32)")
	private String ext;
	
	@Column(columnDefinition = "varchar(32)", name = "serialdate")
	private String serialDate;
	
	@Column(columnDefinition = "int(11) not null")
	private int season;
	
	@Column(columnDefinition = "int(11) not null", name = "serialstate")
	private int serialState = SerialState.ING.codeInt();
	
	@Column(columnDefinition = "int(11) not null", name = "watchstate")
	private int watchState = WatchState.ING.codeInt();
	
	@Column(columnDefinition = "int(11) not null")
	private int curr;
	
	@Column(columnDefinition = "int(11) not null")
	private int total;
	
	@Column(columnDefinition = "int(11) not null")
	private int favo;
	
	@Column(columnDefinition = "varchar(255)")
	private String link;
	
	@Column(columnDefinition = "datetime not null", updatable = false, name = "createtime")
	private Date createTime = new Date();

	@Column(columnDefinition = "datetime not null", updatable = true, name = "updatetime")
	private Date updateTime = new Date();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setPre(PinyinUtils.getPre(name));
	}

	public int getSerialState() {
		return serialState;
	}

	public void setSerialState(int serialState) {
		this.serialState = serialState;
	}

	public int getCurr() {
		return curr;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getFavo() {
		return favo;
	}

	public void setFavo(int favo) {
		this.favo = favo;
	}
	
	public String getLink(){
		return this.link;
	}

	public String getLink2() {
		return link!=null&&!"".equals(link)?link.indexOf("://")==-1?"http://"+link:link:link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPre() {
		return pre;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public int getWatchState() {
		return watchState;
	}

	public void setWatchState(int watchState) {
		this.watchState = watchState;
	}
	
	public void calWatchState(){
		this.watchState = this.curr==0&&this.total==0?WatchState.INIT.codeInt():this.curr!=this.total?WatchState.ING.codeInt():WatchState.END.codeInt();
	}

	public String getSerialDate() {
		return serialDate;
	}

	public void setSerialDate(String serialDate) {
		this.serialDate = serialDate;
	}
	
}
