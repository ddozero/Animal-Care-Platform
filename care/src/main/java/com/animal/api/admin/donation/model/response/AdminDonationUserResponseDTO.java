package com.animal.api.admin.donation.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructors
public class AdminDonationUserResponseDTO {

	private int idx;
	private int userIdx;
	private int donationIdx; // 지원사업 idx
	private int donatedAmount; // 후원금액
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;

}
