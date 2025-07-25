package com.animal.api.animal.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionsRequestDTO {
	private int listSize;
	private int cp;
	private String type;
	private String breed;
	private String gender;
	private int neuter;
	private int age;
	private String adoptionStatus;
	private String personality;
	private int size;
	private String name;
}
