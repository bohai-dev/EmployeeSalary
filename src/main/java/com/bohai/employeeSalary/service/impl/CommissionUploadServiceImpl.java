package com.bohai.employeeSalary.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.FileUploadService;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.util.CommonUtils;

/**
 * 
 * @author cxy
 *上传营销人员提成表
 */
@Service("commissionUploadService")
public class CommissionUploadServiceImpl implements FileUploadService{
	
	
	static Logger logger = Logger.getLogger(CommissionUploadServiceImpl.class);
	
	@Autowired StaffSalaryService salaryService;
	
	@Override
	public String upload(MultipartFile file, Object... objects) throws BohaiException {
		HSSFWorkbook wb = null;
		String message="success";
		
		try {
			wb = new HSSFWorkbook(file.getInputStream());
			HSSFSheet commisonSheet = wb.getSheetAt(0);
			
			for(int i=0;i<commisonSheet.getLastRowNum();i++) {
				if (commisonSheet.getRow(i)!=null) {
				StaffSalary staffSalary=new StaffSalary(); 		
				    if(i==0) {    //获取表头
					String str=commisonSheet.getRow(i).getCell(0).getStringCellValue();
					String time=CommonUtils.filterNumber(str);
					staffSalary.setPayDate(time);
				   }else if (StringUtils.isNotEmpty(commisonSheet.getRow(i).getCell(2).getStringCellValue())) {
					 //获取员工编号
					
				}
			  }
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
