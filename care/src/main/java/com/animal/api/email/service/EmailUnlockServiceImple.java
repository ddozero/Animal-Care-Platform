package com.animal.api.email.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.mapper.AuthMapper;
import com.animal.api.auth.model.vo.UserVO;

@Service
@Primary
public class EmailUnlockServiceImple implements EmailUnlockService {

    @Autowired
    private AuthMapper authMapper;
    
    @Autowired
    private EmailService emailService;

    // 인증코드 생성 로직 이동
    private String generateRandomCode() {
        int length = 6;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append((int)(Math.random() * 10));
        }
        return code.toString();
    }
    
	@Override
	public void sendUnlockCode(String email, HttpSession session) {
        UserVO user = authMapper.findByEmail(email);
        if (user == null) {
            throw new CustomException(404, "해당 이메일로 가입된 계정이 없습니다.");
        }

        // 인증코드 생성
        String code = generateRandomCode();

        prepareUnlock(email, code, session);
        
        // 세션 저장
        session.setAttribute("emailUnlockTarget", user.getId());
        session.setAttribute("emailUnlockCode", code);

        // 이메일 발송
        String subject = "[유기동물 통합 플랫폼] 계정 잠금 해제 인증코드 안내";
        String text = "요청하신 인증코드는 [" + code + "] 입니다.\n\n"
                    + "본 인증코드는 잠긴 계정을 해제하기 위해 사용됩니다.";
        emailService.sendEmail(email, subject, text);		
	}
	
    @Override
    public void prepareUnlock(String email, String code, HttpSession session) {
        UserVO user = authMapper.findByEmail(email);
        if (user == null) {
            throw new CustomException(404, "해당 이메일로 가입된 계정이 없습니다.");
        }

        session.setAttribute("emailAuthTarget", user.getId());
        session.setAttribute("emailUnlockTarget", user.getId());
        session.setAttribute("emailUnlockCode", code);
    }

    @Override
    public void verifyAndUnlock(String inputCode, HttpSession session) {
        String sessionCode = (String) session.getAttribute("emailUnlockCode");

        if (sessionCode == null || inputCode == null || !sessionCode.trim().equals(inputCode.trim())) {
            throw new CustomException(403, "인증코드가 올바르지 않습니다.");
        }

        // 세션에서 사용자 ID 꺼냄
        String targetId = (String) session.getAttribute("emailAuthTarget");

        if (targetId == null) {
            throw new CustomException(400, "잠금 해제 대상이 지정되지 않았습니다.");
        }

        // DB에서 사용자 조회
        UserVO user = authMapper.findUserById(targetId);

        if (user == null || user.getLocked() == 0) {
            throw new CustomException(404, "계정을 찾을 수 없거나 이미 잠금 해제 상태입니다.");
        }

        // 잠금 해제 처리
        user.setLocked(0);
        user.setLockCount(0);
        user.setLockedAt(null);

        int result = authMapper.updateLockInfo(user);
        if (result == 0) {
            throw new CustomException(500, "잠금 해제에 실패했습니다.");
        }

        // 세션 정리
        session.removeAttribute("emailUnlockCode");
        session.removeAttribute("emailAuthTarget");
        session.removeAttribute("emailUnlockTarget");
    }
}
