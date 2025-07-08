package com.animal.api.support.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchNoticeRequestDTO {
	
	private int cp;
	private int listSize;
	private String title;

}
