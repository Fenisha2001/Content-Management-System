package com.example.cms.utility;

import org.springframework.stereotype.Component;

//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
@Component
public class ErrorStructure<T> {
	
	private int status;
	private String errorMessage;
	private T rootCause;
	public int getStatus() {
		return status;
	}
	public ErrorStructure setStatus(int status) {
		this.status = status;
		return this;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public ErrorStructure setErrorMessage(String errormessage) {
		this.errorMessage = errormessage;
		return this;
	}
	public Object getRootCause() {
		return rootCause;
	}
	public ErrorStructure setRootCause(T rootCause) {
		this.rootCause = rootCause;
		return this;
	}
	
//	private int errorStatus;
//	private String errorMessage;
//	private T rootCause;
//	
////	public int getErrorStatus() {
////		return errorStatus;
////	}
//	public ErrorStructure<T> setErrorStatus(int errorStatus) {
//		this.errorStatus = errorStatus;
//		return this;
//	}
////	public String getErrorMessage() {
////		return errorMessage;
////	}
//	public ErrorStructure<T> setErrorMessage(String errorMessage) {
//		this.errorMessage = errorMessage;
//		return this;
//	}
////	public Object getRootCause() {
////		return rootCause;
////	}
//	public ErrorStructure<T> setRootCause(T rootCause) {
//		this.rootCause = rootCause;
//		return this;
//	}
	
	

	
	

}
