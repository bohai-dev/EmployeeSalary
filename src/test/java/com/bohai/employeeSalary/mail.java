package com.bohai.employeeSalary;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.bohai.employeeSalary.util.MailUtil;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class mail extends AbstractJUnit4SpringContextTests{

	private static final Logger log=Logger.getLogger(mail.class);
	
	@Autowired
	private MailUtil mailUtil;
	
	@Test
	public void sendSingleTest() throws MessagingException{
		mailUtil.send("18302168091@163.com", "test1", "test");
		System.err.println("成功");
	}
	
	@Test
	public void sendManyTest() throws MessagingException{
		List<String> recipients=new ArrayList<String>();
		recipients.add("18302168091@163.com");
		recipients.add("498203560@qq.com");
		mailUtil.send(recipients, "test2", "test2");
		System.err.println("成功");
	}
}
