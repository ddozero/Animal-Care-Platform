package com.animal.api.donation.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonationDetailResponseDTO {
	private int idx;
	private String name;
	private Timestamp startDate;
	private Timestamp endDate;
	private int amount;
	private int completionAmount;
	private int completionRate;
	private String sponsor;
	private String sponsorDetail;
	private String content;
	private String status;
}
