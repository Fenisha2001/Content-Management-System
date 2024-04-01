package com.example.cms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Getter
//@Setter
//@Builder
public class ContributionPanel {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int panelId;
	
	@ManyToMany
	private List<User> contributers=new ArrayList<>();
	
	public int getPanelId() {
		return panelId;
	}
	public void setPanelId(int panelId) {
		this.panelId = panelId;
	}
	public List<User> getContributers() {
		return contributers;
	}
	public void setContributers(List<User> contributers) {
		this.contributers = contributers;
	}


}
