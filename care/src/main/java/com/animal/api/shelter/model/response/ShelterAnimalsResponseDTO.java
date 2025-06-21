package com.animal.api.shelter.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterAnimalsResponseDTO {
	private int idx;
	private int userIdx;
	private String name;
	private char gender;
	private int age;
	private int size;
	private int neuter;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createAt;
	private String breed;
	private String type;
	private String personality;
	private String adoptionStatus;
}
