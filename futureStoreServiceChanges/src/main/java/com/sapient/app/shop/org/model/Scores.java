package com.sapient.app.shop.org.model;

public class Scores {
	private float weight;
	private float confidence;
	
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
	@Override
	public String toString() {
		return "Scores [weight=" + weight + ", confidence=" + confidence + "]";
	}
	
}
