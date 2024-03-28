package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.Blog;

public interface BlogRepo extends JpaRepository<Blog, Integer> {

	boolean existsByTitle(String title);

}
