package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;

//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class PublishRequest {
	
	@NotNull(message="should not be null")
	private String seoTitle;
	 private String seoDescription;
	 private String[] seoTopics;
	 private ScheduleRequest schedule;
	 
	public ScheduleRequest getSchedule() {
		return schedule;
	}
	public void setSchedule(ScheduleRequest schedule) {
		this.schedule = schedule;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String[] getSeoTopics() {
		return seoTopics;
	}
	public void setSeoTopics(String[] seoTopics) {
		this.seoTopics = seoTopics;
	}

	
	
	
	

}
