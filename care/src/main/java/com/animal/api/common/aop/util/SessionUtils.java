package com.animal.api.common.aop.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.animal.api.auth.model.response.LoginResponseDTO;

/**
 * 현재 세션에서 로그인 사용자 정보를 꺼내는 유틸 클래스
 * 모든 컨트롤러/서비스에서 공통으로 사용 가능
 * 추후 세션 구조가 바뀌더라도 이 클래스 하나만 수정하면 전체 적용됨
 * 
 * 사용 예: SessionUtils.getLoginUser(request)
 * 
 * @author Whistler95
 * @since 2025-06-20
 */
public class SessionUtils {

    /**
     * 현재 세션에서 로그인된 사용자 정보를 가져옴
     * @param request HttpServletRequest
     * @return 로그인된 사용자 정보 (없으면 null)
     */
    public static LoginResponseDTO getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("loginUser") : null;
        return (user instanceof LoginResponseDTO) ? (LoginResponseDTO) user : null;
    }

    /**
     * 현재 사용자가 로그인 상태인지 여부를 반환
     * @param request HttpServletRequest
     * @return 로그인되어 있으면 true, 아니면 false
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getLoginUser(request) != null;
    }

    /**
     * 세션에서 로그인된 사용자 정보를 제거합니다 (로그아웃용)
     * @param request HttpServletRequest
     */
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("loginUser");
        }
    }
}  
