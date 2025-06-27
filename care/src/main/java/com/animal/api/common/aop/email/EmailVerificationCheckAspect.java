package com.animal.api.common.aop.email;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.animal.api.auth.mapper.AuthMapper;
import com.animal.api.common.model.OkResponseDTO;
/**
 * 회원가입 시 이메일 인증 확인 AOP
 * @author Whistler95
 * @since 2025-06-20
 */
@Aspect
@Component
public class EmailVerificationCheckAspect {
	
	@Autowired
	private AuthMapper authMapper;

    /**
     * 이메일 인증이 필요한 컨트롤러 메서드(@RequireEmailVerified)에 대한 AOP 처리
     * - HttpServletRequest를 파라미터에서 찾아서
     * - 세션에서 이메일 인증 여부 확인
     * - 인증되지 않았으면 컨트롤러 실행 없이 바로 403 응답 반환
     */
    @Around("@annotation(com.animal.api.common.aop.email.RequireEmailVerified)")
    public Object checkEmailVerification(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = null;

        // 1. HttpServletRequest 객체 찾기
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }

        if (request == null) {
        	// 컨트롤러에서 HttpServletRequest를 전달하지 않은 경우 예외
            throw new RuntimeException("HttpServletRequest가 필요합니다.");
        }
        
        // 2. 세션에서 이메일 인증 여부 확인
        HttpSession session = request.getSession(false);
        Boolean verified = (session != null) ? (Boolean) session.getAttribute("emailVerified") : null;

        // 3. 인증 안 되어 있으면 컨트롤러 실행 안 하고 바로 403 응답
        if (verified == null || !verified) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new OkResponseDTO<>(403, "이메일 인증을 먼저 완료해주세요.", null));
        }
        
        // 4. 인증 완료된 경우 → 원래 컨트롤러 메서드 실행
        return joinPoint.proceed();
    }
}
