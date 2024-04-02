package com.example.cms.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishRequest {
	
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	
	
	
	

}
