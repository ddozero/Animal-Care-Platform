package com.animal.api.volunteers.model.request;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteersSubmitRequestDTO {
	
	
	@NotNull(message = "봉사 종류 IDX는 필수입니다.")
	private Integer volunteerIdx;
	
	@NotNull(message = "회원 IDX는 필수입니다.")
	private Integer userIdx;
	
	@NotBlank(message = "신청구분 선택은 필수 입니다.")
	@Pattern (regexp = "S|G", message ="개인은 'S' 단체는 'G'를 선택해주세요")
	private String type;
	
	@NotBlank(message = "회원 이름은 필수입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
	
	@NotNull(message = "전화번호는 필수 항목입니다.")
	private String tel;
	
	@NotNull(message = "생년월일은 필수 항목입니다.")
	private Timestamp birthDate;
	
	private String groupName;
	private String groupTel;
	
	@NotNull(message = "남자 인원수는 필수 입력 항목입니다.")
	@Min(value = 0, message = "인원수는 0 이상이어야 합니다.")
	private Integer male;
	
	@NotNull(message = "여자 인원수는 필수 입력 항목입니다.")
	@Min(value = 0, message = "인원수는 0 이상이어야 합니다.")
	private Integer female;
	
	@NotBlank(message = "설명은 필수입니다.")
	private String description;
	

}
