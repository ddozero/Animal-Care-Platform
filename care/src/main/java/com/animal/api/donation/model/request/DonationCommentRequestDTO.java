package com.animal.api.donation.model.request;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonationCommentRequestDTO {
	private int userIdx;
	private int donationIdx;
	private int content;
}
