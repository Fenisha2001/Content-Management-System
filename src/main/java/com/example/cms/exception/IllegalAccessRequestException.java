package com.example.cms.exception;

public class IllegalAccessRequestException extends RuntimeException {
	private String message;
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
	public IllegalAccessRequestException(String message) {
		super();
		this.message = message;
	}

}
