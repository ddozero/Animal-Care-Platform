package com.animal.api.management.volunteers.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountPeopleResponseDTO {
	private int male;
	private int female;
}
