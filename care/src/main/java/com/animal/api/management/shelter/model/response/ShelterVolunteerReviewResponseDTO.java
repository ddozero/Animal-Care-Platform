package com.animal.api.management.shelter.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterVolunteerReviewResponseDTO {
	
	private int reviewIdx;
	private int reviewContent;
	private Timestamp reviewDate;
	private String reviewWriter;
	private String volunteerTitle;
	

}
