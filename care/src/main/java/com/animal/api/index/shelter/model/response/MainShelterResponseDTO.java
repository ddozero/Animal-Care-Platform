package com.animal.api.index.shelter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainShelterResponseDTO {

	private int idx;
	private String name;
	private String address;
	private String tel;
	private String email;
}
