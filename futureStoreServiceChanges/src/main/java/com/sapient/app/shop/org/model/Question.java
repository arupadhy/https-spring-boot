package com.sapient.app.shop.org.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="question")
public class Question {
	
	@Id
	private String id;
	
	@JsonProperty("question-id")
	private String xlId;
	private String text;
	private String audio;
	private String category;
	private Map<String, Map<String,Double>> attributes;
	@JsonProperty("question-tree")
	private Map<String, QuestionTree> questionTree = new HashMap<>();
	@JsonProperty("quip-text")
	private String quipText = "";
	@JsonProperty("quip-audio")
	private String quipAudio = "";
	
	public String getXlId() {
		return xlId;
	}
	public void setXlId(String xlId) {
		this.xlId = xlId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Map<String, Map<String,Double>> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Map<String,Double>> attributes) {
		this.attributes = attributes;
	}
	public Map<String, QuestionTree> getQuestionTree() {
		return questionTree;
	}
	public void setQuestionTree(Map<String, QuestionTree> questionTree) {
		this.questionTree = questionTree;
	}
	public String getQuipText() {
		return quipText;
	}
	public void setQuipText(String quipText) {
		this.quipText = quipText;
	}
	public String getQuipAudio() {
		return quipAudio;
	}
	public void setQuipAudio(String quipAudio) {
		this.quipAudio = quipAudio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((audio == null) ? 0 : audio.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((questionTree == null) ? 0 : questionTree.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (audio == null) {
			if (other.audio != null)
				return false;
		} else if (!audio.equals(other.audio))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (questionTree == null) {
			if (other.questionTree != null)
				return false;
		} else if (!questionTree.equals(other.questionTree))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", audio=" + audio + ", category=" + category + ", attributes="
				+ attributes + ", questionTree=" + questionTree + "]";
	}
	
	

}