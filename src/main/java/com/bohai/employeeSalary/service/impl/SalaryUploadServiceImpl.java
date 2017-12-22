package com.bohai.employeeSalary.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.FileUploadService;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.util.CommonUtils;

/**
 * 员工社保缴费情况文件上传
 * 
 * @author cxy
 *
 */
@Service("salaryUploadService")
public class SalaryUploadServiceImpl implements FileUploadService {
	static Logger logger = Logger.getLogger(SalaryUploadServiceImpl.class);
	
	@Autowired
	private StaffSalaryMapper staffSalaryMapper;
	@Autowired
	StaffSalaryService salaryService;

	@Override
	public String upload(MultipartFile file, Object... objects) throws BohaiException {

		HSSFWorkbook wb = null;
		String message = "upload success!";
		BigDecimal buckleUp=new BigDecimal("0.00");
		DateFormat formater = new SimpleDateFormat("yyyy-MM");		
		try {

			wb = new HSSFWorkbook(file.getInputStream());
			HSSFSheet salarySheet = wb.getSheetAt(0);
			HSSFSheet houseSheet = wb.getSheetAt(1);
			// 读取文件名
			String fileName = file.getOriginalFilename();
			// 获取时间
			String payDate = CommonUtils.getDateStr(fileName);
			
			logger.info("社保公积金月份："+payDate);

			String pensionPersonalPercent = Double.toString(salarySheet.getRow(5).getCell(6).getNumericCellValue());
			String medicalPersonalPercent = Double.toString(salarySheet.getRow(5).getCell(7).getNumericCellValue());
			String unemploymentPersonalPercent = Double
					.toString(salarySheet.getRow(5).getCell(8).getNumericCellValue());
			String pensionCompanyPercent = Double.toString(salarySheet.getRow(5).getCell(11).getNumericCellValue());
			String medicalCompanyPercent = Double.toString(salarySheet.getRow(5).getCell(12).getNumericCellValue());
			String unemploymentCompanyPercent = Double
					.toString(salarySheet.getRow(5).getCell(13).getNumericCellValue());
			String injuryCompanyPercent = Double.toString(salarySheet.getRow(5).getCell(14).getNumericCellValue());
			String birthCompayPercent = Double.toString(salarySheet.getRow(5).getCell(15).getNumericCellValue());

			for (int i = 6; i < salarySheet.getLastRowNum() + 1; i++) {
				if (salarySheet.getRow(i) != null) {
					for (int j = 0; j < salarySheet.getRow(i).getLastCellNum(); j++) {
						if (salarySheet.getRow(i).getCell(j) != null) {
							//第5列为时间格式
							if(j != 4) {
								salarySheet.getRow(i).getCell(j).setCellType(CellType.STRING); // 将每行单元格的类型都设置为string
							}
						}
					}

					if (salarySheet.getRow(i).getCell(2)!=null && 
							StringUtils.isNotEmpty(salarySheet.getRow(i).getCell(2).getStringCellValue())) {
						
						Date date=salarySheet.getRow(i).getCell(4).getDateCellValue();
								
						
						String payTime=formater.format(date);  //每一行数据的缴费月份
						
						if(!payTime.equals(payDate)) {   //与缴费日期进行比较，如果不相同则包含补缴社保
						    buckleUp=buckleUp.add(new BigDecimal(salarySheet.getRow(i).getCell(18).getStringCellValue()));               	 
						}else {
						
						StaffSalary staffSalary = new StaffSalary();

						staffSalary.setPensionPersonalPercent(pensionPersonalPercent); // getCell(int column, int row)个人缴纳养老保险比例
						staffSalary.setMedicalPersonalPercent(medicalPersonalPercent);//个人缴纳医疗保险比例
						staffSalary.setUnemploymentPersonalPercent(unemploymentPersonalPercent);//个人缴纳失业保险比例

						staffSalary.setPensionCompanyPercent(pensionCompanyPercent);//公司缴纳养老保险比例
						staffSalary.setMedicalCompanyPercent(medicalCompanyPercent);//公司缴纳医疗保险比例
						staffSalary.setUnemploymentCompanyPercent(unemploymentCompanyPercent);//公司缴纳失业保险比例
						staffSalary.setInjuryCompanyPercent(injuryCompanyPercent);//工伤保险比例
						staffSalary.setBirthCompayPercent(birthCompayPercent);//生育保险比例

						/*
						 * HSSFRow row = salarySheet.getRow(i); HSSFCell cell =
						 * salarySheet.getRow(i).getCell(2); cell.setCellType(CellType.STRING);
						 */
						staffSalary.setStaffNumber(salarySheet.getRow(i).getCell(2).getStringCellValue());  // 员工编号
						staffSalary.setStaffDepartmentId(
								salarySheet.getRow(i).getCell(2).getStringCellValue().substring(0, 2));    // 部门编号
						
						
						staffSalary.setPayDate(payDate);                                                // 缴费月份
						staffSalary.setPayBase(salarySheet.getRow(i).getCell(5).getStringCellValue()); // 缴费基数
						staffSalary.setPensionPersonal(salarySheet.getRow(i).getCell(6).getStringCellValue());//个人缴纳养老保险
						staffSalary.setMedicalPersonal(salarySheet.getRow(i).getCell(7).getStringCellValue());//个人缴纳医疗保险
						staffSalary.setUnemploymentPersonal(salarySheet.getRow(i).getCell(8).getStringCellValue());//个人缴纳失业保险
						staffSalary.setPersonalReserve1(salarySheet.getRow(i).getCell(9).getStringCellValue());//个人缴纳备用条目1
						staffSalary.setPersonalReserve2(salarySheet.getRow(i).getCell(10).getStringCellValue());//个人缴纳备用条目2
						staffSalary.setPensionCompany(salarySheet.getRow(i).getCell(11).getStringCellValue());//公司缴纳养老保险
						staffSalary.setMedicalCompany(salarySheet.getRow(i).getCell(12).getStringCellValue());//公司缴纳医疗保险
						staffSalary.setUnemploymentCompany(salarySheet.getRow(i).getCell(13).getStringCellValue());//公司缴纳失业保险
						staffSalary.setInjuryCompany(salarySheet.getRow(i).getCell(14).getStringCellValue());//工伤保险
						staffSalary.setBirthCompany(salarySheet.getRow(i).getCell(15).getStringCellValue());//生育保险
						staffSalary.setCompanyReserve1(salarySheet.getRow(i).getCell(16).getStringCellValue());//公司缴纳备用条目1
						staffSalary.setCompanyReserve2(salarySheet.getRow(i).getCell(17).getStringCellValue());//公司缴纳备用条目2
						staffSalary.setPersonalTotal(salarySheet.getRow(i).getCell(18).getStringCellValue());//个人社保缴费合计
						staffSalary.setCompanyTotal(salarySheet.getRow(i).getCell(19).getStringCellValue());//公司社保缴费合计
						staffSalary.setPaymentTotal(salarySheet.getRow(i).getCell(20).getStringCellValue());//社保缴费合计

						staffSalary.setCreateTime(CommonUtils.getTime());
                        staffSalary.setBuckleUp(buckleUp.toString());   
						salaryService.saveOrUpdate(staffSalary);
						buckleUp=new BigDecimal("0.00");
					}
				}

			}

		}

			String housePerBasePercent = houseSheet.getRow(5).getCell(6).getNumericCellValue() + "";
			String housePerSupplyPercent = houseSheet.getRow(5).getCell(7).getNumericCellValue() + "";
			String houseComBasePercent = houseSheet.getRow(5).getCell(8).getNumericCellValue() + "";
			String houseComSupplyPercent = houseSheet.getRow(5).getCell(9).getNumericCellValue() + "";

			for (int i = 6; i < houseSheet.getLastRowNum(); i++) {
				if (houseSheet.getRow(i) != null) {
					for (int j = 0; j < houseSheet.getRow(i).getLastCellNum(); j++) {
						if(houseSheet.getRow(i).getCell(j) != null) {
							
							houseSheet.getRow(i).getCell(j).setCellType(CellType.STRING); // 将每行单元格的类型都设置为string
						}

					}

					if (houseSheet.getRow(i).getCell(2) !=null && 
							StringUtils.isNotEmpty(houseSheet.getRow(i).getCell(2).getStringCellValue())) {
                        
						Date houseDate=salarySheet.getRow(i).getCell(4).getDateCellValue();								
						
						String houseTime=formater.format(houseDate);  //每一行数据的缴费月份
						String staffNumber=houseSheet.getRow(i).getCell(2).getStringCellValue();
						
						if(!houseTime.equals(houseDate)) {   //与缴费日期进行比较，如果不相同则更新补缴社保公积金
						   //buckleUp=buckleUp.add(new BigDecimal(salarySheet.getRow(i).getCell(18).getStringCellValue()));
							staffSalaryMapper.updateBuckleUp(staffNumber,houseTime,houseSheet.getRow(i).getCell(10).getStringCellValue());
							
						}else {
							
						
						
						StaffSalary staffSalary = new StaffSalary();
						staffSalary.setStaffNumber(staffNumber); // 员工编号
				      
						staffSalary.setPayDate(payDate); // 缴费月份

						staffSalary.setHouseBasePersonalPercent(housePerBasePercent);//公积金个人缴纳基本比例
						staffSalary.setHouseSupplyPersonalPercent(housePerSupplyPercent);//公积金个人缴纳补充比例
						staffSalary.setHouseBaseCompanyPercent(houseComBasePercent);//公积金公司缴纳基本比例
						staffSalary.setHouseSupplyCompanyPercent(houseComSupplyPercent);//公积金公司缴纳补充比例
						staffSalary.setHouseBasePersonal(houseSheet.getRow(i).getCell(6).getStringCellValue());//公积金个人缴纳基本
						staffSalary.setHouseSupplyPersonal(houseSheet.getRow(i).getCell(7).getStringCellValue());//公积金个人缴纳补充
						staffSalary.setHouseBaseCompany(houseSheet.getRow(i).getCell(8).getStringCellValue());//公积金公司缴纳基本
						staffSalary.setHouseSupplyCompany(houseSheet.getRow(i).getCell(9).getStringCellValue());//公积金公司缴纳补充
						staffSalary.setHousePersonalTotal(houseSheet.getRow(i).getCell(10).getStringCellValue());//公积金个人缴纳合计
						staffSalary.setHouseCompanyTotal(houseSheet.getRow(i).getCell(11).getStringCellValue());//公积金公司缴纳合计
						staffSalary.setHouseToatal(houseSheet.getRow(i).getCell(12).getStringCellValue());//公积金缴纳合计
                        
						salaryService.saveOrUpdate(staffSalary);
						}
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("解析社保公积金表失败", e);
			message ="解析社保公积金表失败, "+file.getOriginalFilename()+e.getMessage();

		}

		return message;

	}

}
