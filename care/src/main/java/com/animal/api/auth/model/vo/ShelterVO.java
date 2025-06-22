package com.animal.api.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterVO {
	
	private Integer userIdx;
	private Integer shelterTypeIdx;
	private String shelterTypeName;
	private String shelterTel;
	private String shelterName;
	private String shelterPersonName;
	private Integer shelterZipCode;
	private String shelterAddress;
	private String shelterAddressDetail;
	private String shelterEmail;
	private String shelterDescription;
	private String shelterBusinessNumber;
	private Integer shelterBusinessFile;
	
}
