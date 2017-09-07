package com.bohai.employeeSalary.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.util.ZipUtil;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;

@Service("exportService")
public class exportSalaryService {
	
	static Logger logger = Logger.getLogger(exportSalaryService.class);

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
	public int exportSalary(QueryStaffSalaryParamVO paramVo,HttpServletRequest request,HttpServletResponse response) throws BohaiException {  //paramVo:departNum(可为null) month不为null
		int count=0;
		
		String path=request.getSession().getServletContext().getRealPath("/")+"/savedFolder/";
		File dirFile=new File(path);
		if (!dirFile.exists()) {
			if (dirFile.mkdirs()) {
				System.out.println("文件夹创建成功！创建后的目录为"+dirFile.getPath());
			}
		}
		if (paramVo.getDepNum()==null||paramVo.getDepNum()=="") { //部门为null，查询所有部门
			List<File> fileList=new  ArrayList<File>();
			List<String> departIds=salaryMapper.queryDepartIds();   //部门List
			for(int i=0;i<departIds.size();i++) {
				paramVo.setDepNum(departIds.get(i));
				/*查询该部门该月份所有员工的信息*/
				List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
				
				try {
					XSSFWorkbook workbook=exportSalary(salaryList,response);
					//FileOutputStream fos=new FileOutputStream(salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表");
					File f = new File(path+salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表.xlsx");
					workbook.write(new FileOutputStream(f)); 
					fileList.add(f);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BohaiException("", "生成工资明细文件失败");
				} 
				count+=1;				
			}
			//输出压缩文件
			try {
	            OutputStream output=response.getOutputStream();
	            response.reset();
	            response.setContentType("application/zip");  
	            response.setCharacterEncoding("UTF-8");  
	            String fileName = new String(("各部门工资明细表").getBytes("UTF-8"),"ISO-8859-1");
	            File zipFile=new File(path+"各部门工资明细表.zip");
	          
	            ZipUtil.zipFiles(fileList, zipFile);
	            response.setHeader("Content-Disposition", "attachment;filename="+fileName+".zip");
	            FileInputStream inputStream = new FileInputStream(zipFile);
	            int length = 0;
	            byte[] buf = new byte[1024];
	            while ((length = inputStream.read(buf)) > 0) {	            	
	            	    output.write(buf, 0, length);
	            	    
	                }
	            output.flush();
	            output.close();
	        } catch (IOException e) {
	        	logger.error("导出压缩文件失败",e);
	        	System.out.println("导出压缩文件失败");
	        	throw new BohaiException("", "导出压缩文件失败");
	        }
		}
		
		else {
			
			/*查询该部门该月份所有员工的信息*/
			List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
			XSSFWorkbook wb=exportSalary(salaryList,response);
			
			 try {
		            OutputStream output=response.getOutputStream();
		            response.reset();
		            response.setContentType("application/x-xls");  
		            response.setCharacterEncoding("UTF-8");  
		            String FileName = new String((salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表").getBytes("UTF-8"),"ISO-8859-1");
		            response.setHeader("Content-Disposition", "attachment;filename="+FileName+".xlsx");
		            wb.write(output);  
		            output.close(); 
		        } catch (IOException e) {
		        	logger.error("导出工资明细表失败",e);
		        	System.out.println("导出工资明细表失败");
		        	throw new BohaiException("", "导出工资明细表失败");
		        }
			count+=1;
		}
		
		
		return count;
	}
	
 /**
  * 
  * @param salaryList  要导入的信息列表
 * @throws BohaiException 
  * @throws IOException
  */
	public XSSFWorkbook exportSalary(List<StaffSalary> salaryList,HttpServletResponse response) throws BohaiException{   //一共20列   0-19
		 // FileOutputStream fos=new FileOutputStream("E:\\14.xls"); 
		  String[] salaryHead1= {"序号","姓名","岗位工资","绩效工资","司龄","技能工资","应发工资","取暖补贴","扣款","实发工资","邮箱","备注"};  //length=12
		  String[] salaryHead2= {"住房公积","养老保险","失业保险","医疗保险","补扣保险\n公积金","社保公积金\n代扣合计","计税基数","个人所得税","其他款项"};  //length=9
		  XSSFWorkbook wb = new XSSFWorkbook();
	      XSSFSheet sheet=wb.createSheet(salaryList.get(0).getPayDate()+salaryList.get(0).getDepName());    //表名
	      XSSFCellStyle style = wb.createCellStyle();
	      XSSFFont font = wb.createFont(); 
	      font.setBold(true);//粗体显示    
	      font.setFontHeightInPoints((short) 16);//设置字体大小
	      style.setFont(font);
		  XSSFCellStyle style3 = setStyle(wb.createCellStyle());   //表格内容的样式
	      
		  
		  Row row= sheet.createRow(0);  	
		  row.setHeightInPoints(30);    //设置行的高度
	      Cell cell_1 = row.createCell(0);  
	      /* 
	       * 设定合并单元格区域范围 
	       *  firstRow  0-based 
	       *  lastRow   0-based 
	       *  firstCol  0-based 
	       *  lastCol   0-based 
	       */  
	      CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 19);   //合并第一行所有的列
	      //在sheet里增加合并单元格  
	      sheet.addMergedRegion(cra);    //第一行	     标题	    
	      setRegionBorder(1, cra, sheet);
	      cell_1.setCellValue(salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表");    //第一行标题  "2017年3月郑州部工资明细表"	     
	      style=setStyle(style);
	      cell_1.setCellStyle(style);
	      
		  XSSFCellStyle style1 = wb.createCellStyle();
	      XSSFFont font2 = wb.createFont(); 
	      font2.setBold(true);//粗体显示    
	      style1.setAlignment(HorizontalAlignment.CENTER);
	      style1.setFont(font2);
	      style1=setStyle(style1);
	      Row row1 = sheet.createRow(1);      //第二行表头	
	      for(int i=0;i<8;i++) {
	    	  Cell cell1=row1.createCell(i);
	    	  CellRangeAddress cra1=new CellRangeAddress(1, 2, i, i);  
	    	  sheet.addMergedRegion(cra1); 
	    	  setRegionBorder(1,cra1,sheet);
	    	
	    	  cell1.setCellValue(salaryHead1[i]);   	
	    	  cell1.setCellStyle(style1);
	      }    
	   /*   for(int i=0;i<=7;i++) {
	    	  CellRangeAddress cra1=new CellRangeAddress(1, 2, i, i);  
	    	  sheet.addMergedRegion(cra1); 
	    	  setRegionBorder(5,cra1,sheet);
	      }*/
	     
	      
	      Cell cell2=row1.createCell(8);
	      CellRangeAddress cra3=new CellRangeAddress(1, 1, 8, 16);     //扣款
    	  sheet.addMergedRegion(cra3); 
    	  setRegionBorder(1,cra3,sheet);
    	  cell2.setCellValue(salaryHead1[8]);
    	  cell2.setCellStyle(style1);
    
	    
	/*      Row row1 = sheet.createRow(1);      //第二行表头	
	      for(int i=0;i<=8;i++) {
	    	  row1.createCell(i).setCellValue(salaryHead1[i]);   	
	    	  row1.getCell(i).setCellStyle(style1);
	      }*/
	      
	      row1.createCell(17).setCellValue(salaryHead1[9]);
	      row1.createCell(18).setCellValue(salaryHead1[10]);  
	      row1.createCell(19).setCellValue(salaryHead1[11]);
	      for(int j=17;j<=19;j++) {
	    	  CellRangeAddress cra2=new CellRangeAddress(1, 2, j, j); 
	    	  sheet.addMergedRegion(cra2); 
	    	  setRegionBorder(1,cra2,sheet);
	      }
	      row1.getCell(17).setCellStyle(style1);
	      row1.getCell(18).setCellStyle(style1);
	      row1.getCell(19).setCellStyle(style1);
	    
	      
	      Row row2 = sheet.createRow(2);      //第三行表头
	      row2.setHeight((short) (35 * 20));  
	      for(int i=0;i<=19;i++) {
	    	  if (i<8||i>16) {
	    		  row2.createCell(i).setCellValue(" ");
			}else {
				  row2.createCell(i).setCellValue(salaryHead2[i-8]);
				  sheet.setColumnWidth(i, 256*12);  	
			}
	    	
	    	  
	    	  style1.setWrapText(true);
	    	  row2.getCell(i).setCellStyle(style1);
	      }   
	   
	      double   totalPostion=0,totalAchieve=0,totalYears=0,totalSkill=0,totalShould=0,totalWarm=0,totalHouse=0,
	    		  totalPension=0,totalUnemployment=0,totalMedical=0,totalSupplyInsurance=0,totalPersonal=0,totalTaxBase=0,totalTax=0,totalOther=0,totalActural=0;   //16
	    		  
	      //向表格中填入数据
	      for(int j=0;j<salaryList.size();j++) {
	    	  Row sheetRow= sheet.createRow(j+3);
	    	  
	    	  sheetRow.createCell(0).setCellValue(j+1);   //序号   Optional.ofNullable(salary.getAchiementSalary()).orElse("0")
	    	  sheetRow.createCell(1).setCellValue(salaryList.get(j).getName());
	    	  sheetRow.createCell(2).setCellValue(Optional.ofNullable(salaryList.get(j).getPositionSalary()).orElse("0.00"));
	    	  sheetRow.createCell(3).setCellValue(Optional.ofNullable(salaryList.get(j).getAchiementSalary()).orElse("0.00"));
	    	  sheetRow.createCell(4).setCellValue(Optional.ofNullable(salaryList.get(j).getWorkYears()).orElse("0.00"));
	    	  sheetRow.createCell(5).setCellValue(Optional.ofNullable(salaryList.get(j).getSkillSalary()).orElse("0.00"));
	    	  sheetRow.createCell(6).setCellValue(Optional.ofNullable(salaryList.get(j).getGrossSalary()).orElse("0.00"));   //应发工资
	    	  sheetRow.createCell(7).setCellValue(Optional.ofNullable(salaryList.get(j).getWarmSubsidy()).orElse("0.00"));   
	    	  sheetRow.createCell(8).setCellValue(Optional.ofNullable(salaryList.get(j).getHousePersonalTotal()).orElse("0.00"));
	    	  sheetRow.createCell(9).setCellValue(Optional.ofNullable(salaryList.get(j).getPensionPersonal()).orElse("0.00"));
	    	  sheetRow.createCell(10).setCellValue(Optional.ofNullable(salaryList.get(j).getUnemploymentPersonal()).orElse("0.00"));
	    	  sheetRow.createCell(11).setCellValue(Optional.ofNullable(salaryList.get(j).getMedicalPersonal()).orElse("0.00"));
	    	  sheetRow.createCell(12).setCellValue(0.00);   //补扣保险公积金,暂为0
	    	  sheetRow.createCell(13).setCellValue(Double.parseDouble(Optional.ofNullable(salaryList.get(j).getPersonalTotal()).orElse("0.00"))+Double.parseDouble(Optional.ofNullable((salaryList.get(j).getHousePersonalTotal())).orElse("0.00")));       //社保公积金代扣合计
	    	  sheetRow.createCell(14).setCellValue(Optional.ofNullable(salaryList.get(j).getTaxBase()).orElse("0.00"));   //计税基数  
	    	  sheetRow.createCell(15).setCellValue(Optional.ofNullable(salaryList.get(j).getIncomeTax()).orElse("0.00"));   //个人所得税 
	    	  sheetRow.createCell(16).setCellValue(Optional.ofNullable(salaryList.get(j).getSalaryOther()).orElse("0.00"));   
	    	  sheetRow.createCell(17).setCellValue(Optional.ofNullable(salaryList.get(j).getActualSalary()).orElse("0.00"));
	    	  sheetRow.createCell(18).setCellValue(Optional.ofNullable(salaryList.get(j).getEmail()).orElse(""));
	    	  sheetRow.createCell(19).setCellValue(" ");   //备注    	 
	    	  
	    	  totalPostion+=Optional.ofNullable(salaryList.get(j).getPositionSalary()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalAchieve+=Optional.ofNullable(salaryList.get(j).getAchiementSalary()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalYears+=Optional.ofNullable(salaryList.get(j).getWorkYears()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalSkill+=Optional.ofNullable(salaryList.get(j).getSkillSalary()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalShould+=Optional.ofNullable(salaryList.get(j).getGrossSalary()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalWarm+=Optional.ofNullable(salaryList.get(j).getWarmSubsidy()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalHouse+=Optional.ofNullable(salaryList.get(j).getHousePersonalTotal()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  
	    	  totalPension+=Optional.ofNullable(salaryList.get(j).getPensionPersonal()).map(v->Double.parseDouble(v)).orElse(0.00);  	  
	    	  totalUnemployment+=Optional.ofNullable(salaryList.get(j).getUnemploymentPersonal()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalMedical+=Optional.ofNullable(salaryList.get(j).getMedicalPersonal()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalSupplyInsurance+=0;   //补扣保险公积金
	    	  totalPersonal+=Optional.ofNullable(salaryList.get(j).getPersonalTotal()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalTaxBase+=Optional.ofNullable(salaryList.get(j).getTaxBase()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalTax+=Optional.ofNullable(salaryList.get(j).getIncomeTax()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalOther+=Optional.ofNullable(salaryList.get(j).getSalaryOther()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  totalActural+=Optional.ofNullable(salaryList.get(j).getActualSalary()).map(v->Double.parseDouble(v)).orElse(0.00);
	    	  
	    	  //totalPesonal=0,totalTaxBase=0,totalTax=0,totalOther=0,totalActural=0;
	    	  
	    
	    	
	      }
	      Row totalRow= sheet.createRow(3+salaryList.size());   //合计行
	      totalRow.createCell(0).setCellValue(" ");
	      totalRow.createCell(1).setCellValue("合计");
	      totalRow.createCell(2).setCellValue(totalPostion+"");
	      totalRow.createCell(3).setCellValue(totalAchieve+"");
	      totalRow.createCell(4).setCellValue(totalYears+"");
	      totalRow.createCell(5).setCellValue(totalSkill+"");
	      totalRow.createCell(6).setCellValue(totalShould+"");
	      totalRow.createCell(7).setCellValue(totalWarm+"");
	      totalRow.createCell(8).setCellValue(totalHouse+"");
	      totalRow.createCell(9).setCellValue(totalPension+"");
	      totalRow.createCell(10).setCellValue(totalUnemployment+"");
	      totalRow.createCell(11).setCellValue(totalMedical+"");
	      totalRow.createCell(12).setCellValue(totalSupplyInsurance+"");
	      totalRow.createCell(13).setCellValue(totalPersonal+"");
	      totalRow.createCell(14).setCellValue(totalTaxBase+"");
	      totalRow.createCell(15).setCellValue(totalTax+"");
	      totalRow.createCell(16).setCellValue(totalOther+"");
	      totalRow.createCell(17).setCellValue(totalActural+"");
	    
	     
	      
	      
	      Row lastRow= sheet.createRow(5+salaryList.size());
	      lastRow.createCell(0).setCellValue("总经理:");
	      lastRow.createCell(3).setCellValue("分管领导：");
	      lastRow.createCell(7).setCellValue("财务总监：");
	      lastRow.createCell(10).setCellValue("财务部经理：");
	      lastRow.createCell(13).setCellValue("营业部经理：");
	      lastRow.createCell(16).setCellValue("制表人：");
	      //设置内容行的边框
	      for(int index=3;index<sheet.getLastRowNum();index++) {
	    	  
	    	  for(int c=0;c<=19;c++) {
	    		  
	    		  if (sheet.getRow(index)!=null) {
	    			  
	    			  if (sheet.getRow(index).getCell(c)!=null) {
	    				  sheet.getRow(index).getCell(c).setCellStyle(style3);
					}else {
						sheet.getRow(index).createCell(c).setCellStyle(style3);
					}
					
				}
	    		  
	    	  }
	      }
	      
	     
	      sheet.autoSizeColumn(18); 
	      
	    /*    try {
	            OutputStream output=response.getOutputStream();
	            response.reset();
	            response.setContentType("application/x-xls");  
	            response.setCharacterEncoding("UTF-8");  
	            String FileName = new String((salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表").getBytes("UTF-8"),"ISO-8859-1");
	            response.setHeader("Content-Disposition", "attachment;filename="+FileName+".xlsx");
	            wb.write(output);  
	            output.close(); 
	        } catch (IOException e) {
	        	logger.error("导出工资明细表失败",e);
	        	System.out.println("导出工资明细表失败");
	        	throw new BohaiException("", "导出工资明细表失败");
	        }*/
	      return wb;
	}
	
	
	
	 
	 //设置边框
	private XSSFCellStyle setStyle(XSSFCellStyle style) {
		  style.setAlignment(HorizontalAlignment.CENTER);
	   
	      style.setBorderBottom(BorderStyle.THIN);
	      style.setBorderTop(BorderStyle.THIN);
	      style.setBorderBottom(BorderStyle.THIN);
	      style.setBorderLeft(BorderStyle.THIN);
	      style.setBorderRight(BorderStyle.THIN);
	      
	      style.setTopBorderColor(new XSSFColor(Color.black));
	      style.setBottomBorderColor(new XSSFColor(Color.black));
	      style.setLeftBorderColor(new XSSFColor(Color.black));
	      style.setRightBorderColor(new XSSFColor(Color.black));
	      
	      return style;
	}
	
	
	//设置合并区域的单元格边框
	private  void setRegionBorder(int border, CellRangeAddress region, Sheet sheet){  
        RegionUtil.setBorderBottom(border,region, sheet);  
        RegionUtil.setBorderLeft(border,region, sheet);  
        RegionUtil.setBorderRight(border,region, sheet);  
        RegionUtil.setBorderTop(border,region, sheet);  
        
        RegionUtil.setBottomBorderColor(000, region, sheet);
        RegionUtil.setLeftBorderColor(000, region, sheet);
        RegionUtil.setRightBorderColor(000, region, sheet);
        RegionUtil.setTopBorderColor(000, region, sheet);
        
    }  

}
