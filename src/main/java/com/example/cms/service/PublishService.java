package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId, PublishRequest publishRequest);

}
