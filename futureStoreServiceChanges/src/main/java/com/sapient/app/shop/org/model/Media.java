package com.sapient.app.shop.org.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Media {
	
	@JsonProperty("collateral_id")
	private String id;
	@JsonProperty("content_type")
	private String contentType;
	private String url;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
