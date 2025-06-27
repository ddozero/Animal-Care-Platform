package com.animal.api.admin.donation.model.request;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateRequestDTO {
	
	int idx;
	
	@NotBlank(message = "기부명 입력은 필수입니다.")
	private String name;
	
	@NotNull(message = "지원사업 시작일 입력은 필수입니다.")
	private Timestamp startDate;
	
	@NotNull(message = "지원사업 마감일 입력은 필수입니다.")
	private Timestamp endDate;
	
	@NotNull(message = "목표 기부액 입력은 필수입니다.")
	private Integer amount; // 목표 기부액
	
	@NotBlank(message = "모금단체 입력은 필수입니다.")
	private String sponsor;
	
	@NotBlank(message = "모금액 사용처 입력은 필수입니다.")
	private String sponsorDetail;
	
	@NotBlank(message = "상세내용 입력은 필수입니다.")
	private String content;
	
}
