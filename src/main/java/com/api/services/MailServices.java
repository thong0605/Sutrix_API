package com.api.services;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

public interface MailServices {

	public void send(String toAddress, String fromAddress, String subject, String content) throws Exception;

}
