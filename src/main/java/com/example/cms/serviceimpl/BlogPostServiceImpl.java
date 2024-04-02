package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogPostRepo;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
public class BlogPostServiceImpl implements BlogPostService {
	
	private BlogPostRepo blogPostRepo;
	private BlogRepo blogRepo;
	private ResponseStructure<BlogPostResponse> structure;
	private UserRepository userRepo;
	private ContributionPanelRepository panelRepo;

public BlogPostServiceImpl(BlogPostRepo blogPostRepo, BlogRepo blogRepo,
			ResponseStructure<BlogPostResponse> structure, UserRepository userRepo,
			ContributionPanelRepository panelRepo) {
		super();
		this.blogPostRepo = blogPostRepo;
		this.blogRepo = blogRepo;
		this.structure = structure;
		this.userRepo = userRepo;
		this.panelRepo = panelRepo;
	}

//	public BlogPostServiceImpl(BlogPostRepo blogPostRepo, ResponseStructure<BlogPostResponse> structure) {
//		super();
//		this.blogPostRepo = blogPostRepo;
//		this.structure = structure;
//	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId, BlogPostRequest blogPostRequest) {
		if(!validateUser(blogId))
			throw new IllegalAccessRequestException("Failed to create draft");
		if(blogPostRepo.existsByTitle(blogPostRequest.getTitle()))
			throw new BlogAlreadyExistsByTitleException("Failed to create draft");
		return blogRepo.findById(blogId).map(blog->{
			BlogPost blogPost = mapToBlogPostEntity(blogPostRequest, new BlogPost());
			blogPost.setBlog(blog);
			blogPost.setPostType(PostType.DRAFT);
			blogPost = blogPostRepo.save(blogPost);
			blog.getBlogPosts().add(blogPost);
			blogRepo.save(blog);
			return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value()).setMessage("Draft created successfully!!")
					.setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(()-> new BlogNotFoundByIdException("Failed to fetch blog"));
		}
	
	private BlogPost mapToBlogPostEntity(BlogPostRequest blogPostRequest, BlogPost blogPost) {
		blogPost.setTitle(blogPostRequest.getTitle());
		blogPost.setSubTitle(blogPostRequest.getSubtitle());
		blogPost.setSummary(blogPostRequest.getSummary());
		return blogPost;		
	}
	
	private BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		BlogPostResponse blogPostResponse = new BlogPostResponse();
		blogPostResponse.setPostId(blogPost.getPostId());
		blogPostResponse.setPostType(blogPost.getPostType());
		blogPostResponse.setTitle(blogPost.getTitle());
		blogPostResponse.setSubtitle(blogPost.getSubTitle());
		blogPostResponse.setCreatedAt(blogPost.getCreatedAt());
		blogPostResponse.setLastModifiedAt(blogPost.getLastModifiedAt());
		blogPostResponse.setCreatedBy(blogPost.getCreatedBy());
		blogPostResponse.setLastModifiedBy(blogPost.getLastModifiedBy());
		
		return blogPostResponse;
	}
	
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,
			BlogPostRequest blogPostRequest) {
		return blogPostRepo.findById(postId).map(blogPost->{
			if(!validateUser(blogPost.getBlog().getBlogId()))
				throw new IllegalAccessRequestException("Failed to update Draft");
			BlogPost updatedBlogPost=mapToBlogPostEntity(blogPostRequest,new BlogPost());
			updatedBlogPost.setPostId(blogPost.getPostId());
			updatedBlogPost.setPostType(blogPost.getPostType());
			updatedBlogPost.setCreatedAt(blogPost.getCreatedAt());
			updatedBlogPost.setCreatedBy(blogPost.getCreatedBy());
			updatedBlogPost=blogPostRepo.save(updatedBlogPost);
			return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
					.setMessage("BlogPost updated successfully")
					.setData(mapToBlogPostResponse(updatedBlogPost)));

		}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed to update Draft"));	
		

	}

	public boolean validateUser(int blogId)
	{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).map(user->{
			return blogRepo.findById(blogId).map(blog->{
				if(blog.getUser().getEmail().equals(email)
						|| panelRepo.existsByPanelIdAndContributors(blog.getContributionPanel().getPanelId(),user))
					return true;
				else
					return false;
			}).orElseThrow(()-> new BlogNotFoundByIdException("Failed to validate user"));
		}).orElseThrow(()-> new UserNotFoundByIdException("Failes to validate User"));

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteDraft(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).map(owner->{
			return blogPostRepo.findById(postId).map(blogPost->{
				Blog blog = blogPost.getBlog();
				if(!blogRepo.existsByUser(owner))
					throw new IllegalAccessRequestException("Failed to delete BlogPost");
				blogPostRepo.delete(blogPost);
				return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost deleted successfully")
						.setData(mapToBlogPostResponse(blogPost)));
			}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed to delete BlogPost"));
				
			}).get();
	}
}



