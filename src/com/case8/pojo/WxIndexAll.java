package com.case8.pojo;

import java.util.ArrayList;
import java.util.List;

public class WxIndexAll {
	private List<WxIndex> imgUrls = new ArrayList<>();
	private WxIndex image_ad = new WxIndex();
	private List<WxIndex> image_bottom = new ArrayList<>();
	
	public List<WxIndex> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(List<WxIndex> imgUrls) {
		this.imgUrls = imgUrls;
	}
	public WxIndex getImage_ad() {
		return image_ad;
	}
	public void setImage_ad(WxIndex image_ad) {
		this.image_ad = image_ad;
	}
	public List<WxIndex> getImage_bottom() {
		return image_bottom;
	}
	public void setImage_bottom(List<WxIndex> image_bottom) {
		this.image_bottom = image_bottom;
	}
	
	
}
