package com.animal.api.auth.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
	
	// USERS 테이블 공통
    private int userIdx;
    private int userTypeIdx;
    private String userTypeName;

    private String id;
    private String email;
    private String name;
    private String nickname;

    private LocalDate birthDate;
    private String gender;
    private int tel;

    private int zipCode;
    private String address;
    private String addressDetail;

    private int point;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime withdrawnAt;

    private int status;
    private int locked;
    private int lockCount;
    private LocalDateTime lockedAt;

    // 보호소 전용 (SHELTERS + SHELTER_TYPES)
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
