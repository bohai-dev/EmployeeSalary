package com.bohai.employeeSalary.test;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.bohai.employeeSalary.util.MailUtil;

public class mail {

	private static final Logger log=Logger.getLogger(mail.class);
	
	@Autowired
	private MailUtil mailUtil;
	
	@Test
	public void sendSingleTest() throws MessagingException{
		mailUtil.send("18302168091@163.com", "test1", "test");
		System.err.println("成功");
	}
	
	public void sendManyTest() throws MessagingException{
		List<String> recipients=new ArrayList<String>();
		recipients.add("18302168091@163.com");
		recipients.add("498203560@qq.com");
		mailUtil.send(recipients, "test2", "test2");
		System.err.println("成功");
	}
}
