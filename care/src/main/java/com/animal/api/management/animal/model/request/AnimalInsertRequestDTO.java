package com.animal.api.management.animal.model.request;

import javax.validation.constraints.Max;
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
public class AnimalInsertRequestDTO {
	@NotNull(message = "유저 IDX는 필수입니다.")
	private Integer userIdx;

	@NotNull(message = "입양 상태 IDX는 필수입니다.")
	private Integer adoptionStatusIdx;

	@NotNull(message = "동물 품종 IDX는 필수입니다.")
	private Integer animalBreedIdx;

	@NotNull(message = "동물 성격 IDX는 필수입니다.")
	private Integer animalPersonalityIdx;

	@NotBlank(message = "동물 이름은 필수입니다.")
	private String name;

	@NotBlank(message = "성별은 필수입니다.")
	@Pattern(regexp = "^[MF]$", message = "성별은 'M' 또는 'F'만 가능합니다.")
	private String gender;

	@NotNull(message = "나이는 필수입니다.")
	@Min(value = 0, message = "나이는 0 이상이어야 합니다.")
	private Integer age;

	@NotNull(message = "사이즈는 필수입니다.")
	@Min(value = 0, message = "사이즈는 0 이상이어야 합니다.")
	private Integer size;

	@NotNull(message = "중성화 여부는 필수입니다.")
	@Min(value = 0)
	@Max(value = 1)
	private Integer neuter;

	@NotBlank(message = "설명은 필수입니다.")
	private String description;
	
	// DB 생성 후 등록된 idx 값
	private int idx;

}
