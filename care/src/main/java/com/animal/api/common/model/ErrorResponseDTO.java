package com.animal.api.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
	private int errorCode;
	private String errorMsg;
}
