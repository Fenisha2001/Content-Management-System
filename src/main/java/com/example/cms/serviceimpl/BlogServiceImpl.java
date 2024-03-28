package com.example.cms.serviceimpl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogServiceImpl implements BlogService {
	
	private BlogRepo blogRepo;
	private ResponseStructure<BlogResponse> structure;
	private UserRepository userRepository;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(BlogRequest blogRequest, int userId) {
		if(blogRepo.existsByTitle(blogRequest.getTitle()))
			throw new BlogAlreadyExistsByTitleException("Failed to create a blog");
		return userRepository.findById(userId).map(user ->{
		Blog uniqueBlog = blogRepo.save(mapToBlogEntity(blogRequest, new Blog()));
		user.setBlogs(Arrays.asList(uniqueBlog));
		userRepository.save(user);
		return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value()).setMessage("Blog created successfully!!")
				.setData(mapToBlogResponse(uniqueBlog)));
	}).orElseThrow(()-> new UserNotFoundByIdException("Invalid userId"));
	}

	private BlogResponse mapToBlogResponse(Blog blog) {
		BlogResponse blogResponse = new BlogResponse();
		blogResponse.setBlogId(blog.getBlogId());
		blogResponse.setTitle(blog.getTitle());
		blogResponse.setTopics(blog.getTopics());
		blogResponse.setAbout(blog.getAbout());
		return blogResponse;
	}

	private Blog mapToBlogEntity(BlogRequest blogRequest, Blog blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

}
