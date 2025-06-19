package com.animal.api.support.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchNoticeResponseDTO {
	
	private int cp;
	private int listSize;
	private String title;
	private String content;

}
