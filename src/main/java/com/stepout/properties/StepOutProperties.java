package com.stepout.properties;

import org.springframework.beans.factory.annotation.Value;

public class StepOutProperties {
	@Value("${com.stepout.title}")
	private String title;
	@Value("${com.stepout.description}")
	private String description;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
