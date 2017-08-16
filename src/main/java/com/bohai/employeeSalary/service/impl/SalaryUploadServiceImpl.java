package com.bohai.employeeSalary.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.FileUploadService;
import com.bohai.employeeSalary.util.CommonUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 * 员工社保缴费情况文件上传
 * @author cxy
 *
 */
@Service("salaryUploadService")
public class SalaryUploadServiceImpl implements FileUploadService {
	static Logger logger = Logger.getLogger(SalaryUploadServiceImpl.class);
	
	@Autowired
	private StaffSalaryMapper staffSalaryMapper;

	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
			Sheet salarySheet = workbook.getSheet(0);
			for(int i = 5;i<salarySheet.getRows()-1;i++){
				 StaffSalary staffSalary=new StaffSalary();  
				 if (i==5) {
					 staffSalary.setPensionPersonalPercent(salarySheet.getCell(6, i).getContents());     //getCell(int column, int row)
					 staffSalary.setMedicalPersonalPercent(salarySheet.getCell(7,i).getContents());
					 staffSalary.setUnemploymentPersonalPercent(salarySheet.getCell(8,i).getContents());
					 
					 staffSalary.setPensionCompanyPercent(salarySheet.getCell(9,i).getContents());
					 staffSalary.setMedicalCompanyPercent(salarySheet.getCell(10,i).getContents());
					 staffSalary.setUnemploymentCompanyPercent(salarySheet.getCell(11,i).getContents());
					 staffSalary.setInjuryCompanyPercent(salarySheet.getCell(12,i).getContents());
					 staffSalary.setBirthCompayPercent(salarySheet.getCell(13,i).getContents());			 
					 
					 
				}else {
					staffSalary.setStaffNumber(salarySheet.getCell(2,i).getContents());   //员工编号
					staffSalary.setStaffDepartmentId(salarySheet.getCell(2,i).getContents().substring(0, 2));   //部门编号
					String payTime=salarySheet.getCell(4,i).getContents().replace("年", "-").replace("月", "");
					staffSalary.setPayDate(payTime);   //缴费月份
					staffSalary.setPayBase(salarySheet.getCell(5,i).getContents());  //缴费基数
					staffSalary.setPensionPersonal(salarySheet.getCell(6,i).getContents());
					staffSalary.setMedicalPersonal(salarySheet.getCell(7,i).getContents());
					staffSalary.setUnemploymentPersonal(salarySheet.getCell(8,i).getContents());
					staffSalary.setPersonalReserve1(salarySheet.getCell(9,i).getContents());
					staffSalary.setPersonalReserve2(salarySheet.getCell(10,i).getContents());
					staffSalary.setPensionCompany(salarySheet.getCell(11,i).getContents());
					staffSalary.setMedicalCompany(salarySheet.getCell(12,i).getContents());
					staffSalary.setUnemploymentCompany(salarySheet.getCell(13,i).getContents());
					staffSalary.setInjuryCompany(salarySheet.getCell(14,i).getContents());
					staffSalary.setBirthCompany(salarySheet.getCell(15,i).getContents());
					staffSalary.setCompanyReserve1(salarySheet.getCell(16,i).getContents());
					staffSalary.setCompanyReserve2(salarySheet.getCell(17,i).getContents());
					staffSalary.setPersonalTotal(salarySheet.getCell(18,i).getContents());
					staffSalary.setCompanyTotal(salarySheet.getCell(19,i).getContents());
					staffSalary.setPaymentTotal(salarySheet.getCell(20,i).getContents());
					
					staffSalary.setCreateTime(CommonUtils.getTime());
					
					
					
					
					
					
					
					
				}
			
				
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	

	}

}
