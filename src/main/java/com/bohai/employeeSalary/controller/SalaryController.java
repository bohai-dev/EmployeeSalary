package com.bohai.employeeSalary.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.service.exportSalaryService;
import com.bohai.employeeSalary.util.CommonUtils;
import com.bohai.employeeSalary.util.MailUtil;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;
import com.bohai.employeeSalary.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
public class SalaryController{
	
	@Autowired
	static Logger logger=Logger.getLogger(SalaryController.class);
	
	@Autowired
	private StaffSalaryMapper StaffSalaryMapper;
	

	@Autowired
	private StaffSalaryService salaryService;


	@Autowired
	private exportSalaryService exportService;


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
		//后台修改个人社保缴纳合计、公司社保缴纳合计、社保缴纳总计、公积金个人缴纳合计、公积金公司缴纳合计、公积金缴纳合计
		double personalTotal=Optional.ofNullable(staffSalary.getPensionPersonal()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				             Optional.ofNullable(staffSalary.getMedicalPersonal()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				             Optional.ofNullable(staffSalary.getUnemploymentPersonal()).filter(v->v.length()>0).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				             Optional.ofNullable(staffSalary.getPersonalReserve1()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				             Optional.ofNullable(staffSalary.getPersonalReserve2()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00);
		
		double companyTotal=Optional.ofNullable(staffSalary.getPensionCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				            Optional.ofNullable(staffSalary.getMedicalCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				            Optional.ofNullable(staffSalary.getUnemploymentCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				            Optional.ofNullable(staffSalary.getInjuryCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				            Optional.ofNullable(staffSalary.getBirthCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				            Optional.ofNullable(staffSalary.getCompanyReserve1()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
				            Optional.ofNullable(staffSalary.getCompanyReserve2()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00);
	   double paymentTotal=personalTotal+companyTotal;		
	   
	   double housePersonalTotal=Optional.ofNullable(staffSalary.getHouseBasePersonal()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
			                     Optional.ofNullable(staffSalary.getHouseSupplyPersonal()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00);
	   
	   double houseCompanyTotal=Optional.ofNullable(staffSalary.getHouseBaseCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00)+
			                    Optional.ofNullable(staffSalary.getHouseSupplyCompany()).filter(v->v.length()>0).map(v->Double.valueOf(v)).orElse(0.00);
	   
	   double houseToatal=housePersonalTotal+houseCompanyTotal;

	   
	    staffSalary.setPersonalTotal(String.valueOf(CommonUtils.getRound(new BigDecimal(personalTotal))));
		staffSalary.setCompanyTotal(String.valueOf(CommonUtils.getRound(new BigDecimal(companyTotal))));
		staffSalary.setPaymentTotal(String.valueOf(CommonUtils.getRound(new BigDecimal(paymentTotal))));
		staffSalary.setHousePersonalTotal(String.valueOf(CommonUtils.getRound(new BigDecimal(housePersonalTotal))));
		staffSalary.setHouseCompanyTotal(String.valueOf(CommonUtils.getRound(new BigDecimal(houseCompanyTotal))));
		staffSalary.setHouseToatal(String.valueOf(CommonUtils.getRound(new BigDecimal(houseToatal))));
		
		
		int count=StaffSalaryMapper.updateByStaffNumAndDate(staffSalary);  //更新其他款项	
		
		QueryStaffSalaryParamVO paramVo=new QueryStaffSalaryParamVO();
		paramVo.setStaffNum(staffSalary.getStaffNumber());
		paramVo.setPayDate(staffSalary.getPayDate());
		salaryService.updateSalary(paramVo);  //修改信息之后要重新计算工资并更新
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
	 * 导出报表
	 * @param 
	 * @return
	 */
	@RequestMapping(value="exportSalary")
	public void exportSalary(QueryStaffSalaryParamVO paramVo,HttpServletRequest request, HttpServletResponse response){
		int count=0;
		try {
			count = exportService.exportSalary(paramVo,request,response);
		} catch (BohaiException e) {

			
		}
		
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
		
		XSSFCellStyle style = wb.createCellStyle();
	      XSSFFont font = wb.createFont(); 
	      font.setBold(true);//粗体显示    
	      style.setAlignment(HorizontalAlignment.CENTER);
	      style.setFont(font);
	      //设置上下左右四个边框宽度
          style.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
          style.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
          style.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
          style.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
          //设置上下左右四个边框颜色
          style.setTopBorderColor(HSSFColor.BLACK.index);
          style.setBottomBorderColor(HSSFColor.BLACK.index);
          style.setLeftBorderColor(HSSFColor.BLACK.index);
          style.setRightBorderColor(HSSFColor.BLACK.index);
          
	      XSSFSheet salarySheet=wb.createSheet("员工工资条");
		String[] salaryHead={"行号","员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","应发工资","取暖补贴","住房公积金","养老保险",
				"失业保险","医疗保险","计税基数","个人所得税","实发工资"};
		XSSFRow row=salarySheet.createRow(0);
		
		//初始化表头
		for (int i = 0; i < salaryHead.length; i++) {
			row.createCell(i).setCellValue(salaryHead[i]);
			salarySheet.setColumnWidth(i, 256*15);
			row.getCell(i).setCellStyle(style);
		}
		
		XSSFCellStyle style1 = wb.createCellStyle();
	      style1.setAlignment(HorizontalAlignment.CENTER);
	      //设置上下左右四个边框宽度
        style1.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
        style1.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
        style1.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
        style1.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
        //设置上下左右四个边框颜色
        style1.setTopBorderColor(HSSFColor.BLACK.index);
        style1.setBottomBorderColor(HSSFColor.BLACK.index);
        style1.setLeftBorderColor(HSSFColor.BLACK.index);
        style1.setRightBorderColor(HSSFColor.BLACK.index);
		
	
		//根据用户编号和时间查询数据
		List<StaffSalary> salaryList=StaffSalaryMapper.queryStaffSalaryByParams(paramVO);
		if(salaryList!=null&&salaryList.size()>0){
			for (int i = 0; i < salaryList.size(); i++) {
				//"员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","取暖补贴","住房公积金","养老保险",
				//"失业保险","医疗保险","实发工资"
				XSSFRow row2=salarySheet.createRow(1);
				//行号
				row2.createCell(0).setCellValue(String.valueOf(i+1));
				//员工编号
				row2.createCell(1).setCellValue(salaryList.get(i).getStaffNumber());
				//员工姓名
				row2.createCell(2).setCellValue(salaryList.get(i).getName());
				//岗位工资
				if(salaryList.get(i).getPositionSalary()==null){
					row2.createCell(3).setCellValue(0.00);
				}else{
				row2.createCell(3).setCellValue(salaryList.get(i).getPositionSalary());
				}
				//技能工资
				if(salaryList.get(i).getSkillSalary()==null){
					row2.createCell(4).setCellValue(0.00);
				}else{
				row2.createCell(4).setCellValue(salaryList.get(i).getSkillSalary());
				}
				//绩效工资
				if(salaryList.get(i).getAchiementSalary()==null){
					row2.createCell(5).setCellValue(0.00);
				}else{
				row2.createCell(5).setCellValue(salaryList.get(i).getAchiementSalary());
				}
				//司龄工资
				if(salaryList.get(i).getWorkYears()==null){
					row2.createCell(6).setCellValue(0.00);
				}else{
				row2.createCell(6).setCellValue(salaryList.get(i).getWorkYears());
				}
				
				//应发工资
				row2.createCell(7).setCellValue(salaryList.get(i).getGrossSalary());
				
				//取暖补贴
				if(salaryList.get(i).getWarmSubsidy()==null){
					row2.createCell(8).setCellValue(0.00);
				}else{
				row2.createCell(8).setCellValue(salaryList.get(i).getWarmSubsidy());
				}
				//住房公积金
				if(salaryList.get(i).getHousePersonalTotal()==null){
					row2.createCell(9).setCellValue(0.00);
				}else{}
				row2.createCell(9).setCellValue(salaryList.get(i).getHousePersonalTotal());
				//养老保险
				if(salaryList.get(i).getPensionPersonal()==null){
					row2.createCell(10).setCellValue(0.00);
				}else{
				row2.createCell(10).setCellValue(salaryList.get(i).getPensionPersonal());
				}
				//失业保险
				if(salaryList.get(i).getUnemploymentPersonal()==null){
					row2.createCell(11).setCellValue(0.00);
				}else{
				row2.createCell(11).setCellValue(salaryList.get(i).getUnemploymentPersonal());
				}
				//医疗保险
				if(salaryList.get(i).getMedicalPersonal()==null){
					row2.createCell(12).setCellValue(0.00);
				}else{
				row2.createCell(12).setCellValue(salaryList.get(i).getMedicalPersonal());
				}
				//计税基数
				row2.createCell(13).setCellValue(salaryList.get(i).getTaxBase());
				//个人所得税
				row2.createCell(14).setCellValue(salaryList.get(i).getIncomeTax());
				//实发工资
				row2.createCell(15).setCellValue(salaryList.get(i).getActualSalary());
			
				for(int j=0;j<16;j++){
					row2.getCell(j).setCellStyle(style1);
				}
			}
		}
		
		
		String FileName = new String(salaryList.get(0).getStaffNumber()+"号员工工资条信息.xlsx");
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
						XSSFCellStyle style = wb.createCellStyle();
					      XSSFFont font = wb.createFont(); 
					      font.setBold(true);//粗体显示    
					      style.setAlignment(HorizontalAlignment.CENTER);
					      style.setFont(font);
					      //设置上下左右四个边框宽度
				          style.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
				          style.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
				          style.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
				          style.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
				          //设置上下左右四个边框颜色
				          style.setTopBorderColor(HSSFColor.BLACK.index);
				          style.setBottomBorderColor(HSSFColor.BLACK.index);
				          style.setLeftBorderColor(HSSFColor.BLACK.index);
				          style.setRightBorderColor(HSSFColor.BLACK.index);
						
						XSSFSheet salarySheet=wb.createSheet("员工工资条");
						String[] salaryHead={"行号","员工编号","员工姓名","岗位工资","技能工资","绩效工资","司龄工资","应发工资","取暖补贴","住房公积金","养老保险",
								"失业保险","医疗保险","计税基数","个人所得税","实发工资"};
						XSSFRow row=salarySheet.createRow(0);
						
						//初始化表头
						for (int j = 0; j < salaryHead.length; j++) {
							row.createCell(j).setCellValue(salaryHead[j]);
							salarySheet.setColumnWidth(j, 256*15);
							row.getCell(j).setCellStyle(style);
						}
						
						XSSFCellStyle style1 = wb.createCellStyle();
					      style1.setAlignment(HorizontalAlignment.CENTER);
					      //设置上下左右四个边框宽度
				        style1.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
				        style1.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
				        style1.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
				        style1.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
				        //设置上下左右四个边框颜色
				        style1.setTopBorderColor(HSSFColor.BLACK.index);
				        style1.setBottomBorderColor(HSSFColor.BLACK.index);
				        style1.setLeftBorderColor(HSSFColor.BLACK.index);
				        style1.setRightBorderColor(HSSFColor.BLACK.index);
						
						
						XSSFRow row2=salarySheet.createRow(1);
						//行号
						row2.createCell(0).setCellValue(String.valueOf(1));
						//员工编号
						row2.createCell(1).setCellValue(salaryList.get(i).getStaffNumber());
						//员工姓名
						row2.createCell(2).setCellValue(salaryList.get(i).getName());
						//岗位工资
						if(salaryList.get(i).getPositionSalary()==null){
							row2.createCell(3).setCellValue(0.00);
						}else{
						row2.createCell(3).setCellValue(salaryList.get(i).getPositionSalary());
						}
						//技能工资
						if(salaryList.get(i).getSkillSalary()==null){
							row2.createCell(4).setCellValue(0.00);
						}else{
						row2.createCell(4).setCellValue(salaryList.get(i).getSkillSalary());
						}
						//绩效工资
						if(salaryList.get(i).getAchiementSalary()==null){
							row2.createCell(5).setCellValue(0.00);
						}else{
						row2.createCell(5).setCellValue(salaryList.get(i).getAchiementSalary());
						}
						//司龄工资
						if(salaryList.get(i).getWorkYears()==null){
							row2.createCell(6).setCellValue(0.00);
						}else{
						row2.createCell(6).setCellValue(salaryList.get(i).getWorkYears());
						}
						
						//应发工资
						row2.createCell(7).setCellValue(salaryList.get(i).getGrossSalary());
						
						//取暖补贴
						if(salaryList.get(i).getWarmSubsidy()==null){
							row2.createCell(8).setCellValue(0.00);
						}else{
						row2.createCell(8).setCellValue(salaryList.get(i).getWarmSubsidy());
						}
						//住房公积金
						if(salaryList.get(i).getHousePersonalTotal()==null){
							row2.createCell(9).setCellValue(0.00);
						}else{}
						row2.createCell(9).setCellValue(salaryList.get(i).getHousePersonalTotal());
						//养老保险
						if(salaryList.get(i).getPensionPersonal()==null){
							row2.createCell(10).setCellValue(0.00);
						}else{
						row2.createCell(10).setCellValue(salaryList.get(i).getPensionPersonal());
						}
						//失业保险
						if(salaryList.get(i).getUnemploymentPersonal()==null){
							row2.createCell(11).setCellValue(0.00);
						}else{
						row2.createCell(11).setCellValue(salaryList.get(i).getUnemploymentPersonal());
						}
						//医疗保险
						if(salaryList.get(i).getMedicalPersonal()==null){
							row2.createCell(12).setCellValue(0.00);
						}else{
						row2.createCell(12).setCellValue(salaryList.get(i).getMedicalPersonal());
						}
						//计税基数
						row2.createCell(13).setCellValue(salaryList.get(i).getTaxBase());
						//个人所得税
						row2.createCell(14).setCellValue(salaryList.get(i).getIncomeTax());
						//实发工资
						row2.createCell(15).setCellValue(salaryList.get(i).getActualSalary());
						
						for(int k=0;k<16;k++){
							row2.getCell(k).setCellStyle(style1);
						}
						
				
						FileNames[i]=salaryList.get(i).getStaffNumber()+"号员工"+salaryList.get(i).getPayDate()+"工资条信息.xlsx";
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