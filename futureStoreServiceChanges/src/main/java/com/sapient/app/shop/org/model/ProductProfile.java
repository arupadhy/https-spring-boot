package com.sapient.app.shop.org.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="product")
public class ProductProfile {
	
	@Id
	@JsonProperty("product_id")
	private String id;
	private String frontEndProductId;
	private String name;
	private String description;
	private String shortDescription;
	private List<Media> media;
	private List<History> history = new ArrayList<>();
	private Map<String, Scores> attributes;
	
	public String getFrontEndProductId() {
		return frontEndProductId;
	}
	public void setFrontEndProductId(String frontEndProductId) {
		this.frontEndProductId = frontEndProductId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public List<Media> getMedia() {
		return media;
	}
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	public List<History> getHistory() {
		return history;
	}
	public void setHistory(List<History> history) {
		this.history = history;
	}
	public Map<String, Scores> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Scores> attributes) {
		this.attributes = attributes;
	}
	
	

	
}
