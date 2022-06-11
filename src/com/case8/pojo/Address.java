package com.case8.pojo;

public class Address extends Base{
	private int postId;
	private String addresslist;
	private String name;
	private String phone;
	
	
	
	public Address(int postId,String addresslist,String name,String phone) {
		this.postId = postId;
		this.addresslist = addresslist;
		this.name = name;
		this.phone = phone;
		
	}
	
	public Address() {
	}

	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getAddresslist() {
		return addresslist;
	}
	public void setAddresslist(String addresslist) {
		this.addresslist = addresslist;
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

	
	
}
