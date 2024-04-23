package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PublishController {
	
	public PublishService publishService;
	
	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(@PathVariable int postId,@RequestBody @Valid PublishRequest publishRequest)
	{
		return publishService.publishBlogPost(postId, publishRequest);
	}
	
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishBlogPost(@PathVariable int postId)
	{
		return publishService.unpublishBlogPost(postId);
	}
	
	@GetMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByBlogPostId(@PathVariable int postId)
	{
		return publishService.findByBlogPostId(postId);
	}
	
	@GetMapping("/blog-posts/{postId}/blogs")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByBlogPostIdByPosttype(@PathVariable int postId)
	{
		return publishService.findByBlogPostIdByPosttype(postId);
	}

}
