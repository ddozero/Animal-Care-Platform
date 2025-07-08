package com.animal.api.index.volunteer.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainVolunteerSummaryDTO {

	private int idx;
	private String title;
	private String location;
	private LocalDateTime volunteerDate;
}
