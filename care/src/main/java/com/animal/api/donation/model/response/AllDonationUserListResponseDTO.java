package com.animal.api.donation.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllDonationUserListResponseDTO {
	private int idx;
	private int donatedAmount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private String nickname;
}
