package com.case8.pojo;

public class Base {

	String condition;// 查询条件
	String limit;// 记录数
	String orderBy;// 排序条件

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String toString() {
		return "Base [condition=" + condition + ", limit=" + limit + ", orderBy=" + orderBy + "]";
	}

	public Base(String condition, String limit, String orderBy) {
		super();
		this.condition = condition;
		this.limit = limit;
		this.orderBy = orderBy;
	}

	public Base() {
		super();
	}
}
