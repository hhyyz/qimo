package com.case8.pojo;

public class Knowledge extends Base{
	private int postId;
	private String title;
	private String img;
	private String content;
	private int eye;
	private int collection;		// 排序
	private String eyeimg;
	private String collimg;
	private String type;
	public Knowledge(int postId, String title, String img, String content, int eye, int collection,String eyeimg,String collimg,String type) {
		this.postId = postId;
		this.title = title;
		this.img = img;
		this.content = content;
		this.eye = eye;
		this.collection = collection;
		this.eyeimg=eyeimg;
		this.collimg=collimg;
		this.type=type;
	}
	
	public Knowledge() {
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getEye() {
		return eye;
	}
	public void setEye(int eye) {
		this.eye = eye;
	}

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}
	public String getEyeimg() {
		return eyeimg;
	}
	public void setEyeimg(String eyeimg) {
		this.eyeimg = eyeimg;
	}
	public String getCollimg() {
		return collimg;
	}
	public void setCollimg(String collimg) {
		this.collimg = collimg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
