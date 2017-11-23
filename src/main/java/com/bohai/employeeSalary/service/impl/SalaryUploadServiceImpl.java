package com.bohai.employeeSalary.service.impl;

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
	StaffSalaryService salaryService;

	@Override
	public String upload(MultipartFile file, Object... objects) throws BohaiException {

		HSSFWorkbook wb = null;
		String message = "upload success!";
		try {

			wb = new HSSFWorkbook(file.getInputStream());
			HSSFSheet salarySheet = wb.getSheetAt(0);
			HSSFSheet houseSheet = wb.getSheetAt(1);
			// 读取文件名
			String fileName = file.getOriginalFilename();
			// 获取时间
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(fileName);
			String payDate = new StringBuilder(m.replaceAll("").trim()).insert(4, "-").toString();

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
							salarySheet.getRow(i).getCell(j).setCellType(CellType.STRING); // 将每行单元格的类型都设置为string
						}
					}

					if (salarySheet.getRow(i).getCell(2)!=null && 
							StringUtils.isNotEmpty(salarySheet.getRow(i).getCell(2).getStringCellValue())) {
						StaffSalary staffSalary = new StaffSalary();

						staffSalary.setPensionPersonalPercent(pensionPersonalPercent); // getCell(int column, int row)
						staffSalary.setMedicalPersonalPercent(medicalPersonalPercent);
						staffSalary.setUnemploymentPersonalPercent(unemploymentPersonalPercent);

						staffSalary.setPensionCompanyPercent(pensionCompanyPercent);
						staffSalary.setMedicalCompanyPercent(medicalCompanyPercent);
						staffSalary.setUnemploymentCompanyPercent(unemploymentCompanyPercent);
						staffSalary.setInjuryCompanyPercent(injuryCompanyPercent);
						staffSalary.setBirthCompayPercent(birthCompayPercent);

						/*
						 * HSSFRow row = salarySheet.getRow(i); HSSFCell cell =
						 * salarySheet.getRow(i).getCell(2); cell.setCellType(CellType.STRING);
						 */
						staffSalary.setStaffNumber(salarySheet.getRow(i).getCell(2).getStringCellValue()); // 员工编号
						staffSalary.setStaffDepartmentId(
								salarySheet.getRow(i).getCell(2).getStringCellValue().substring(0, 2)); // 部门编号
						// String
						// payTime=salarySheet.getRow(i).getCell(4).getStringCellValue().replace("年",
						// "-").replace("月", "");
						staffSalary.setPayDate(payDate); // 缴费月份
						staffSalary.setPayBase(salarySheet.getRow(i).getCell(5).getStringCellValue()); // 缴费基数
						staffSalary.setPensionPersonal(salarySheet.getRow(i).getCell(6).getStringCellValue());
						staffSalary.setMedicalPersonal(salarySheet.getRow(i).getCell(7).getStringCellValue());
						staffSalary.setUnemploymentPersonal(salarySheet.getRow(i).getCell(8).getStringCellValue());
						staffSalary.setPersonalReserve1(salarySheet.getRow(i).getCell(9).getStringCellValue());
						staffSalary.setPersonalReserve2(salarySheet.getRow(i).getCell(10).getStringCellValue());
						staffSalary.setPensionCompany(salarySheet.getRow(i).getCell(11).getStringCellValue());
						staffSalary.setMedicalCompany(salarySheet.getRow(i).getCell(12).getStringCellValue());
						staffSalary.setUnemploymentCompany(salarySheet.getRow(i).getCell(13).getStringCellValue());
						staffSalary.setInjuryCompany(salarySheet.getRow(i).getCell(14).getStringCellValue());
						staffSalary.setBirthCompany(salarySheet.getRow(i).getCell(15).getStringCellValue());
						staffSalary.setCompanyReserve1(salarySheet.getRow(i).getCell(16).getStringCellValue());
						staffSalary.setCompanyReserve2(salarySheet.getRow(i).getCell(17).getStringCellValue());
						staffSalary.setPersonalTotal(salarySheet.getRow(i).getCell(18).getStringCellValue());
						staffSalary.setCompanyTotal(salarySheet.getRow(i).getCell(19).getStringCellValue());
						staffSalary.setPaymentTotal(salarySheet.getRow(i).getCell(20).getStringCellValue());

						staffSalary.setCreateTime(CommonUtils.getTime());

					//	message = salaryService.saveOrUpdate(staffSalary);
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

					if (salarySheet.getRow(i).getCell(2) !=null && 
							StringUtils.isNotEmpty(salarySheet.getRow(i).getCell(2).getStringCellValue())) {
						StaffSalary staffSalary = new StaffSalary();

						staffSalary.setStaffNumber(houseSheet.getRow(i).getCell(2).getStringCellValue()); // 员工编号
						// String
						// payTime=salarySheet.getRow(i).getCell(4).getStringCellValue().replace("年",
						// "-").replace("月", "");
						staffSalary.setPayDate(payDate); // 缴费月份

						staffSalary.setHouseBasePersonalPercent(housePerBasePercent);
						staffSalary.setHouseSupplyPersonalPercent(housePerSupplyPercent);
						staffSalary.setHouseBaseCompanyPercent(houseComBasePercent);
						staffSalary.setHouseSupplyCompanyPercent(houseComSupplyPercent);
						staffSalary.setHouseBasePersonal(houseSheet.getRow(i).getCell(6).getStringCellValue());
						staffSalary.setHouseSupplyPersonal(houseSheet.getRow(i).getCell(7).getStringCellValue());
						staffSalary.setHouseBaseCompany(houseSheet.getRow(i).getCell(8).getStringCellValue());
						staffSalary.setHouseSupplyCompany(houseSheet.getRow(i).getCell(9).getStringCellValue());
						staffSalary.setHousePersonalTotal(houseSheet.getRow(i).getCell(10).getStringCellValue());
						staffSalary.setHouseCompanyTotal(houseSheet.getRow(i).getCell(11).getStringCellValue());
						staffSalary.setHouseToatal(houseSheet.getRow(i).getCell(12).getStringCellValue());

					//	message = salaryService.saveOrUpdate(staffSalary);

					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("解析社保公积金表失败", e);
			message ="解析社保公积金表失败 "+e.getMessage();

		}

		return message;

	}

}
