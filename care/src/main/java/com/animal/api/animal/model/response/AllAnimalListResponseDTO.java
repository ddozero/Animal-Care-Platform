package com.animal.api.animal.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllAnimalListResponseDTO {
	private int idx;
	private String name;
	private char gender;
	private int age;
	private int size;
	private int neuter;
	private Timestamp createAt;
	private String breed;
	private String type;
	private String personality;
	private String adoptionStatus;
}
