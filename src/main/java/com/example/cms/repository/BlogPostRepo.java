package com.example.cms.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.User;
import com.example.cms.enums.PostType;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Integer>{

	public boolean existsByTitle(String title);

	public Optional<BlogPost> findByPostIdAndPostType(int postId, PostType published);

//	public List<BlogPost> findAllByPublishScheduledByCurrentTimeOrLess(LocalDateTime dateTime);

	public List<BlogPost> findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime now,
			PostType scheduled);


}
