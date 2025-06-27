package com.animal.api.admin.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDTO {

    private int userIdx;
    
    // USERS - 수정 가능 항목만
    private String nickname;
    private String name;
    private String tel;
    private String gender;
    private String birthDate;
    private Integer zipCode;
    private String address;
    private String addressDetail;
    private Integer point;
    private Integer status;
	
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
