package com.animal.api.animal.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdoptionAnimalResponseDTO {
	private int idx;
	private String name;
	private char gender;
	private int age;
	private int size;
	private int neuter;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private String breed;
	private String type;
	private String personality;
	private int adoptionStatusIdx;
	private String shelterName;
}
