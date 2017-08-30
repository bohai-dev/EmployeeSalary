package com.bohai.employeeSalary.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.util.MailUtil;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;
import com.bohai.employeeSalary.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class SalaryController{
	
	@Autowired
	static Logger logger=Logger.getLogger(SalaryController.class);
	
	@Autowired
	private StaffSalaryMapper StaffSalaryMapper;
	
	@Autowired
	private StaffSalaryService salaryService;

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
	 * 计算工资
	 * @param staffSalary
	 * @return
	 */
	@RequestMapping(value="calculateSalary")
	@ResponseBody
	public int calculateSalary(@RequestBody(required = true)QueryStaffSalaryParamVO paramVo){
		int count=salaryService.updateSalary(paramVo);
		
		return count;

	}

	/**
	 * 单发送邮件
	 * 
	 * @author CY
	 * @throws BohaiException 
	 * @throws MessagingException 
	 * @throws IOException 
	 * */
	@RequestMapping(value="sendMail")
	@ResponseBody
	public void sendMail(@RequestBody(required = true) QueryStaffSalaryParamVO paramVO) throws BohaiException, MessagingException, IOException{
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
		
		
		String FileName = new String(salaryList.get(0).getStaffNumber()+"号员工工资条信息.xls");
		String FileUrl=new String("D:\\"+FileName);
		OutputStream outputStream =new FileOutputStream(new File(FileUrl));
		wb.write(outputStream);
		String subject=new String(salaryList.get(0).getStaffNumber()+"号"+salaryList.get(0).getName()+salaryList.get(0).getPayDate()+"工资条信息");
		String content=new String(salaryList.get(0).getName()+"员工  ,您好!"
				+ "———" +salaryList.get(0).getPayDate()+"工资条信息，请查看!");
		mailUtil.send(salaryList.get(0).getEmail(),subject,content,FileName,FileUrl);
		outputStream.close();
	}


	/**
	 * 群发邮件
	 * 
	 * @author CY
	 * */
	@RequestMapping(value="sendMails")
	@ResponseBody
	public void sendMails(@RequestBody(required = true) QueryStaffSalaryParamVO paramVO) throws BohaiException, MessagingException, IOException{
		//根据时间查询多用户数据
				List<StaffSalary> salaryList=StaffSalaryMapper.queryStaffSalaryByParams(paramVO);
				String[] FileNames=new String[salaryList.size()];
				String[] FileUrls=new String[salaryList.size()];
				String[] subjects=new String[salaryList.size()];
				String[] contents=new String[salaryList.size()];
				String[] recipients=new String[salaryList.size()];
				
				if(salaryList!=null&&salaryList.size()>0){
					
					for (int i = 0; i < salaryList.size(); i++) {
						//"员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","取暖补贴","住房公积金","养老保险",
						//"失业保险","医疗保险","实发工资"
						XSSFWorkbook wb=new XSSFWorkbook();
						XSSFSheet salarySheet=wb.createSheet("员工工资条");
						String[] salaryHead={"行号","员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","取暖补贴","住房公积金","养老保险",
								"失业保险","医疗保险","实发工资"};
						XSSFRow row=salarySheet.createRow(0);
						
						//初始化表头
						for (int j = 0; j < salaryHead.length; j++) {
							row.createCell(i).setCellValue(salaryHead[j]);
							salarySheet.setColumnWidth(j, 256*15);
						}
						
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
				
						FileNames[i]=salaryList.get(i).getStaffNumber()+"号员工"+salaryList.get(i).getPayDate()+"工资条信息.xls";
						FileUrls[i]="D:\\"+FileNames[i];
						OutputStream outputStream =new FileOutputStream(new File(FileUrls[i]));
						wb.write(outputStream);
						subjects[i]=salaryList.get(i).getStaffNumber()+"号"+salaryList.get(i).getName()+salaryList.get(i).getPayDate()+"工资条信息";
						contents[i]=salaryList.get(i).getName()+"员工  ,您好!"
								+ "———" +salaryList.get(i).getPayDate()+"工资条信息，请查看!";
						recipients[i]=salaryList.get(i).getEmail();
						System.out.println(recipients[i]);
						outputStream.close();
					}
					mailUtil.send(recipients,subjects,contents,FileNames,FileUrls);
				}
	}












}	