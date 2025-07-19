package com.ecom.util;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommanUtil {
	
	@Autowired
	private  JavaMailSender mailSender;

	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom("daspabitra55@gmail.com", "Shoping Card");
	    helper.setTo(reciepentEmail);
	    String comtext = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
		+"<p>Click the link beloqw to change yor password:</P>" +"<p><a href=\"" + url
		+ "\">Change my password</a></p>";
	    	
	    helper.setSubject("Password Reset");
	    helper.setText(comtext, true);
	    mailSender.send(message);
	    return true;
		
	}

	public static String generateUrl(HttpServletRequest request) {
		
		 String siteUrl = request.getRequestURL().toString();
		return siteUrl.replace(request.getServletPath(), "");
		
		 
	}
	
}
