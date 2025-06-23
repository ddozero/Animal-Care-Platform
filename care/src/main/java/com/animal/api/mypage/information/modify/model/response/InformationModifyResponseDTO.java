package com.animal.api.mypage.information.modify.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 내 정보 수정 - 내 정보 조회 값 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationModifyResponseDTO {

	private String id;
	private String email;
	private String password; // 마스킹 된 값으로 표현
	private String name;
	private String nickname;
	private String birthDate;
	private String gender;
	private String tel;
	private int zipCode;
	private String address;
	private String addressDetail;
}
