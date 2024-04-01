package com.example.cms.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class BlogRequest {
	
	@NotNull(message = "Title should not be null")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]*$")
	private String title;
	
	@NotNull(message = "Atleast one topic should be specified")
	private String[] topics;
	
	private String about;
	
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	
}
