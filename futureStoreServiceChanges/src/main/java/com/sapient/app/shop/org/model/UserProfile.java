package com.sapient.app.shop.org.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="users")
public class UserProfile {
	
	@Id
	@JsonProperty("person_id")
	private String id;
	private String name;
	private Date timestamp;
	private Map<String, Scores> attributes;
	private List<History> history = new ArrayList<>();
	
	public Map<String, Scores> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Scores> attributes) {
		this.attributes = attributes;
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
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public List<History> getHistory() {
		return history;
	}
	public void setHistory(List<History> history) {
		this.history = history;
	}
	

}
