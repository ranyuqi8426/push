package com.send;
/**
 * 消息体
 * @author ryq
 * @param title 通知标题  
 * @param Htcontext 透传的内容
 * @param context 通知内容 
 * @param imgName 通知图标，需要客户端开发时嵌入  
 * @param imgUrl 通知图标URL地址
 *
 */
public class Msg {
	private String title;
	private String context;
	private String imgName;
	private String imgNameUrl;
	private String Htcontext;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgNameUrl() {
		return imgNameUrl;
	}
	public void setImgNameUrl(String imgNameUrl) {
		this.imgNameUrl = imgNameUrl;
	}
	public String getHtcontext() {
		return Htcontext;
	}
	public void setHtcontext(String htcontext) {
		Htcontext = htcontext;
	}
	
}
