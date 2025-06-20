package com.animal.api.auth.model.vo;

import lombok.Data;

@Data
public class ShelterVO {
	
	private int userIdx;
	private Integer shelterTypeIdx;
	private String shelterTypeName;
	private Integer shelterTel;
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
