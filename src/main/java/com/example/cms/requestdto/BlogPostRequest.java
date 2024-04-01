package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogPostRequest {
	@NotNull(message = "Title should not be null or empty")
	private String title;
	private String subtitle;
	@Size(min = 500, max = 3000)
	private String summary;
	
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;

}
