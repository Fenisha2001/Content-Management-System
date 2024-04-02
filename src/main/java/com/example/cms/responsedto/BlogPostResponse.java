package com.example.cms.responsedto;

import java.time.LocalDateTime;

import com.example.cms.entity.Blog;
import com.example.cms.enums.PostType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostResponse {
	
	private int postId;
	private String title;
	private String subtitle;
	
	private PostType postType;
//	private String seoTitle;
//	private String seoDescription;
//	private String[] seoTopics;
	
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	
	private Blog blog;

}
