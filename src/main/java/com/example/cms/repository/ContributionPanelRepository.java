package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;

public interface ContributionPanelRepository extends JpaRepository<ContributionPanel, Integer> {

//	Object existsByContributors(User user);

	boolean existsByPanelIdAndContributors(int panelId, User user);

}
