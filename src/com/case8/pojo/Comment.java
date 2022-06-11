package com.case8.pojo;

public class Comment extends Base{
	private int postId;
	private String name;
	private String img;
	private String comment;
	private int shoppingid;
	
	
	
	public Comment(int postId,String name,String img,String comment, int shoppingid) {
		this.postId = postId;
		this.name = name;
		this.img = img;
		this.comment = comment;
		this.shoppingid = shoppingid;
		
	}
	
	public Comment() {
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getShoppingid() {
		return shoppingid;
	}
	public void setShoppingid(int shoppingid) {
		this.shoppingid = shoppingid;
	}

	
	
}
