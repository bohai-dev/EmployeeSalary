package com.bohai.employeeSalary.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.bohai.employeeSalary.dao.DepartmentMapper;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.Department;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.impl.SalaryUploadServiceImpl;
import com.bohai.employeeSalary.util.CommonUtils;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;
import com.bohai.employeeSalary.vo.TreeView;



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
	DepartmentMapper departmentMapper;
	@Autowired
	StaffInfoMapper staffInfoMapper;
	
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
			double salary=calculateSalary(salaryList.get(i));
			salaryList.get(i).setActualSalary(salary+"");  //设置实发工资
			i+=staffSalaryMapper.updateByStaffNumAndDate(salaryList.get(i));
			
		}
		return count;
	}
	
	/*计算某员工的工资*/
	public double calculateSalary(StaffSalary salary) {
		
		//计算 应发工资=岗位工资+绩效工资+司龄工资+技能工资
        /*BigDecimal shouldSalary= new BigDecimal(salary.getPositionSalary())
				         .add(new BigDecimal(salary.getAchiementSalary()))
				         .add(new BigDecimal(salary.getPositionSalary()))
						 .add(new BigDecimal(salary.getSkillSalary()));*/
		double shouldSalary=Double.valueOf(Optional.ofNullable(salary.getPositionSalary()).orElse("0"))+Double.valueOf(Optional.ofNullable(salary.getAchiementSalary()).orElse("0"))
		                    +Double.valueOf(Optional.ofNullable(salary.getWorkYears()).orElse("0"))+Double.valueOf(Optional.ofNullable(salary.getSkillSalary()).orElse("0"));
		                         
		
		/*double taxBase=shouldSalary-Double.valueOf(salary.getPersonalTotal())-Double.valueOf(salary.getHousePersonalTotal())
				       +Double.valueOf(salary.getWarmSubsidy())-3500;*/
		double taxBase=shouldSalary-Optional.ofNullable(salary.getPersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0);
		
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
		
		//计算 实发工资=应发工资+取暖补贴-个人所得税-其他扣款
		
		double actualSalary=shouldSalary+Double.valueOf(salary.getWarmSubsidy())-tax-Double.valueOf(salary.getSalaryOther());
		
		return actualSalary;
	}
	
//	public List<TreeView<Map<String,List<StaffInfo>>>> queryDepStaffInfos(String depName,Long parentDepNumber) {
//		
//		List<TreeView<Map<String,List<StaffInfo>>>> treeList=null;
//		
//		//所有部门
//		List<Department> depList=this.departmentMapper.queryDepartments();
//		
//		for(Department dep:depList){
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put(dep.getDepName(), dep);
//			List<StaffInfo> infoList=this.staffInfoMapper.selectByDepartment(dep.getDepNumber());
//			for(StaffInfo info:infoList){
//				
//			}
//			}
////			Map<String,List<StaffInfo>> map=new HashMap<String, List<StaffInfo>>();
////			map.put(dep.getDepName(), infoList);
//			
//			
//		}
//		
//		
//		
//		return null;
//	}

}
