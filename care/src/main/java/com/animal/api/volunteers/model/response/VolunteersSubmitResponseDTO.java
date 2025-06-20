package com.animal.api.volunteers.model.response;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VolunteersSubmitResponseDTO {
	
	private int idx;
	private String name;
	private String email;
	private String tel;
	private Timestamp BirthDate;
	private String type;
	private String groupName;
	private String groupTel;
	private int male;
	private int female;
	

}
