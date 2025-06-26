package com.animal.api.admin.donation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDonationSearchRequestDTO {

	private int cp;
	private int listSize;
	private String name;
	private String status;

}
