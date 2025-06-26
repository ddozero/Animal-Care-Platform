package com.animal.api.admin.shelter.model.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private int status; // 0 : 요청 / 1 : 승인 / -1 : 탈퇴
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;
	private String filePath;
}
