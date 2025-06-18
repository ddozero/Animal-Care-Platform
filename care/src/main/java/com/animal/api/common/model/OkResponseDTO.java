package com.animal.api.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OkResponseDTO<T> {
	private int status;
    private String message;
    private T data;
}
