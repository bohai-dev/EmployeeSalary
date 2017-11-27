package com.bohai.employeeSalary.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.employeeSalary.dao.SalaryDetailMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.SalaryDetail;

@Service("salaryDetailService")
public class SalaryDetailService {
	
    static Logger logger = Logger.getLogger(SalaryDetailService.class);
	
	@Autowired
	SalaryDetailMapper   salaryDetailMapper;	
	DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public void insertByCheckMessage(CheckMessage checkMessage) {
		
		    SalaryDetail salaryDetail=new SalaryDetail();
		    salaryDetail.setCheckMessageId(checkMessage.getId());
		    salaryDetail.setStaffNumber(checkMessage.getStaffNumber());
		    salaryDetail.setCheckState("0");  //状态不可用
		    
		    Date probaionStartDate=checkMessage.getProbationDateStart();  //试用期起始日期
		    
		    Date formalStartDate=checkMessage.getFormalDateStart();   //转正日期
		    if (probaionStartDate!=null) { //试用期起始日期不为null
		    	if (formalStartDate!=null) {  //转正日期也不为null，试用期日期和转正日期都不为null，要插入两行数据
		    		
		    		if (formalStartDate.after(probaionStartDate)) {  //如果转正日期在试用期日期之后
		    			 //获取试用期结束日期，也就是转正日期的前一天
			    		Date probationEndDate=new Date(formalStartDate.getTime()-24*60*60*1000);		    	
			    		//1、试用期工资
			    		salaryDetail.setSalary(checkMessage.getProbationSalary());
			    		salaryDetail.setStartTime(formatDate.format(probaionStartDate));
			    		salaryDetail.setEndTime(formatDate.format(probationEndDate));		    		
			    		//插入
			    		salaryDetailMapper.insertSelective(salaryDetail);
					}
				   
		    		//2、转正工资
		    		salaryDetail.setSalary(checkMessage.getPositionSalary());
		    		salaryDetail.setStartTime(formatDate.format(formalStartDate));
		    		salaryDetail.setEndTime(null);
		    		//插入
		    		salaryDetailMapper.insertSelective(salaryDetail); 
		    		
				 }else {  //转正日期为null，只有一行数据
					 salaryDetail.setSalary(checkMessage.getProbationSalary());
					 salaryDetail.setStartTime(formatDate.format(probaionStartDate));
					 salaryDetail.setEndTime(null);
					 salaryDetailMapper.insertSelective(salaryDetail);					
					
				}
				
			}else {  //试用期日期为null
				if (formalStartDate!=null) { //如果转正日期不为null
					salaryDetail.setSalary(checkMessage.getPositionSalary());
					salaryDetail.setStartTime(formatDate.format(formalStartDate));
					salaryDetail.setEndTime(null);
					salaryDetailMapper.insertSelective(salaryDetail);
				}
				
				
			}
	}
	

}
