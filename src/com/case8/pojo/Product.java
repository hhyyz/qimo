package com.case8.pojo;

public class Product extends Base{
	private int postId;
	private String name;
	private int price;
	private int number;
	private int type;
	private int yprice;
	private String img;
	
	public Product(int postId,String name, int number,int price, int type,int yprice,String img) {
		this.postId = postId;
		this.name = name;
		this.number = number;
		this.price = price;
		this.type = type;
		this.yprice = yprice;
		this.img = img;
	}
	
	public Product() {
	}

	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public int getYprice() {
		return yprice;
	}

	public void setYprice(int yprice) {
		this.yprice = yprice;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
