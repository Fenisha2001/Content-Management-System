package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicsNotSpecifiedException extends RuntimeException {
     
	private String message;
}
