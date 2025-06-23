package com.animal.api.management.animal.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionConsultStatusRequestDTO {
	private Integer idx;	// 서비스에서 세팅
	@NotNull(message = "상담 상태는 필수입니다.")
	@Min(value = 1, message = "상담 상태는 1 ~ 4 여야 합니다.")
	@Max(value = 4, message = "상담 상태는 1 ~ 4 여야 합니다.")
	private Integer statusIdx;

}
