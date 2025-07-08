package com.animal.api.admin.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDatailResopnseDTO {

	// USERS
	private int userIdx;
	private String id;
	private String email;
	private String nickname;
	private String name;
	private String tel;
	private String gender;
	private String birthDate;
	private int zipCode;
	private String address;
	private String addressDetail;
	private int point;
	private int userType;
	private int status;
	private String createdAt;
	private int locked;
	private int lockCount;
	private String lockedAt;
	
    // SHELTERS (userType = 2일 때만)
	private String shelterName;
	private String shelterTel;
	private String shelterPersonName;
	private String shelterEmail;
	private String shelterBusinessNumber;
	private String shelterAddress;
	private String shelterAddressDetail;
	private String shelterDescription;

}
