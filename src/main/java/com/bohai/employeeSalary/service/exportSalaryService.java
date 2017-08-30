package com.bohai.employeeSalary.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;

@Service("exportService")
public class exportSalaryService {

	@Autowired
	StaffSalaryMapper   staffSalaryMapper;
	@Autowired
	StaffSalaryMapper  salaryMapper;
 
	
	
	/**
	 * 导出某部门、某月份的工资明细表
	 * @param staffSalary
	 * @return
	 * @throws IOException 
	 */
	public int exportSalary(QueryStaffSalaryParamVO paramVo,HttpServletResponse response) throws IOException {  //paramVo:departNum(可为null) month不为null
		int count=0;
		
		if (paramVo.getDepNum()==null||paramVo.getDepNum()=="") { //部门为null，查询所有部门
			List<String> departIds=salaryMapper.queryDepartIds();   //部门List
			for(int i=0;i<departIds.size();i++) {
				paramVo.setDepNum(departIds.get(i));
				/*查询该部门该月份所有员工的信息*/
				List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
				exportSalary(salaryList,response);
				count+=1;
				
			}
		}
		
		else {
			
			/*查询该部门该月份所有员工的信息*/
			List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
			exportSalary(salaryList,response);
			count+=1;
		}
		
		
		return count;
	}
	
 /**
  * 
  * @param salaryList  要导入的信息列表
  * @throws IOException
  */
	public void exportSalary(List<StaffSalary> salaryList,HttpServletResponse response) throws IOException {   //一共20列   0-19
		 // FileOutputStream fos=new FileOutputStream("E:\\14.xls"); 
		  String[] salaryHead1= {"序号","姓名","岗位工资","绩效工资","司龄","技能工资","应发工资","取暖补贴","扣款","实发工资","邮箱","备注"};  //length=12
		  String[] salaryHead2= {"住房公积","养老保险","失业保险","医疗保险","补扣保险\n公积金","社保公积金\n代扣合计","计税基数","个人所得税","其他款项"};  //length=9
		  XSSFWorkbook wb = new XSSFWorkbook();
	      XSSFSheet sheet=wb.createSheet(salaryList.get(0).getPayDate()+salaryList.get(0).getDepName());    //表名
	      XSSFCellStyle style = wb.createCellStyle();
	      XSSFFont font = wb.createFont(); 
	      font.setBold(true);//粗体显示    
	      font.setFontHeightInPoints((short) 16);//设置字体大小
	      /* 
	       * 设定合并单元格区域范围 
	       *  firstRow  0-based 
	       *  lastRow   0-based 
	       *  firstCol  0-based 
	       *  lastCol   0-based 
	       */  
	      CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 19);   //合并第一行所有的列
	      //在sheet里增加合并单元格  
	      sheet.addMergedRegion(cra);    //第一行
	      Row row= sheet.createRow(0);  	        
	      Cell cell_1 = row.createCell(0);  	        
	      cell_1.setCellValue(salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表");    //第一行标题  "2017年3月郑州部工资明细表"
	      style.setAlignment(HorizontalAlignment.CENTER);
	      style.setFont(font);
	      cell_1.setCellStyle(style);
	      
	          
	      for(int i=0;i<=7;i++) {
	    	  CellRangeAddress cra1=new CellRangeAddress(1, 2, i, i);  
	    	  sheet.addMergedRegion(cra1); 
	      }
	      for(int j=17;j<=19;j++) {
	    	  CellRangeAddress cra2=new CellRangeAddress(1, 2, j, j); 
	    	  sheet.addMergedRegion(cra2); 
	      }
	      CellRangeAddress cra3=new CellRangeAddress(1, 1, 8, 16); 
    	  sheet.addMergedRegion(cra3); 
    	  
    	  XSSFCellStyle style1 = wb.createCellStyle();
	      XSSFFont font2 = wb.createFont(); 
	      font2.setBold(true);//粗体显示    
	      style1.setAlignment(HorizontalAlignment.CENTER);
	      style1.setFont(font2);
	      Row row1 = sheet.createRow(1);      //第二行表头	
	      for(int i=0;i<=8;i++) {
	    	  row1.createCell(i).setCellValue(salaryHead1[i]);
	    	 // sheet.setColumnWidth(i, 256*15);
	    	  row1.getCell(i).setCellStyle(style1);
	      }
	      
	      row1.createCell(17).setCellValue(salaryHead1[9]);
	      row1.createCell(18).setCellValue(salaryHead1[10]);
	      row1.createCell(19).setCellValue(salaryHead1[11]);
	      row1.getCell(17).setCellStyle(style1);
	      row1.getCell(18).setCellStyle(style1);
	      row1.getCell(19).setCellStyle(style1);
	      
	      
	      Row row2 = sheet.createRow(2);      //第三行表头
	      row2.setHeight((short) (40 * 20));  
	      for(int i=8;i<=16;i++) {
	    	  row2.createCell(i).setCellValue(salaryHead2[i-8]);
	    	  sheet.setColumnWidth(i, 256*12);  	
	    	  row2.getCell(i).setCellStyle(style1);
	      }   
	      
	      //向想表格中填入数据
	      for(int j=0;j<salaryList.size();j++) {
	    	  Row sheetRow= sheet.createRow(j+2);
	    	  
	    	  row2.createCell(0).setCellValue(j);   //序号   Optional.ofNullable(salary.getAchiementSalary()).orElse("0")
	    	  row2.createCell(1).setCellValue(salaryList.get(j).getName());
	    	  row2.createCell(2).setCellValue(Optional.ofNullable(salaryList.get(j).getPositionSalary()).orElse("0.00"));
	    	  row2.createCell(3).setCellValue(Optional.ofNullable(salaryList.get(j).getAchiementSalary()).orElse("0.00"));
	    	  row2.createCell(4).setCellValue(Optional.ofNullable(salaryList.get(j).getWorkYears()).orElse("0.00"));
	    	  row2.createCell(5).setCellValue(Optional.ofNullable(salaryList.get(j).getSkillSalary()).orElse("0.00"));
	    	  row2.createCell(6).setCellValue(0);   //应发工资
	    	  row2.createCell(7).setCellValue(Optional.ofNullable(salaryList.get(j).getWarmSubsidy()).orElse("0.00"));   
	    	  row2.createCell(8).setCellValue(Optional.ofNullable(salaryList.get(j).getHousePersonalTotal()).orElse("0.00"));
	    	  row2.createCell(9).setCellValue(Optional.ofNullable(salaryList.get(j).getPensionPersonal()).orElse("0.00"));
	    	  row2.createCell(10).setCellValue(Optional.ofNullable(salaryList.get(j).getUnemploymentPersonal()).orElse("0.00"));
	    	  row2.createCell(11).setCellValue(Optional.ofNullable(salaryList.get(j).getMedicalPersonal()).orElse("0.00"));
	    	  row2.createCell(12).setCellValue(0.00);   //补扣保险公积金,暂为0
	    	  row2.createCell(13).setCellValue(Double.parseDouble(Optional.ofNullable(salaryList.get(j).getPersonalTotal()).orElse("0.00"))+Double.parseDouble(Optional.ofNullable((salaryList.get(j).getHousePersonalTotal())).orElse("0.00")));       //社保公积金代扣合计
	    	  row2.createCell(14).setCellValue(0);   //计税基数  
	    	  row2.createCell(15).setCellValue(0);   //个人所得税 
	    	  row2.createCell(16).setCellValue(Optional.ofNullable(salaryList.get(j).getSalaryOther()).orElse("0.00"));   
	    	  row2.createCell(17).setCellValue(Optional.ofNullable(salaryList.get(j).getActualSalary()).orElse("0.00"));
	    	  row2.createCell(18).setCellValue(Optional.ofNullable(salaryList.get(j).getEmail()).orElse(""));
	    	  row2.createCell(19).setCellValue("");   //备注    	  
	    	 
	    	  
	    	  
	      }
	      
	      
	        try {
	            OutputStream output=response.getOutputStream();
	            response.reset();
	            response.setContentType("application/x-xls");  
	            response.setCharacterEncoding("UTF-8");  
	            String FileName = new String("客户信息".getBytes("UTF-8"),"ISO-8859-1");
	            response.setHeader("Content-Disposition", "attachment;filename="+FileName+".xlsx");
	            wb.write(output);  
	            output.close(); 
	        } catch (IOException e) {
	        	e.printStackTrace();
	        	System.out.println("导出工资明细表失败");
	        }
	}
	
	
	
	 
	 
	public static void main(String[] args) {
		/*try {
			exportSalary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
