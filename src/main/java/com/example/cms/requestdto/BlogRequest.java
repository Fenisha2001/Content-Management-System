package com.example.cms.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {
	
	@NotNull(message = "Title should not be null")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]*$")
	private String title;
	
	@NotNull(message = "Atleast one topic should be specified")
	private String[] topics;
	
	private String about;

}
