package com.lepus.hikariboot.framework.build;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wangchenyu
 * @date 2017-11-3
 */
public class Page<T> {
	
	public static final int DEFAULT_PAGE_SIZE = 8;
	public static final int DEFAULT_FIRST_PAGE_NO = 1;
	public static final int DISPLAY_PAGINATION_LEN = 8;

	private int pageNo = DEFAULT_FIRST_PAGE_NO;

	private int pageSize = DEFAULT_PAGE_SIZE;
	
	private int pre = DEFAULT_FIRST_PAGE_NO;
	
	private int after = DEFAULT_FIRST_PAGE_NO + 1;
	
	private int rowFound;

	private int rowCount;

	private int pageCount;
	
	private List<Integer> pagination = new ArrayList<Integer>();

	private List<T> list;
	
	public Page(){
		
	}
	
	public Page(Integer pageNo){
		setPageNo(pageNo);
	}
	
	public Page(Integer pageNo, Integer pageSize){
		setPageNo(pageNo);
		setPageSize(pageSize);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo==null||pageNo<1?DEFAULT_FIRST_PAGE_NO:pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize==null||pageSize<1?DEFAULT_PAGE_SIZE:pageSize;
	}

	public int getPre() {
		return pre;
	}

	public void setPre(int pre) {
		this.pre = pre;
	}

	public int getAfter() {
		return after;
	}

	public void setAfter(int after) {
		this.after = after;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getRowFound() {
		this.rowFound = this.list!=null?this.list.size():0;
		return rowFound;
	}

	public List<Integer> getPagination() {
		calPagination();
		return pagination;
	}

	public void calPagination(){
		int curr = this.pageNo;
		int total = this.pageCount;
		int len = DISPLAY_PAGINATION_LEN;
		if(total > len){
			int begin = 0;
			int end = 0;
			if(len % 2 == 0){
				begin = curr - len / 2;
				end = curr + len / 2 + 1;
			}else{
				begin = curr - len / 2;
				end = curr + len / 2;
			}
			if(begin < 1){
				end += 1 - begin;
				begin = 1;
			}
			if(end > total){
				begin += total - end;
				end = total;
			}
			for(int i=begin ,iLen=end+1; i<iLen; i++){
				this.pagination.add(i);
			}
		}else{
			for(int i=1, iLen=total+1; i<iLen; i++){
				this.pagination.add(i);
			}
		}
	}

}
