package com.animal.api.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OkPageResponseDTO<T> {
	private int status;
	private String message;
	private T data;
	private PageInformationDTO pageInfo;
}
