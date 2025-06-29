package com.animal.api.management.volunteers.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterVolunteerApplicationDetailResponseDTO {
	private int idx;
	private int volunteerIdx;
	private int userIdx;
	private int volunteerRequestStatusIdx;
	private String volunteerRequestStatusName;
	private String type;
	private String name;
	private String email;
	private String tel;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp birthDate;
	private String groupName;
	private String groupTel;
	private String description;
	private int male;
	private int female;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
}
