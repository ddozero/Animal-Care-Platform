package com.animal.api.auth.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

	// USERS 테이블 공통
	private int idx;
	private int userTypeIdx;
	private String userTypeName;

	private String id;
	private String email;
	private String name;
	private String nickname;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	private String gender;
	private int tel;

	private int zipCode;
	private String address;
	private String addressDetail;

	private int point;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime updatedAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime lastLoginAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime withdrawnAt;

	private int status;
	private int locked;
	private int lockCount;
	private LocalDateTime lockedAt;

	// 보호소들 전용
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
