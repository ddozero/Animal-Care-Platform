package com.animal.api.common.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 공통 페이징 응답 wrapper DTO
 * 
 * 	페이징이 필요한 API 응답에서 사용되는 제네릭 클래스 입니다
 * 	전체 데이터 (totalCount)와 실제 데이터 목록(items)를 함께 담아 반환합니다.
 * 
 * 사용 예시
 * 	PageResult<MemberListDTO>
 * 	pageResult<BoardListDTO>
 * 
 * 사용 목적
 * 	컨트롤러에서 공통 포맷으로 페이징 응답 반환
 * 	프론트엔드에서 totaCount 기반 페이지네이션 처리 용이	
 * 
 *JSON 응답 예시:
 *{@code
 * {
 *   "code": 200,
 *   "message": "회원 목록 조회 성공",
 *   "data": {
 *     "totalCount": 102,
 *     "items": [
 *       {
 *         "userIdx": 33,
 *         "id": "whister95",
 *         "nickname": "휘슬러",
 *         "userType": 1,
 *         "email": "test@naver.com",
 *         "status": 0,
 *         "createdAt": "2025-06-01 13:12:55",
 *         "shelterName": null
 *       },
 *       ...
 *     ]
 *   }
 * }
 * }
 * 
 * @author Whistler95
 * @param <T> 목록에 담기는 DTO 타입
 * @since 2025-06-26
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
	private int totalCount;
	private List<T> items;
}
