package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.repository.BlogPostRepo;
import com.example.cms.repository.BlogRepo;
import com.example.cms.repository.PublishRepo;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
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
	private ResponseStructure<PublishResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId,
			PublishRequest publishRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).map(user->{
			return blogPostRepo.findById(postId).map(blogPost->{
				if(!blogRepo.existsByUser(user))
					throw new IllegalAccessRequestException("Failed to publish BlogPost");
				Publish publish=mapToPublishEntity(publishRequest,new Publish());
				publish.setBlogPost(blogPost);
				publishRepo.save(publish);
				blogPost.setPostType(PostType.PUBLISHED);
				blogPostRepo.save(blogPost);
				return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
						.setMessage("BlogPost Published successfully")
						.setData(mapToPublishResponse(publish)));

			}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed To publish BlogPost"));

		}).get();

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
		publishResponse.setCreatedAt(publish.getCreatedAt());
		publishResponse.setCreatedBy(publish.getCreatedBy());
//		publishResponse.setBlogPost(publish.getBlogPost());
		return publishResponse;
	}


}
