package com.animal.api.donation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestDTO {
	private int userIdx;
	private int donationIdx;
	private int donatedAmount;
}
