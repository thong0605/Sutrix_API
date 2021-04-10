package com.api.servicesImplement;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.api.services.MailServices;

@Service("mailService")
public class EmailServicesImpl implements MailServices {

	@Autowired
	private JavaMailSender sender;

	public void send(String toAddress, String fromAddress, String subject, String content) throws Exception {

		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom(fromAddress);
		mimeMessageHelper.setTo(toAddress);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(content);
		mimeMessageHelper.setSentDate(new Date());

		sender.send(mimeMessage);
	}

}
