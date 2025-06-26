package com.animal.api.admin.donation.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAllDonationResponseDTO {

	private int idx;
	private int userIdx;
	private int statusIdx;

	private String name;
	private String status;
	private int amount;
	private int completionAmount;
	private int completionRate;
	private Timestamp startDate;
	private Timestamp endDate;
	private String sponspr;
	private String sponsor;
	private String sponsorDetail;
	private String content;

}
