package com.animal.api.management.animal.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalInsertRequestDTO {
	private Integer userIdx;
	private Integer adoptionStatusIdx;
	private Integer animalBreedIdx;
	private Integer animalPersonalityIdx;
	private String name;
	private String gender;
	private Integer age;
	private Integer size;
	private Integer neuter;
	private String description;
}
