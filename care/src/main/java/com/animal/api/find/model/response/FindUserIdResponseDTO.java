package com.animal.api.find.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserIdResponseDTO {

	private String userid;
	private String createdAt;
}
