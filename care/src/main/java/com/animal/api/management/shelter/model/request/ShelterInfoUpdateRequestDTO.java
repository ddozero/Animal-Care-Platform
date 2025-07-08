package com.animal.api.management.shelter.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterInfoUpdateRequestDTO {
	
	private int idx;
	private int userIdx;
	
	@NotBlank(message = "보호소 이름 작성은 필수입니다.")
	private String shelterName;
	
	@NotBlank(message = "보호소 담당자 이름 작성은 필수입니다.")
	private String personName;
	
	@NotBlank(message = "연락처 작성은 필수입니다.")
	private String tel;
	
	@NotNull(message = "우편번호 작성은 필수입니다.")
	private Integer zipCode;
	
	@NotBlank(message = "주소 작성은 필수입니다.")
	private String address;
	
	private String addressDetail;
	private String email;
	private String description;
	private String businessNumber;
	private String businessFile;

}
