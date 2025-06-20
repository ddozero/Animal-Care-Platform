package com.animal.api.email.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EmailServiceImple implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String to, String subject, String text) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, false);
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("이메일 전송 실패", e);
		}
		
	}
}
