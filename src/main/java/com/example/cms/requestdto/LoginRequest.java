package com.example.cms.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	@NotBlank(message = "must not be null")
//	@NotNull(message = "must not be null")
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid emailId")
	@NotBlank(message = "Email must not be null")
    @Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Password must not be null")
//	@NotNull(message = "must not be null")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
	private String password;


}
