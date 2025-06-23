package com.animal.api.board.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchRequestDTO {
	private String type;
	private String keyword;
	private Integer listSize;
	private Integer cp;

}
