package com.bohai.employeeSalary.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.SysUser;
import com.bohai.employeeSalary.service.FileUploadService;


@Service("staffInfoUploadService")
public class StaffInfoUploadServiceImpl implements FileUploadService{
	
    static Logger logger = Logger.getLogger(StaffInfoUploadServiceImpl.class);
	 
	@Autowired
	private CheckMessageMapper checkMessageMapper;	
	@Autowired
	private StaffInfoMapper staffInfoMapper;	 
	
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
							}else {
								message.append("第"+ (i+1) +"行员工编号为空，导入失败</br>");
								continue;
							}
							//员工姓名
							if (staffSheet.getRow(i).getCell(1)!=null) {
								checkMessage.setName(staffSheet.getRow(i).getCell(1).getStringCellValue());
							}else {
								message.append("第"+ (i+1) +"行员工姓名为空，导入失败</br>");
								continue;
							}
							//岗位工资
							if (staffSheet.getRow(i).getCell(2)!=null) {
								try {
                                    String salary=staffSheet.getRow(i).getCell(2).getStringCellValue().trim();
                                    BigDecimal probationSalary=new BigDecimal(salary).multiply(new BigDecimal("0.8")).setScale(2, RoundingMode.HALF_UP);
                                    checkMessage.setPositionSalary(salary);
                                    checkMessage.setProbationSalary(probationSalary.toString());
                                } catch (Exception e) {
                                    logger.error("解析岗位工资失败",e);
                                    message.append("第"+ (i+1) +"行岗位工资解析错误，导入失败</br>");
                                    continue;
                                }
							}else {
								message.append("第"+ (i+1) +"行岗位工资为空，导入失败</br>");
								continue;
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
									message.append("第"+ (i+1) +"行员工类型非法，导入失败</br>");
									continue;
								}
							}else {
								message.append("第"+ (i+1) +"行员工类型为空，导入失败</br>");
								continue;
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
								
								if (StringUtils.isNotEmpty(startDateStr.trim())) {
									
									try {
										Date startDate=sdf.parse(startDateStr);
										checkMessage.setProbationDateStart(startDate);
									} catch (ParseException e) {
									    logger.error("解析异常", e);
										message.append(checkMessage.getStaffNumber()).append(":").append(checkMessage.getName()).append("试用日期格式不正确，未能导入，时间必须是'2017-10-31'这种文本形式</br>");
										continue;
									}
								}
								 

							}
							//转正日期
							if (staffSheet.getRow(i).getCell(8)!=null) {
								String formalDateStr=staffSheet.getRow(i).getCell(8).getStringCellValue();
								
								if (StringUtils.isNotEmpty(formalDateStr.trim())) {
									
									try {
										Date formalDate=sdf.parse(formalDateStr);
										checkMessage.setFormalDateStart(formalDate);
									} catch (ParseException e) {
									    logger.error("解析异常", e);
										message.append(checkMessage.getStaffNumber()).append(":").append(checkMessage.getName()).append("转正日期格式不正确，未能导入，时间必须是'2017-10-31'这种文本形式</br>");
										continue;
									}
								}
							}
							//离职日期
							if (staffSheet.getRow(i).getCell(9)!=null) {
								String leaveDateStr=staffSheet.getRow(i).getCell(9).getStringCellValue();
								
								if (StringUtils.isNotEmpty(leaveDateStr.trim())) {
									try {
										Date leaveDate=sdf.parse(leaveDateStr);
										checkMessage.setLeaveDate(leaveDate);
										
									} catch (ParseException e) {
									    logger.error("解析异常", e);
										message.append(checkMessage.getStaffNumber()).append(":").append(checkMessage.getName()).append("离职日期格式不正确，未能导入，时间必须是'2017-10-31'这种文本形式</br>");
									    continue;
									}
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
					        
					       
					         List checkMessageList=checkMessageMapper.selectByStaffNumber(number);
					         StaffInfo staffInfo=this.staffInfoMapper.selectByPrimaryKey(number);
					         
					         String submitType = "0";
					         
					         if (staffInfo!=null) {
								// message.append("已存在 ").append(checkMessage.getStaffNumber()).append(":").append(checkMessage.getName()).append("员工信息，未能导入</br>");
								// continue;
					        	 submitType = "1";
							}
					         
					         if (checkMessageList.size()>0) {  //已存在
					        	 //checkMessage.setSubmitType("1"); //修改
					        	 message.append("已存在 ").append(checkMessage.getStaffNumber()).append(":").append(checkMessage.getName()).append("审核信息，未能导入</br>");
					        	 continue;
							}else {
								checkMessage.setSubmitType(submitType);  //新增
								//审核信息提交时间
							    checkMessage.setSubmitTime(new Date());
								
								checkMessageMapper.insert(checkMessage);
							}
					       
					        
						
					}
					
				} catch (Exception e) {
				    logger.error("解析异常", e);
					message.append("上传失败："+e.getMessage());
				}
	       
		return message.toString();
	}	

}
