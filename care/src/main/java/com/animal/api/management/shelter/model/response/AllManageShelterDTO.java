package com.animal.api.management.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllManageShelterDTO {

	private int userIdx;
	private int typeIdx;
	private String tel;
	private String name;
	private String personName;
	private int zipCode;
	private String address;
	private String addressDetail;
	private String email;
	private String description;
	private String businessNumber;
	private String businessFile;

}
