package com.animal.api.support.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchNoticeRequestDTO {
	
	private int cp;
	private int listSize;
	private String title;
	private String content;

}
