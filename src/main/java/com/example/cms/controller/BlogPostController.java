package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class BlogPostController {
	
private BlogPostService blogPostService;
	
	public BlogPostController(BlogPostService blogPostService) {
		super();
		this.blogPostService = blogPostService;
	}



	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPost(@PathVariable int blogId,@RequestBody BlogPostRequest blogPostRequest)
	{
		return blogPostService.createDraft(blogId, blogPostRequest);
	}
	
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(@PathVariable int postId,@RequestBody BlogPostRequest blogPostRequest)
	{
	 return	blogPostService.updateDraft(postId,blogPostRequest);
	}
	
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId)
	{
	 return	blogPostService.deleteDraft(postId);
	}

}
