package com.animal.api.common.aop.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.animal.api.common.model.OkResponseDTO;

/**
 * 로그인 여부 확인 AOP 
 * - 컨트롤러 메서드에 @RequireLogin 어노테이션이 붙은 경우 
 * 실행 전 세션에 로그인 정보(loginUser)가 있는지 검사 
 * - 없을 경우 401 UNAUTHORIZED 응답 반환 
 * - 개발자는 컨트롤러에서 로그인 여부 체크 로직 생략 가능
 * 
 * @author Whistler95
 * @since 2025-06-20
 */

@Aspect
@Component
public class LoginCheckAspect {

	/**
	 * @RequireLogin 어노테이션이 붙은 메서드 실행 전 로그인 여부 확인
	 */
	@Around("@annotation(com.animal.api.common.aop.auth.RequireLogin)")
	public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = null;

		// 메서드 인자 중 HttpServletRequest 추출
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				break;
			}
		}

		// HttpServletRequest가 없을 경우 서버 에러 처리
		if (request == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new OkResponseDTO<>(500, "내부 오류: HttpServletRequest를 찾을 수 없습니다.", null));
		}

		// 세션에서 로그인 사용자 정보(loginUser) 확인
		HttpSession session = request.getSession(false);
		Object loginUser = (session != null) ? session.getAttribute("loginUser") : null;

		// 로그인 정보가 없을 경우 401 Unauthorized 응답 반환
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인이 필요합니다", null));
		}

		// 로그인 정보가 있을 경우 원래 메서드 실행
		return joinPoint.proceed();
	}
}
