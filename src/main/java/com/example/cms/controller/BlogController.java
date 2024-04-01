package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogController {
	
	public BlogService blogService;
	
	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(@RequestBody @Valid BlogRequest blogRequest, @PathVariable int userId){
		return blogService.createBlog(blogRequest, userId);
	}
	
	@GetMapping("/titles/{title}/blogs")
	public ResponseEntity<Boolean> checkBlogTitle(@PathVariable String title){
		return blogService.checkBlogByTitle(title);
	}
	
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(@PathVariable int blogId)
	{
		return blogService.findBlogById(blogId);
	}
	
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(@PathVariable int blogId,@RequestBody @Valid BlogRequest bolgRequest)
	{
		return blogService.updateBlogData(blogId,bolgRequest);
	}
	
	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributors(@PathVariable int userId,@PathVariable int panelId)
	{
		return blogService.addContributors(userId,panelId);
	}
	
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId,@PathVariable int panelId){
		return blogService.deleteUser(userId,panelId);
	}


	

}
