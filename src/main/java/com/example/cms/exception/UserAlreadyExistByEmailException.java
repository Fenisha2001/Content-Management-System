package com.example.cms.exception;

//@SuppressWarnings("serial")
//@Getter
//@AllArgsConstructor
public class UserAlreadyExistByEmailException extends RuntimeException {
	
	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public UserAlreadyExistByEmailException(String message) {
		super();
		this.message = message;
	}
    
	
}
