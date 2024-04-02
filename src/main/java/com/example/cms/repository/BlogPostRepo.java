package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.entity.BlogPost;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Integer>{

	public boolean existsByTitle(String title);


}
