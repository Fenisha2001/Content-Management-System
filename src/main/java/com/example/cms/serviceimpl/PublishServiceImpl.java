package com.example.cms.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogAlreadyScheduledException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.repository.BlogPostRepo;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.PublishRepo;
import com.example.cms.repository.ScheduleRepo;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.requestdto.ScheduleRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService {
	
	private BlogPostRepo blogPostRepo;
	private BlogRepo blogRepo;
	private UserRepository userRepo;
	private PublishRepo publishRepo;
	private ResponseStructure<BlogPostResponse> blogStructure;
	private ResponseStructure<PublishResponse> structure;
	private ScheduleRepo scheduleRepo;

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId,
			PublishRequest publishRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).map(user->{
			return blogPostRepo.findById(postId).map(blogPost->{
				if(!blogRepo.existsByUser(user))
					throw new IllegalAccessRequestException("Failed to publish BlogPost");
				Publish publish = null;
				if (blogPost.getPublish()!=null) {
					 publish = mapToPublishEntity(publishRequest, blogPost.getPublish());
						Schedule schedule;
						if(publish.getSchedule()!=null) {
							schedule = publish.getSchedule();
							mapToScheduleEntity(publishRequest.getSchedule(), schedule);
						}else {
							schedule = mapToScheduleEntity(publishRequest.getSchedule(), new Schedule());
		                    blogPost.setPostType(PostType.SCHEDULED);
						}
						scheduleRepo.save(schedule);
		                publish.setSchedule(schedule);
				}
				else {
					 publish = mapToPublishEntity(publishRequest, new Publish());
				}
				if(publishRequest.getSchedule()!=null) {
					if(!publishRequest.getSchedule().getDateTime().isAfter(LocalDateTime.now()))
						throw new BlogAlreadyScheduledException("Blog failed to schedule");
					
					Schedule schedule = mapToScheduleEntity(publishRequest.getSchedule(), new Schedule());
	                scheduleRepo.save(schedule);
	                publish.setSchedule(schedule);
	                blogPost.setPostType(PostType.SCHEDULED);
//					publish.setSchedule(scheduleRepo.save(mapToScheduleEntity(publishRequest.getSchedule(),new Schedule())));
//					blogPost.setPostType(PostType.SCHEDULED);
				}
				else {
					blogPost.setPostType(PostType.PUBLISHED);
				}
				publish.setBlogPost(blogPost);
				blogPost.setPublish(publish);
				blogPostRepo.save(blogPost);
				publishRepo.save(publish);			
				return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost Published successfully")
						.setData(mapToPublishResponse(publish)));

			}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed To publish BlogPost"));

		}).get();

	}

	private Schedule mapToScheduleEntity(ScheduleRequest scheduleRequest, Schedule schedule) {
		schedule.setDateTime(scheduleRequest.getDateTime());
		return schedule;
	}

	private Publish mapToPublishEntity(PublishRequest publishRequest,Publish publish) {
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTopics(publishRequest.getSeoTopics());
		return publish;

	}
	 
	private PublishResponse mapToPublishResponse(Publish publish)
	{
		PublishResponse publishResponse=new PublishResponse();
		publishResponse.setPublishId(publish.getPublishId());
		publishResponse.setSeoTitle(publish.getSeoTitle());
		publishResponse.setSeoDescription(publish.getSeoDescription());
		publishResponse.setSeoTopics(publish.getSeoTopics());
//		publishResponse.setCreatedAt(publish.getCreatedAt());
//		publishResponse.setCreatedBy(publish.getCreatedBy());
//		publishResponse.setBlogPost(publish.getBlogPost());
		return publishResponse;
	}



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishBlogPost(int postId) {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByEmail(email).map(user->{
			return blogPostRepo.findById(postId).map(blogPost->{
				if(!blogRepo.existsByUser(user))
					throw new IllegalAccessRequestException("Failed to unpublish BlogPost");
				if(blogPost.getPostType().equals(PostType.PUBLISHED))
				blogPost.setPostType(PostType.DRAFT);
				blogPostRepo.save(blogPost);
				return ResponseEntity.ok(blogStructure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost Unpublished successfully")
						.setData(mapToBlogPostResponse(blogPost)));

			}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed To unpublish BlogPost"));

		}).get();
				
				}
	
	private BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		BlogPostResponse blogPostResponse = new BlogPostResponse();
		blogPostResponse.setPostId(blogPost.getPostId());
//		blogPostResponse.setPostType(blogPost.getPostType());
		blogPostResponse.setTitle(blogPost.getTitle());
		blogPostResponse.setSubtitle(blogPost.getSubTitle());
//		blogPostResponse.setCreatedAt(blogPost.getCreatedAt());
//		blogPostResponse.setLastModifiedAt(blogPost.getLastModifiedAt());
//		blogPostResponse.setCreatedBy(blogPost.getCreatedBy());
//		blogPostResponse.setLastModifiedBy(blogPost.getLastModifiedBy());
		if(blogPost.getBlog()!= null)
			blogPostResponse.setBlogResponse(mapToBlogResponse(blogPost.getBlog()));
		if(blogPost.getPublish()!=null)
			blogPostResponse.setPublishResponse(mapToPublishResponse(blogPost.getPublish()));
		
		return blogPostResponse;
	}
	   	
	private BlogResponse mapToBlogResponse(Blog blog) {
		BlogResponse blogResponse = new BlogResponse();
		blogResponse.setBlogId(blog.getBlogId());
		blogResponse.setTitle(blog.getTitle());
		blogResponse.setTopics(blog.getTopics());
		blogResponse.setAbout(blog.getAbout());
		return blogResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByBlogPostId(int postId) {
			return blogPostRepo.findById(postId).map(blogPost->{
				return ResponseEntity.ok(blogStructure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost fetched successfully")
						.setData(mapToBlogPostResponse(blogPost)));

			}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed to fetch BlogPost"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByBlogPostIdByPosttype(int postId) {
		return blogPostRepo.findByPostIdAndPostType(postId, PostType.PUBLISHED).map(blogPost->
		ResponseEntity.status(HttpStatus.FOUND)
				.body(blogStructure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost fetched successfully")
						.setData(mapToBlogPostResponse(blogPost)))

	).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed to fetch BlogPost"));
	}		
	
	@Scheduled(fixedDelay = 60*1000l)
	public void publishScheduledBlogPosts()
	{
		List<BlogPost> blogPosts= blogPostRepo.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),
				PostType.SCHEDULED).stream().map(blogPost->{
					blogPost.setPostType(PostType.PUBLISHED);
					return blogPost;
				}).collect(Collectors.toList());
		blogPostRepo.saveAll(blogPosts);
	}
}
