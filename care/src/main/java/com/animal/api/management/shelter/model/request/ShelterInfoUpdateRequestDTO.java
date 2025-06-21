package com.animal.api.management.shelter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterInfoUpdateRequestDTO {

	private int idx;
	private String type;
	private String tel;
	private String shelterName;
	private String personName;
	private int zipCode;
	private String address;
	private String addressDetail;
	private String email;
	private String description;
	private String businessNumber;
	private String businessFile;

}
