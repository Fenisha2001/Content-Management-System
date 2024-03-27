package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.LoginRequest;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	private ResponseStructure<UserResponse> structure;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, ResponseStructure<UserResponse> structure,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.structure = structure;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@Valid UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Failed to register User");

		User uniqueUser=userRepository.save(mapToUserEntity(userRequest, new User()));
		
		return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value()).setMessage("User registered successfully")
				.setData(mapToUserResponse(uniqueUser)));
	}
	
	private UserResponse mapToUserResponse(User user) {
	    UserResponse userResponse = new UserResponse();
	    userResponse.setUserId(user.getUserId());
	    userResponse.setUsername(user.getUsername());
	    userResponse.setEmail(user.getEmail());
	     userResponse.setCreatedAt(user.getCreatedAt());
	     userResponse.setLastModifiedAt(user.getLastModifiedAt());
	    return userResponse;
	}


//	private UserResponse mapToUserResponse(User user) {
////		return UserResponse.builder()
//				.userId(user.getUserId())
//				.username(user.getUsername())
//				.email(user.getEmail())
////				.createAt(user.getCreatedAt())
////				.lastModifiedAt(user.getLastModifiedAt())
////				.build();
//	}

	private User mapToUserEntity(UserRequest userRequest, User user) {
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//		user.setPassword(userRequest.getPassword());
		user.setUsername(userRequest.getUsername());
		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> loginUser(@Valid LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		return null;
	}
		

}