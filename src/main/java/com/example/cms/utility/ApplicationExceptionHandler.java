package com.example.cms.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.UserAlreadyExistByEmailException;

@RestControllerAdvice
//@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
private ErrorStructure<String> structure;
	

	public ApplicationExceptionHandler(ErrorStructure<String> structureList) {
		super();
		this.structure = structureList;
	}

	@ExceptionHandler(UserAlreadyExistByEmailException.class)
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyByEmailException(UserAlreadyExistByEmailException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST,ex.getMessage(),"User already exists with the given email ID");
	}

	private ResponseEntity<ErrorStructure<String>> errorStructure(HttpStatus status, String errorMessage,
			String rootCause) {

			return new ResponseEntity<ErrorStructure<String>>(structure.setStatus(status.value())
				.setErrorMessage(errorMessage)
				.setRootCause(rootCause),HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
			Map<String, String> messages=new HashMap<>();
			ex.getAllErrors().forEach(error->{

			messages.put(((FieldError)error).getField(),error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(structure.setStatus(HttpStatus.BAD_REQUEST.value())
				.setErrorMessage("Invalid inputs")
				.setRootCause(messages));
	}
}
 
//	private ErrorStructure<String> structure;
//
//	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status,String message,String rootCause)
//	{
//		return new ResponseEntity<ErrorStructure<String>>(structure.setErrorStatus(status.value())
//				.setErrorMessage(message)
//				.setRootCause(rootCause),status);
//	}
//	
//	@ExceptionHandler(UserAlreadyExistByEmailException.class)
//	public ResponseEntity<ErrorStructure<String>> handlerUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
//	{
//		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"User already exists with the given email Id");
//	}

//	public ApplicationExceptionHandler(ErrorStructure<Object> structureList) {
//		super();
//		this.structureList = structureList;
//	}
	
//	@ExceptionHandler()
//	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
//	{
//		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "User Already exists with the given email ID");
//		return ResponseEntity.badRequest().body(structureList.setErrorStatus(HttpStatus.BAD_REQUEST.value())
//				.setErrorMessage(ex.getMessage())
//				.setRootCause("Email id already exists"));
//	}

//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//		Map<String, String> messages=new HashMap<>();
//		ex.getAllErrors().forEach(error->{
//
//			messages.put(((FieldError)error).getField(),error.getDefaultMessage());
//		});
//		return ResponseEntity.badRequest().body(structureList.setErrorStatus(HttpStatus.BAD_REQUEST.value())
//				.setErrorMessage("Invalid inputs")
//				.setRootCause(messages));
	

