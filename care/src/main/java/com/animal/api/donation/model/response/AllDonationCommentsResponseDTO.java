package com.animal.api.donation.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllDonationCommentsResponseDTO {
	private int idx;
	private String nickname;
	private String content;
	private Timestamp createAt;
}
