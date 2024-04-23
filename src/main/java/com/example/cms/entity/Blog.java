package com.example.cms.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogs")
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	
//	@ManyToMany
//	private List<User> user;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "blog")
	private List<BlogPost> blogPosts;
	
	@OneToOne
	private ContributionPanel contributionPanel;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ContributionPanel getContributionPanel() {
		return contributionPanel;
	}

	public void setContributionPanel(ContributionPanel contributionPanel) {
		this.contributionPanel = contributionPanel;
	}

	public List<BlogPost> getBlogPosts() {
		return blogPosts;
	}

	public void setBlogPosts(List<BlogPost> blogPosts) {
		this.blogPosts = blogPosts;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
	
	

}
