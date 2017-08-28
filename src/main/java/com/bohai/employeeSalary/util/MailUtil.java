package com.bohai.employeeSalary.util;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


/**
 * 发送邮件工具类
 * @author ChenY
 * */
public class MailUtil {

	private JavaMailSenderImpl mailSender;  //发送者
	private SimpleMailMessage simpleMailMessage; 
	
	/**
	 * 单发
	 * @param recipient 收件人
	 * @param subject	 主题
	 * @param content 	 内容
	 * @throws MessagingException 
	 * */
	public void send(String recipient,String subject,String content,String FileName,InputStreamSource inputStreamSource) throws MessagingException{
		
		MimeMessage message=this.mailSender.createMimeMessage();
		mailSender.setUsername("18302168091@163.com");
		mailSender.setPassword("cy19960119");
		MimeMessageHelper helper=new MimeMessageHelper(message,true,"utf-8");
		helper.setFrom(this.simpleMailMessage.getFrom());//设置发送人
		helper.setTo(recipient);//设置收件人
		helper.setSentDate(new Date());
		helper.setSubject(subject);//设置主题
		helper.setText(content);//设置文本内容
//		helper.addAttachment("a.txt", new File("D:/a.txt"));
//		helper.addAttachment(attachmentFilename, inputStreamSource);
		mailSender.send(message);
	}
	
	/**
	 * 群发
	 * @param recipients 收件人
	 * @param subject 主题
	 * @param content 内容
	 * @throws MessagingException 
	 * */
	public void send(List<String> recipients,String subject,String content) throws MessagingException{
		MimeMessage message=this.mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message,true,"utf-8");
		helper.setFrom(this.simpleMailMessage.getFrom());//设置发送人
		final int num=recipients.size();
		InternetAddress[] addresses=new InternetAddress[num];
		for(int i=0;i<num;i++){
			addresses[i]=new InternetAddress(recipients.get(i));
					}
		helper.setTo(addresses);
		helper.setSubject(subject);
		helper.setText(content);
		helper.addAttachment("a.txt", new File("D:/a.txt"));
		mailSender.send(message);
	}
	
	
	

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	
}