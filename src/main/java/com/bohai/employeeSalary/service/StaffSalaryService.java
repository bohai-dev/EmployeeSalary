package com.bohai.employeeSalary.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.employeeSalary.dao.SalaryDateMapper;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.impl.SalaryUploadServiceImpl;
import com.bohai.employeeSalary.util.CommonUtils;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;



/**
 * 员工工资管理service
 * @author cxy
 *
 */
@Service("salaryService")
public class StaffSalaryService {
	static Logger logger = Logger.getLogger(SalaryUploadServiceImpl.class);
	
	@Autowired
	StaffSalaryMapper   staffSalaryMapper;
	
	@Autowired
	SalaryDateMapper   dateMapper;
	
	public String saveOrUpdate(StaffSalary staffSalary) {
	 String staffNum=staffSalary.getStaffNumber();
	 String payDate=staffSalary.getPayDate();
	 String message="";
	 Long count=staffSalaryMapper.selectByStaffNumAndDate(staffNum, payDate);
	 if(count<=0) {   //不存在 
		 int num=staffSalaryMapper.insertSelective(staffSalary);
		 message="已成功插入"+num+"条记录";
	 }else {   //已存在更新
		 int num=staffSalaryMapper.updateByStaffNumAndDate(staffSalary);
		 message="已成功更新"+num+"条记录";
	 }
	 
	 return message;
		
		
	}
	/**
	 * 更新某部门、某月份的工资
	 * @param staffSalary
	 * @return
	 */
	public int updateSalary(String depNum,String month) {
		QueryStaffSalaryParamVO paramVo=new QueryStaffSalaryParamVO();
		paramVo.setDepNum(depNum);
		paramVo.setPayDate(month);
		int count=0;
		/*查询该部门该月份所有员工的信息*/
		List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
		for(int i=0;i<salaryList.size();i++) {
			double salary=calculateSalary(salaryList.get(i));   //计算工资
			salaryList.get(i).setActualSalary(salary+"");  //设置实发工资
			i+=staffSalaryMapper.updateByStaffNumAndDate(salaryList.get(i));
			
		}
		return count;
	}
	
	/*计算某员工的工资*/
	public double calculateSalary(StaffSalary salary) {
		
		String isLeave=salary.getIsLeave();
		if("1".equals(isLeave)) {   //离职员工
			String isProbation=salary.getIsProbation();
			if ("1".equals(isProbation)) {   //试用期员工
				
				
			}
			if("0".equals(isProbation)){  //正式员工
				
				if() {
					
				}
				
			}
		}
		
		
		if("0".equals(isLeave)) {  //在职员工
			
		}
		
		
		
		
		
		
		//判断是否为试用期员工
		if("1".equals(salary.getIsProbation())){
			
			int year=salary.getProbationDateStart().getYear()+1900; //获取年份
			int month=salary.getProbationDateStart().getMonth()+1;  //获取试用期起始日期的月份
			int payMonth=Integer.valueOf(salary.getPayDate().split("-")[1]);
			if(month==payMonth) {  //如果是试用期当月
				int startDay=salary.getProbationDateStart().getDate();  //1-31  起始工作的天
				int countDay=dateMapper.countWorkDays(year+"", month+"", startDay+"");
				
			}
		}
		
		
		
		
		
		
		
		
		
		
		//计算 应发工资=岗位工资*系数+绩效工资+司龄工资+技能工资
        /*BigDecimal shouldSalary= new BigDecimal(salary.getPositionSalary())
				         .add(new BigDecimal(salary.getAchiementSalary()))
				         .add(new BigDecimal(salary.getPositionSalary()))
						 .add(new BigDecimal(salary.getSkillSalary()));*/
		double shouldSalary=Double.valueOf(Optional.ofNullable(salary.getPositionSalary()).orElse("0"))*Double.valueOf(Optional.ofNullable(salary.getCoefficeient()).orElse("0"))
				            +Double.valueOf(Optional.ofNullable(salary.getAchiementSalary()).orElse("0"))
		                    +Double.valueOf(Optional.ofNullable(salary.getWorkYears()).orElse("0"))+Double.valueOf(Optional.ofNullable(salary.getSkillSalary()).orElse("0"));
		                         
		System.out.println("应发工资："+shouldSalary);

        //计算计税基数=应发工资-社保公积金代扣合计+取暖补贴-3500
		double taxBase=shouldSalary-Optional.ofNullable(salary.getPersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0)
				       -Optional.ofNullable(salary.getHousePersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0)
				       +Optional.ofNullable(salary.getWarmSubsidy()).map(i->Double.valueOf(i)).orElse((double)0)-3500;
		System.out.println("计税基数："+taxBase);
		//计算个人所得税   ROUND(MAX(计税基数*5%*{0.6,2,4,5,6,7,9}-5*{0,21,111,201,551,1101,2701},0),2)		 
		ArrayList<Double> tempList=new ArrayList<Double>();
		tempList.add(taxBase*0.05*0.6-5*0);
		tempList.add(taxBase*0.05*2-5*21);
		tempList.add(taxBase*0.05*4-5*111);
		tempList.add(taxBase*0.05*5-5*201);
		tempList.add(taxBase*0.05*6-5*551);
		tempList.add(taxBase*0.05*7-5*1101);
		tempList.add(taxBase*0.05*9-5*2701);
		tempList.add((double)0);
		
		double tax=CommonUtils.getRound(CommonUtils.getMax(tempList));  //个人所得税
		
		//计算 实发工资=应发工资+取暖补贴-社保公积金代扣合计-个人所得税-其他扣款		
		
		double actualSalary=shouldSalary+Optional.ofNullable(salary.getWarmSubsidy()).map(i->Double.valueOf(i)).orElse((double)0)
				            -Optional.ofNullable(salary.getPersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0)
			                -Optional.ofNullable(salary.getHousePersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0)-tax
				            -Optional.ofNullable(salary.getSalaryOther()).map(i->Double.valueOf(i)).orElse((double)0);
		
		return actualSalary;
	}

}
