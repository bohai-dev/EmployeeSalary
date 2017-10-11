package com.bohai.employeeSalary.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.CheckMessageMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.SysUser;
import com.bohai.employeeSalary.service.FileUploadService;


@Service("staffInfoUploadService")
public class StaffInfoUploadServiceImpl implements FileUploadService{
	
	 
	@Autowired
	private CheckMessageMapper checkMessageMapper;
	 
	@Override
	public String upload(MultipartFile file, Object... objects) throws BohaiException {
		 StringBuilder message=new StringBuilder();
		 XSSFWorkbook wb = null;
		 String number="";
		 SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );  
	        
	            try {
					wb = new XSSFWorkbook(file.getInputStream());
					XSSFSheet staffSheet = wb.getSheetAt(0);
					
					for(int i=2;i<=staffSheet.getLastRowNum();i++) {
						for(int j=0;j<12;j++) {
							if (staffSheet.getRow(i).getCell(j)!=null) {
								staffSheet.getRow(i).getCell(j).setCellType(CellType.STRING);
							}
							
						}
						
							CheckMessage checkMessage=new CheckMessage();
							//员工编号、部门
							if (staffSheet.getRow(i).getCell(0)!=null) {
								number=staffSheet.getRow(i).getCell(0).getStringCellValue();
								checkMessage.setStaffNumber(number);
								
								checkMessage.setDepartmentId(number.substring(0,2));
							}
							//员工姓名
							if (staffSheet.getRow(i).getCell(1)!=null) {
								checkMessage.setName(staffSheet.getRow(i).getCell(1).getStringCellValue());
							}
							//岗位工资
							if (staffSheet.getRow(i).getCell(2)!=null) {
								checkMessage.setPositionSalary(staffSheet.getRow(i).getCell(2).getStringCellValue());
							}
							//技能工资
							if (staffSheet.getRow(i).getCell(3)!=null) {
								checkMessage.setSkillSalary(staffSheet.getRow(i).getCell(3).getStringCellValue());
							}
							//司龄工资
							if (staffSheet.getRow(i).getCell(4)!=null) {
								checkMessage.setWorkYears(staffSheet.getRow(i).getCell(4).getStringCellValue());
							}
							//员工类型、工资系数
							if (staffSheet.getRow(i).getCell(5)!=null) {
								String staffType=staffSheet.getRow(i).getCell(5).getStringCellValue();
								if (staffType.equals("正式员工")) {
									checkMessage.setIsProbation("0");
									checkMessage.setCoefficeient("1.0");
								}else if (staffType.equals("试用期员工")) {
									checkMessage.setIsProbation("1");
									checkMessage.setCoefficeient("0.8");
								}else {
									checkMessage.setIsProbation("2");
								}
								
							}
							//员工状态
							if (staffSheet.getRow(i).getCell(6)!=null) {
								String leaveType=staffSheet.getRow(i).getCell(6).getStringCellValue();
								if (leaveType.equals("在职")) {
									checkMessage.setIsLeave("0");
								}else if (leaveType.equals("离职")) {
									checkMessage.setIsLeave("1");
								}
							}
							//试用期起始日期
							if (staffSheet.getRow(i).getCell(7)!=null) {
								String startDateStr=staffSheet.getRow(i).getCell(7).getStringCellValue();
								 
								try {
									Date startDate=sdf.parse(startDateStr);
									checkMessage.setProbationDateStart(startDate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							//转正日期
							if (staffSheet.getRow(i).getCell(8)!=null) {
								String formalDateStr=staffSheet.getRow(i).getCell(8).getStringCellValue();
								try {
									Date formalDate=sdf.parse(formalDateStr);
									checkMessage.setFormalDateStart(formalDate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//离职日期
							if (staffSheet.getRow(i).getCell(9)!=null) {
								String leaveDateStr=staffSheet.getRow(i).getCell(9).getStringCellValue();
								try {
									Date leaveDate=sdf.parse(leaveDateStr);
									checkMessage.setLeaveDate(leaveDate);
									
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//邮箱
							if (staffSheet.getRow(i).getCell(10)!=null) {
								checkMessage.setEmail(staffSheet.getRow(i).getCell(10).getStringCellValue());
							}
							//备注
							if (staffSheet.getRow(i).getCell(11)!=null) {
								checkMessage.setRemark(staffSheet.getRow(i).getCell(11).getStringCellValue());
							}
							
							//审核状态
							checkMessage.setTage("0");
							
							//信息提交人
							//获取当前登录用户姓名
							Subject currentUser = SecurityUtils.getSubject();
					        String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
					        checkMessage.setSubmitter(userName);
					        
					        //审核类型
					         List checkMessageList=checkMessageMapper.selectByStaffNumber(number);
					         if (checkMessageList.size()>0) {  //已存在
					        	 //checkMessage.setSubmitType("1"); //修改
					        	 message.append("已存在 ").append(checkMessage.getStaffNumber()).append(":").append(checkMessage.getName()).append(" 未能导入</br>");
							}else {
								checkMessage.setSubmitType("0");  //新增
								//审核信息提交时间
							    checkMessage.setSubmitTime(new Date());
								
								checkMessageMapper.insert(checkMessage);
							}
					       
					        
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					message.append("fail:"+e.getMessage());
					e.printStackTrace();
				}
	       
		return message.toString();
	}	

}
