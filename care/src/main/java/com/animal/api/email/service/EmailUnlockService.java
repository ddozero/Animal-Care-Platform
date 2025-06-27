package com.animal.api.email.service;

import javax.servlet.http.HttpSession;

public interface EmailUnlockService {
	
	void sendUnlockCode(String email, HttpSession session);
	
    void prepareUnlock(String email, String code, HttpSession session);
    void verifyAndUnlock(String inputCode, HttpSession session);
}
