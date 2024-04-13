package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId, PublishRequest publishRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishBlogPost(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findByBlogPostId(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findByBlogPostIdByPosttype(int postId);

//	ResponseEntity<ResponseStructure<BlogPostResponse>> findAllBlogPostsScheduled();

}
