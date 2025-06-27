package com.animal.api.admin.donation.model.request;

import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAddDonationRequestDTO {
	
	private int idx;
	private int userIdx;

	@NotNull(message = "기부 상태 번호 입력은 필수입니다.")
	@Min(value = 1, message = "기부 상태 번호 입력은 필수입니다.1:대기/2:진행중/3:모금완료/4:완료")
	@Max(value = 4, message = "기부 상태 번호 입력은 필수입니다.1:대기/2:진행중/3:모금완료/4:완료")
	private Integer statusIdx; // 기부 상태 번호
	
	@NotBlank(message = "기부명 입력은 필수입니다.")
	private String name;
	
	@NotNull(message = "지원사업 시작일 입력은 필수입니다.")
	private Timestamp startDate;
	
	@NotNull(message = "지원사업 마감일 입력은 필수입니다.")
	private Timestamp endDate;
	
	@NotNull(message = "목표 기부액 입력은 필수입니다.")
	private Integer amount; // 목표 기부액
	
	private Integer completionAmount; // 기부 누적액
	private Integer completionRate; // 기부 달성률
	
	@NotBlank(message = "모금단체 입력은 필수입니다.")
	private String sponsor;
	
	@NotBlank(message = "모금액 사용처 입력은 필수입니다.")
	private String sponsorDetail;
	
	@NotBlank(message = "상세내용 입력은 필수입니다.")
	private String content;

}
