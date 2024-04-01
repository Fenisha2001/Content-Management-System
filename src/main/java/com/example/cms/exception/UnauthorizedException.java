package com.example.cms.exception;

public class UnauthorizedException extends RuntimeException {
	private String message;@Override
	public String getMessage() {
		return message;
	}
	public UnauthorizedException(String message) {
		super();
		this.message = message;
	}
	

}
