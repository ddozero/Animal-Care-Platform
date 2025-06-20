package com.animal.api.animal.model.request;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionSubmitReqestDTO {
	private int userIdx;
	private int animalIdx;
	private String name;
	private String email;
	private String tel;
	private int zipCode;
	private String address;
	private String addressDetail;
	private int hasPet;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp consultedAt;
	private String description;

}
