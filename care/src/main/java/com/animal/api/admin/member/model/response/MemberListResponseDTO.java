package com.animal.api.admin.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListResponseDTO {

	private int userIdx;
	private String id;
	private String nickname;
	private String email;
	private int userType;  // 1-일반 2-보호소
	private int status; // 0 정지 1 정상 -1 탈퇴
	private String creatAt;
	
	private String sheltername;
	
}
