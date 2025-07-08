package com.animal.api.donation.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDonationListResponseDTO {
	private int idx;
	private String name;
	private String sponsor;
	private int completionRate;
	private int completionAmount;
	private String status;
	private String imagePath;
}
