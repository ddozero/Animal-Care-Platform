package com.animal.api.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterDetailResponseDTO {
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
	private String imagePath;
}
