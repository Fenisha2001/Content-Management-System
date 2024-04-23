package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.User;
import com.example.cms.requestdto.LoginRequest;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

//	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest userReq);

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> loginUser(@Valid LoginRequest loginRequest);

	ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(int userId);

}
