package com.animal.api.animal.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDetailResponseDTO {
	private int idx;
	private int userIdx;
	private String name;
	private char gender;
	private int age;
	private int size;
	private int neuter;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private String description;
	private String breed;
	private String type;
	private String personality;
	private String adoptionStatus;
	private String shelterName;
	private String shelterPersonName;
	private String shelterTel;
	private int shelterZipCode;
	private String shelterAddress;
	private String shelterAddressDetail;
	private String imagePath;
}
