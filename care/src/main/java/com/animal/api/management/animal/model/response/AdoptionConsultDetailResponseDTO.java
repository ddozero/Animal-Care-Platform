package com.animal.api.management.animal.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionConsultDetailResponseDTO {
	private int idx;
	private int userIdx;
	private int animalIdx;
	private String name;
	private String email;
	private String tel;
	private int zipCode;
	private String address;
	private String addressDetail;
	private boolean hasPet;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp consultedAt;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private int statusIdx;
	private String status;

}
