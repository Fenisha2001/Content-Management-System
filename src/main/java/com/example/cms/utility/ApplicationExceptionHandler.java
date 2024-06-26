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

import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostAlreadyExistByTitleException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundByIdException;
import com.example.cms.exception.TopicsNotSpecifiedException;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

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


	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundByIdException(UserNotFoundByIdException ex){

		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "User is not found by given Id");

	}

	@ExceptionHandler(BlogAlreadyExistsByTitleException.class)
	public ResponseEntity<ErrorStructure<String>> handleBlogAlreadyExistsByTitleException(BlogAlreadyExistsByTitleException ex){
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Blog already exists with the given title");

	}
	
	@ExceptionHandler(BlogNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handleBlogNotFoundByIdException(BlogNotFoundByIdException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Blog does not exists with the given blogId");
	}
	
	@ExceptionHandler(BlogPostAlreadyExistByTitleException.class)
	public ResponseEntity<ErrorStructure<String>> handleBlogPostAlreadyExistByTitleException(BlogPostAlreadyExistByTitleException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST,ex.getMessage(),"BlogPost already exists with the given title");
	}
	
	@ExceptionHandler(BlogPostNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handleBlogPostNotFoundByIdException(BlogPostNotFoundByIdException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "BlogPost does not exists with the given postId");
	}
	
	@ExceptionHandler(IllegalAccessRequestException.class)
	public ResponseEntity<ErrorStructure<String>> handleIllegalAccessRequestException(IllegalAccessRequestException ex){
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Blog already exists with the given title");

	}
	
	@ExceptionHandler(PanelNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handlePanelNotFoundByIdException(PanelNotFoundByIdException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "panel does not exists with the given panelId");
	}
	
	@ExceptionHandler(TopicsNotSpecifiedException.class)
	public ResponseEntity<ErrorStructure<String>> handleTopicsNotSpecifiedException(TopicsNotSpecifiedException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST,ex.getMessage(),"topics must not be null");
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



