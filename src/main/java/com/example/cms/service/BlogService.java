package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponse>> createBlog(BlogRequest blogRequest, int userId);

//	ResponseEntity<Boolean> checkBlogTitle(String title);
	
	ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId);

	ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(int blogId, BlogRequest bolgRequest);

	ResponseEntity<ResponseStructure<ContributionPanel>> addContributors(int userId, int panelId);

	ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId, int panelId);

	ResponseEntity<Boolean> checkBlogByTitle(String title);


}
