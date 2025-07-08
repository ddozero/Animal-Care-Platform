package com.animal.api.index.notice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainNoticeDTO {

	private int idx;
	private String title;
    private String contentSummary;
    private String createdDate;
}
