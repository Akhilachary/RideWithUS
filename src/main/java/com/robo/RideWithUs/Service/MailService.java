package com.robo.RideWithUs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendMail(String toMail,String subject,String message) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(toMail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		
		javaMailSender.send(mailMessage);
		
	}
}
