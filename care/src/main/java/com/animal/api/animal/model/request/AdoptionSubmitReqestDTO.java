package com.animal.api.animal.model.request;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdoptionSubmitReqestDTO {
	private int userIdx;
	private int animalIdx;
	private String name;
	private String email;
	private String tel;
	private int zipCode;
	private String adress;
	private String adressDetail;
	private int hasPet;
	private String description;
}
