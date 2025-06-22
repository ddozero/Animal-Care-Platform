package com.animal.api.donation.model.request;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestDTO {
	@Positive(message = "회원번호는 0이상이어야 합니다.")
	private int userIdx;
	@Positive(message = "기부번호는 0이상이어야 합니다.")
	private int donationIdx;
	@Positive(message = "기부금액은 0이상이어야 합니다.")
	private int donatedAmount;
}
