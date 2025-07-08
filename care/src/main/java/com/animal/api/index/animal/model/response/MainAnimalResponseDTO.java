package com.animal.api.index.animal.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainAnimalResponseDTO {

	private int idx;
	private String name;
	private String breedName;
	private String gender;
	private String thumbnailPath;
}
