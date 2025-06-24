package com.animal.api.mypage.information.screen.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentActivityDTO {
	private Timestamp createdAt;
	private String activityText;
}
