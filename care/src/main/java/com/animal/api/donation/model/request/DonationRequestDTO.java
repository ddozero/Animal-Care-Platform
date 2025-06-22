package com.animal.api.donation.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestDTO {
	@NotNull(message = "회원번호는 필수입니다.")
	@Positive(message = "회원번호는 0이상이어야 합니다.")
	private Integer userIdx;
	@NotNull(message = "기부번호는 필수입니다.")
	@Positive(message = "기부번호는 0이상이어야 합니다.")
	private Integer donationIdx;
	@NotNull(message = "기부금액은 필수입니다.")
	@Positive(message = "기부금액은 0이상이어야 합니다.")
	private Integer donatedAmount;
}
