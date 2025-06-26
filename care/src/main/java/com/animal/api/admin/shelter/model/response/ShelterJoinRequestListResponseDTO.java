package com.animal.api.admin.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterJoinRequestListResponseDTO {
	private int idx;
	private String id;
	private String email;
	private String password;
	private String nickname;
	private String shelterType;
	private String shelterName;
	private String shelterTel;
	private String shelterPersonName;
	private int shelterZipCode;
	private String shelterAddress;
	private String shelterAddressDetail;
	private String shelterEmail;
	private String shelterBusinessNumber;
}
