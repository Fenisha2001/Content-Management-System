package com.example.cms.requestdto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
	
	@Pattern(regexp = "\\d{2}-(0[1-9]|1[0-2])-\\d{4}")
	private LocalDateTime dateTime;

}
