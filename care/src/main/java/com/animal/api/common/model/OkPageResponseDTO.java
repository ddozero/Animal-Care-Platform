package com.animal.api.common.model;

public class OkPageResponseDTO<T> {
	private int status;
	private String message;
	private T data;
	private PageInformationDTO pageInfo;
}
