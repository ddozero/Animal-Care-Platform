package com.animal.api.signup.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ShelterSignupRequestDTO {

    // USERS 테이블 정보
    private String id;
    private String email;
    private String password;

    private String name;
    private String nickname;

    private LocalDate birthDate;
    private String gender;
    private Integer tel;

    private Integer zipCode;
    private String address;
    private String addressDetail;

    // SHELTERS 테이블 공통
    private Integer shelterTypeIdx;
    private Integer shelterTel;
    private String shelterName;
    private String shelterPersonName;
    private Integer shelterZipCode;
    private String shelterAddress;
    private String shelterAddressDetail;
    private String shelterDescription;

    // 조건 분기
    private String shelterEmail;         // 공공(1)
    private String shelterBusinessNumber; // 공공(1), 민간유(2)
    private Integer shelterBusinessFile;  // 민간유(2)
	
}
