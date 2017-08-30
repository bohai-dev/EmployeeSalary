package com.bohai.employeeSalary.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.FileUploadService;
import com.bohai.employeeSalary.service.StaffInfoService;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.util.CommonUtils;

/**
 * 
 * @author cxy
 *上传营销人员提成表
 */
@Service("commissionUploadService")
public class CommissionUploadServiceImpl implements FileUploadService{
    
    
    static Logger logger = Logger.getLogger(CommissionUploadServiceImpl.class);
    
    @Autowired
    private StaffSalaryService salaryService;
    
    @Autowired
    private StaffInfoService staffInfoService;
    
    @Override
    public String upload(MultipartFile file, Object... objects) throws BohaiException {
        XSSFWorkbook wb = null;
        String message="success";
        
        try {
            wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet commisonSheet = wb.getSheetAt(0);
            
            //营销人员编号所在列
            int marketerNoIndex = 0;
            //营销人员名称所在列
            int marketerNameIndex = 0;
            //营销人员所得所在列
            int marketerProfitIndex = 0;
            
            //获取表头中的列序号
            XSSFRow row = commisonSheet.getRow(2);
            for(int i = row.getFirstCellNum(); i < row.getLastCellNum() ; i++){
                String colName = row.getCell(i).getStringCellValue();
                
                if(StringUtils.isEmpty(colName)){
                    continue;
                }
                
                if(colName.equals("营销人员编号")){
                    marketerNoIndex = i;
                }else if (colName.equals("营销人员姓名")) {
                    marketerNameIndex = i;
                }else if (colName.equals("营销人员所得")){
                    marketerProfitIndex = i;
                }
            }
            
            //获取标题中的日期
            String str=commisonSheet.getRow(0).getCell(0).getStringCellValue();
            String time=CommonUtils.filterNumber(str);
            logger.debug("绩效工资月份："+time);
            time = time.substring(0, 4) + "-" + time.substring(4,6);
            logger.debug("绩效工资月份："+time);
            
            for(int i=2; i<commisonSheet.getLastRowNum(); i++) {
                
                if (commisonSheet.getRow(i) != null) {
                    
                    //设置单元格类型
                    commisonSheet.getRow(i).getCell(marketerNameIndex).setCellType(CellType.STRING);
                    commisonSheet.getRow(i).getCell(marketerProfitIndex).setCellType(CellType.STRING);
                    commisonSheet.getRow(i).getCell(marketerNoIndex).setCellType(CellType.STRING);
                    
                    if (StringUtils.isNotEmpty(commisonSheet.getRow(i).getCell(marketerNameIndex).getStringCellValue())) {
                        //获取员工姓名
                        StaffSalary staffSalary=new StaffSalary();
                        staffSalary.setPayDate(time);
                        String marketerName = commisonSheet.getRow(i).getCell(marketerNameIndex).getStringCellValue();
                        staffSalary.setAchiementSalary(commisonSheet.getRow(i).getCell(marketerProfitIndex).getStringCellValue());
                        staffSalary.setUpdateTime(CommonUtils.getTime());
                        
                        List<StaffInfo> list = this.staffInfoService.queryStaffInfoByName(marketerName);
                        if(list == null || list.size() <1){
                            logger.warn("无此员工信息：" + marketerName);
                            message += "无此员工信息：" + marketerName;
                        }else if (list.size() >1) {
                            logger.warn("出现重名的员工：" + marketerName);
                            message += "出现重名的员工：" + marketerName;
                        }else {
                            StaffInfo staffInfo = list.get(0);
                            staffSalary.setStaffNumber(staffInfo.getStaffNumber());
                            this.salaryService.saveOrUpdate(staffSalary);
                        }
                    }
                    
                }
            }
            
        } catch (IOException e) {
            logger.error("导入营销人员绩效工资失败",e);
            throw new BohaiException("", "导入营销人员绩效工资失败");
        } finally {
            
            try {
                wb.close();
            } catch (IOException e) {
                logger.error("关闭XSSFWorkbook失败",e);
            }
        }
        
        return message;
    }

}
