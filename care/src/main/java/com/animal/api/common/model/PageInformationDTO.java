package com.animal.api.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInformationDTO {
	private int totalCnt;	// 총 페이지 수
	private int listSize;	// 한 페이지에 보여줄 리스트 수
	private int pageSize;	// 한번에 보여줄 페이지 수
	private int cp;			// 현재 페이지 번호
}
