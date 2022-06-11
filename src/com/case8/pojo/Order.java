package com.case8.pojo;

public class Order extends Base{
	private int id;
	private String name;
	private int type;
	private String pname;
	private String dname;
	private int number;
	private int price;
	
	
	
	
	public Order(int id,String name, int type,String pname, String dname,int number,int price) {
		this.id = id;
		this.name = name;
		this.type = type;
		
		this.pname = pname;
		this.dname = dname;
		this.number = number;
		this.price = price;
		
	}
	
	public Order() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
    public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
