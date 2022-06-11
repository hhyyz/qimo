package com.case8.pojo;

public class WxIndex extends Base{
	private int id;
	private String title;
	private String imgUrl;
	private String link;
	private int state;
	private int rank;		// 排序
	private int position;	// 位置
	
	public WxIndex(int id, String title, String imgUrl, String link, int state, int rank, int position) {
		this.id = id;
		this.title = title;
		this.imgUrl = imgUrl;
		this.link = link;
		this.state = state;
		this.rank = rank;
		this.position = position;
	}
	
	public WxIndex() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
