package com.animal.api.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShelterDetailDTO {
	private int idx;
	private String shelterName;
	private String shelterTel;
	private String shelterPersonName;
	private String shelterAddress;
	private String shelterAddressDetail;
	private String shelterEmail;
	private String shelterDescription;
	private int animalCount;
	private int reviewCount;
	private String shelterType;
}
