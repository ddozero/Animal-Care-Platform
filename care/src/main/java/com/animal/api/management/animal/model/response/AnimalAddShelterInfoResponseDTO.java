package com.animal.api.management.animal.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalAddShelterInfoResponseDTO {
	private int userIdx;
	private String shelterName;
	private String shelterType;
	private int shelterZipCode;
	private String shelterAddress;
	private String shelterAddressDetail;
	private String shelterTel;
	private String shelterEmail;
	private String shelterPersonName;
}
