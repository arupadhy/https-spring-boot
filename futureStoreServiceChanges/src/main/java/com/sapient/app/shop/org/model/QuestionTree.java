package com.sapient.app.shop.org.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionTree {
	
	@JsonProperty("answer_key")
	private String answerKey;

	@JsonProperty("answer_text")
	private String answerText;

	@JsonProperty("follow_up_id")
	private String followUpId;

	@JsonProperty("follow_up_audio")
	private String followUpAudio;

	@JsonProperty("attribute_scores")
	private Map<String, Map<String,Double>> attributeScores;

	public String getAnswerKey() {
		return answerKey;
	}

	public void setAnswerKey(String answerKey) {
		this.answerKey = answerKey;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public String getFollowUpId() {
		return followUpId;
	}

	public void setFollowUpId(String followUpId) {
		this.followUpId = followUpId;
	}

	public String getFollowUpAudio() {
		return followUpAudio;
	}

	public void setFollowUpAudio(String followUpAudio) {
		this.followUpAudio = followUpAudio;
	}

	public Map<String, Map<String, Double>> getAttributeScores() {
		return attributeScores;
	}

	public void setAttributeScores(Map<String, Map<String, Double>> attributeScores) {
		this.attributeScores = attributeScores;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		QuestionTree that = (QuestionTree) o;

		if (answerKey != null ? !answerKey.equals(that.answerKey) : that.answerKey != null) return false;
		if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
		if (followUpId != null ? !followUpId.equals(that.followUpId) : that.followUpId != null) return false;
		if (followUpAudio != null ? !followUpAudio.equals(that.followUpAudio) : that.followUpAudio != null)
			return false;
		return attributeScores != null ? attributeScores.equals(that.attributeScores) : that.attributeScores == null;

	}

	@Override
	public int hashCode() {
		int result = answerKey != null ? answerKey.hashCode() : 0;
		result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
		result = 31 * result + (followUpId != null ? followUpId.hashCode() : 0);
		result = 31 * result + (followUpAudio != null ? followUpAudio.hashCode() : 0);
		result = 31 * result + (attributeScores != null ? attributeScores.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "QuestionTree{" +
				"answerKey='" + answerKey + '\'' +
				", answerText='" + answerText + '\'' +
				", followUpId='" + followUpId + '\'' +
				", followUpAaudio='" + followUpAudio + '\'' +
				", attributeScores=" + attributeScores +
				'}';
	}
}