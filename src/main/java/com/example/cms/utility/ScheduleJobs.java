package com.example.cms.utility;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJobs {
	
	@Scheduled(fixedDelay = 1000l)
	public void logDateTime() {
//		System.out.println(LocalDateTime.now());
		
	}

}
