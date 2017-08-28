package com.bohai.employeeSalary.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.remoting.exchange.Response;
import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.Department;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.util.MailUtil;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;
import com.bohai.employeeSalary.vo.TableJsonResponse;
import com.bohai.employeeSalary.vo.TreeView;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class SalaryController {
	
	static Logger logger=Logger.getLogger(SalaryController.class);
	
	@Autowired
	private StaffSalaryMapper StaffSalaryMapper;
	@Autowired
	private StaffSalaryService staffSalaryService;
	
	@Autowired
	private MailUtil mailUtil;
	
	@RequestMapping(value="toSalary")
    public String toSalary(){
        return "salary";
    }
	
	
	@RequestMapping(value="querySalaryByParams")
	@ResponseBody
	public TableJsonResponse<StaffSalary> querySalaryByParams(@RequestBody(required = true) QueryStaffSalaryParamVO paramVO){
		
		PageHelper.startPage(paramVO.getPageNumber(), paramVO.getPageSize());
		List<StaffSalary> list=StaffSalaryMapper.queryStaffSalaryByParams(paramVO);
		Page<StaffSalary> page = (Page)list;
		TableJsonResponse<StaffSalary> response = new TableJsonResponse<StaffSalary>();
		
		
		
		response.setTotal(page.getTotal());
		response.setRows(list);		
		return response;
	}
	
	@RequestMapping(value="updateSalary")
	@ResponseBody
	public int updateSalary(@RequestBody(required = true) StaffSalary staffSalary){
		int count=StaffSalaryMapper.updateByStaffNumAndDate(staffSalary);
		return count;
	}

	/**
	 * 发送邮件
	 * 
	 * @author CY
	 * @throws BohaiException 
	 * */
	@RequestMapping(value="sendMail")
	@ResponseBody
	public void sendMail(@RequestBody(required = true) QueryStaffSalaryParamVO paramVO) throws BohaiException{
		XSSFWorkbook wb=new XSSFWorkbook();
		XSSFSheet salarySheet=wb.createSheet("员工工资条");
		String[] salaryHead={"行号","员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","取暖补贴","住房公积金","养老保险",
				"失业保险","医疗保险","实发工资"};
		XSSFRow row=salarySheet.createRow(0);
		
		//初始化表头
		for (int i = 0; i < salaryHead.length; i++) {
			row.createCell(i).setCellValue(salaryHead[i]);
			salarySheet.setColumnWidth(i, 256*15);
		}
		//根据用户编号和时间查询数据
		List<StaffSalary> salaryList=StaffSalaryMapper.queryStaffSalaryByParams(paramVO);
		if(salaryList!=null&&salaryList.size()>0){
			for (int i = 0; i < salaryList.size(); i++) {
				//"员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","取暖补贴","住房公积金","养老保险",
				//"失业保险","医疗保险","实发工资"
				XSSFRow row2=salarySheet.createRow(i+1);
				//行号
				row2.createCell(0).setCellValue(String.valueOf(i+1));
				//员工编号
				row2.createCell(1).setCellValue(salaryList.get(i).getStaffNumber());
				//员工姓名
				row2.createCell(2).setCellValue(salaryList.get(i).getName());
				//岗位工资
				row2.createCell(3).setCellValue(salaryList.get(i).getPositionSalary());
				//技能工资
				row2.createCell(4).setCellValue(salaryList.get(i).getSkillSalary());
				//绩效工资
				row2.createCell(5).setCellValue(salaryList.get(i).getAchiementSalary());
				//司龄工资
				row2.createCell(6).setCellValue(salaryList.get(i).getWorkYears());
				//取暖补贴
				row2.createCell(7).setCellValue(salaryList.get(i).getWarmSubsidy());
				//住房公积金
				row2.createCell(8).setCellValue(salaryList.get(i).getHousePersonalTotal());
				//养老保险
				row2.createCell(9).setCellValue(salaryList.get(i).getPensionPersonal());
				//失业保险
				row2.createCell(10).setCellValue(salaryList.get(i).getUnemploymentPersonal());
				//医疗保险
				row2.createCell(11).setCellValue(salaryList.get(i).getMedicalPersonal());
				//实发工资
				row2.createCell(12).setCellValue(salaryList.get(i).getActualSalary());
				
			}
		}
		
		
		try{
			String FileName = new String(salaryList.get(0).getStaffNumber()+"号员工工资条信息.xls");
			OutputStream output = new FileOutputStream(new File("D:\\"+FileName));
			wb.write(output);
			output.close();
		}catch(IOException e){ logger.error("下载信息失败", e);
        throw new BohaiException("", "下载信息失败");
        }
	
	} 

}
