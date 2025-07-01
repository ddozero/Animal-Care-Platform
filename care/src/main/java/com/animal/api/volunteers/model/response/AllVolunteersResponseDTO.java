package com.animal.api.volunteers.model.response;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllVolunteersResponseDTO {

	private int idx;
	private String title;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate volunteerDate;
	private int time;
	private String location;
	private int capacity;
	private int applicants;
	private String content;
	private String ageTarget;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private String type;
	private String status;
	private int userIdx;
	private String shelter;
	private String shelterType;
	private List<String> imagePaths;
	private List<String> filePaths;

}
