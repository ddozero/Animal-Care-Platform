package com.animal.api.management.shelter.model.response;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterBoardResponseDTO {
	
	private int idx;
	private int userIdx;
	private int boardTypeIdx;
	private String title;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private int views;
	private int ref;
	private int lev;
	private int turn;
	private String filesPath;	
	private List<String> imagePath;
	
}
