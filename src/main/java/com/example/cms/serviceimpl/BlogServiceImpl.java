package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;
import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundByIdException;
import com.example.cms.exception.TopicsNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
	
	private BlogRepo blogRepository;
	private ResponseStructure<BlogResponse> structure;
	private UserRepository userRepository;
	private ContributionPanelRepository contributionPanelRepo;
	private ResponseStructure<ContributionPanel> panelStructure;
	private ResponseStructure<UserResponse> userResponseStructure;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(BlogRequest blogRequest, int userId) {
		return userRepository.findById(userId).map(user ->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new BlogAlreadyExistsByTitleException("Failed to create a blog");
			if(blogRequest.getTopics().length<1)
				throw new TopicsNotSpecifiedException("Failed to create a blog");
		Blog blog = mapToBlogEntity(blogRequest, new Blog());
				blog.setUser(user);
				blog = blogRepository.save(blog);
		return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value()).setMessage("Blog created successfully!!")
				.setData(mapToBlogResponse(blog)));
	}).orElseThrow(()-> new UserNotFoundByIdException("Failed to create a blog"));
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

	@Override
	public ResponseEntity<Boolean> checkBlogByTitle(String title) {
		Boolean res= blogRepository.existsByTitle(title);

		return new  ResponseEntity<Boolean>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		return blogRepository.findById(blogId)
				.map(blog -> ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("blog Fetched ")
						.setData(mapToBlogResponse(blog))))
				.orElseThrow(()-> new BlogNotFoundByIdException("Failed to Fetch blog"));

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(int blogId, BlogRequest blogRequest) {
		return blogRepository.findById(blogId).map(blog->{
			Blog blog1=mapToBlogEntity(blogRequest,new Blog());
			blog1.setBlogId(blog.getBlogId());
			blog1=blogRepository.save(blog1);
			return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
					.setMessage("blog created successfully")
					.setData(mapToBlogResponse(blog)));

		}).orElseThrow(()-> new BlogNotFoundByIdException("Failed to create blog"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributors(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			return contributionPanelRepo.findById(panelId).map(panel->{
				if(!blogRepository.existsByUserAndContributionPanel(owner,panel))
					throw new IllegalAccessRequestException("Failed to add contributor");
				return userRepository.findById(userId).map(contributor->{
					panel.getContributors().add(contributor);
					contributionPanelRepo.save(panel);
					return ResponseEntity.ok(panelStructure.setStatus(HttpStatus.OK.value())
							.setMessage("contributor added successfully")
							.setData(panel));
				}).orElseThrow(()-> new UserNotFoundByIdException("Failed to add contributor"));
			}).orElseThrow(()->new PanelNotFoundByIdException("Failed to fetch panel"));
		}).get();
	}
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			return contributionPanelRepo.findById(panelId).map(panel->{
				if(!blogRepository.existsByUserAndContributionPanel(owner,panel))
					throw new IllegalAccessRequestException("Failed to delete user");
			return userRepository.findById(userId).map(user->{
					panel.getContributors().remove(user);
					contributionPanelRepo.save(panel);
					return ResponseEntity.ok(userResponseStructure.setStatus(HttpStatus.OK.value())
							.setMessage("user deleted successfully")
							.setData(mapToUserResponse(user)));
			}).orElseThrow(()-> new UserNotFoundByIdException("Failed to add contributor"));
			}).orElseThrow(()->new PanelNotFoundByIdException("Failed to fetch panel"));
		}).get();
			
			
	}

	private UserResponse mapToUserResponse(User user) {
		UserResponse userResponse=new UserResponse();
		userResponse.setUserId(user.getUserId());
		userResponse.setUsername(user.getUsername());
		userResponse.setEmail(user.getEmail());
		userResponse.setCreatedAt(user.getCreatedAt());
		userResponse.setLastModifiedAt(user.getLastModifiedAt());
		userResponse.setDeleted(user.isDeleted());
		return userResponse;
	}


}
