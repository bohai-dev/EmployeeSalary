package com.bohai.employeeSalary.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.impl.SalaryUploadServiceImpl;

/**
 * 员工工资管理service
 * @author cxy
 *
 */
@Service("salaryService")
public class StaffSalaryService {
	static Logger logger = Logger.getLogger(SalaryUploadServiceImpl.class);
	
	@Autowired
	StaffSalaryMapper   staffSalaryMapper;
	
	public String saveOrUpdate(StaffSalary staffSalary) {
	 String staffNum=staffSalary.getStaffNumber();
	 String payDate=staffSalary.getPayDate();
	 String message="";
	 Long count=staffSalaryMapper.selectByStaffNumAndDate(staffNum, payDate);
	 if(count<=0) {   //不存在 
		 int num=staffSalaryMapper.insertSelective(staffSalary);
		 message="已成功插入"+num+"条记录";
	 }else {   //已存在更新
		 int num=staffSalaryMapper.updateByStaffNumAndDate(staffSalary);
		 message="已成功更新"+num+"条记录";
	 }
	 
	 return message;
		
		
	}

}
