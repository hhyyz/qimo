package com.case8.pojo;

public class User extends Base{
	private int id;
	private String name;
	private String phone;
	private String password;
	private int type;
	
	public User(int id,String name, String phone, int type) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.type = type;
	}
	
	public User() {
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
