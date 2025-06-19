package com.animal.api.donation.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllDonationListResponseDTO {
	
	private int idx;
	private String name;
	private String sponser;
	private int completionRate;
	private int completionAmount;
	private String status;

}
