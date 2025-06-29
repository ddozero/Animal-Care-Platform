package com.animal.api.management.volunteers.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterVolunteerUpdateRequestDTO {
	@NotNull(message = "봉사 번호는 필수 입니다.")
	@Min(value = 1, message = "봉사 번호는 1이상이어야 합니다.")
	private Integer volunteerIdx;
	@NotNull(message = "회원 번호는 필수 입니다.")
	@Min(value = 1, message = "회원 번호는 1이상이어야 합니다.")
	private Integer userIdx;
	@NotNull(message = "봉사 분야는 필수 입니다.")
	@Min(value = 1, message = "봉사 분야는 1이상이어야 합니다.")
	@Max(value = 4, message = "봉사 분야는 4이하이어야 합니다.")
	private Integer volunteerTypeIdx;
	@NotNull(message = "봉사 모집상태는 필수 입니다.")
	@Min(value = 1, message = "봉사 모집상태는 1이상이어야 합니다.")
	@Max(value = 4, message = "봉사 모집상태는 4이하이어야 합니다.")
	private Integer volunteerStatusIdx;
	@NotBlank(message = "제목 입력은 필수입니다.")
	private String title;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Integer volunteerDate;
	@NotNull(message = "봉사 시간은 필수 입니다.")
	private Integer time;
	@NotBlank(message = "봉사장소 입력은 필수입니다.")
	private String location;
	@NotNull(message = "모집인원은 필수 입니다.")
	private Integer capacity;
	@NotBlank(message = "봉사상세내용 입력은 필수입니다.")
	private String content;
	@NotBlank(message = "신청연령대상 입력은 필수입니다.")
	private Integer ageTarget;
}
