package com.case8.pojo;

import java.util.ArrayList;
import java.util.List;

public class BaseListPojo<T> {

	private boolean success;
	private String msg;
	private int total;
	private List<T> list = new ArrayList<>(); // 封装数组数据

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public BaseListPojo(String msg, boolean success, int total, List<T> list){
		this.msg = msg;
		this.success = success;
		this.total = total;
		this.list = list;
	}
	
	public BaseListPojo() {		
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
