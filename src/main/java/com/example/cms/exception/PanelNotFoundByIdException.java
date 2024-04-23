package com.example.cms.exception;

public class PanelNotFoundByIdException extends RuntimeException  {
	private String message;

	public PanelNotFoundByIdException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

}
