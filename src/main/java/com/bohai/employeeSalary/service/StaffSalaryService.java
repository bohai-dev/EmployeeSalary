package com.bohai.employeeSalary.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bohai.employeeSalary.dao.SalaryDateMapper;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.impl.SalaryUploadServiceImpl;
import com.bohai.employeeSalary.util.CommonUtils;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;



/**
 * 员工工资管理service
 * @author cxy
 *
 */
@Service("salaryService")
public class StaffSalaryService {

	static Logger logger = Logger.getLogger(StaffSalaryService.class);
	
	@Autowired
	StaffSalaryMapper   staffSalaryMapper;
	
	@Autowired
	SalaryDateMapper   dateMapper;
	
	@Autowired
	StaffInfoMapper  staffInfoMapper;
	
	public String saveOrUpdate(StaffSalary staffSalary) {
	 /*String staffNum=staffSalary.getStaffNumber();
	 String payDate=staffSalary.getPayDate();*/
	 String message="";
	 Long count=staffSalaryMapper.selectByStaffNumAndDate(staffSalary);
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
    public int updateSalary(QueryStaffSalaryParamVO paramVo) {
     
        int count=0;
        /*查询该部门该月份所有员工的信息*/
        List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
        
        if (salaryList==null||salaryList.size()<=0) {     //工资表中还没有该月份信息，要先生成
        	QueryStaffInfoParamVO vo=new QueryStaffInfoParamVO();
        	vo.setDepartmentId(paramVo.getDepNum());
        	vo.setLeaveDate(paramVo.getPayDate());
        	List<String>  staffnums=staffInfoMapper.selectByDepartmentId(vo);    //查询[x部门]所有的员工编号
        	for(int i=0;i<staffnums.size();i++) {
        		StaffSalary staffSalary=new StaffSalary();
        		staffSalary.setStaffNumber(staffnums.get(i));
        		staffSalary.setPayDate(paramVo.getPayDate());
        		staffSalary.setStaffDepartmentId(paramVo.getDepNum());
        		saveOrUpdate(staffSalary);
        	}
		}
        
        List<StaffSalary> list=staffSalaryMapper.queryStaffSalaryByParams(paramVo);
        for(int i=0;i<list.size();i++) {
        	StaffSalary salary=calculateSalary(list.get(i));   //计算工资
           // salaryList.get(i).setActualSalary(salary+"");  //设置实发工资
            count+=staffSalaryMapper.updateByStaffNumAndDate(salary);
            
        }
        return count;
    }
    
    /*计算某员工的工资*/
    public StaffSalary calculateSalary(StaffSalary salary) {
        
      //  BigDecimal posSalary= new BigDecimal(Optional.ofNullable(salary.getPositionSalary()).orElse("0.00"))
      //  		              .multiply(new BigDecimal(Optional.ofNullable(salary.getCoefficeient()).orElse("0.00"))); //岗位工资*系数
       
    	BigDecimal posSalary=new BigDecimal("0.00");
    	
        BigDecimal actualSalary=new BigDecimal("0.00");    //实发工资
        String isLeave=salary.getIsLeave();   //是否离职
        if("1".equals(isLeave)) {   //离职员工
            Date leaveDate=salary.getLeaveDate();    //获取到离职日期
            String isProbation=salary.getIsProbation();  //是否为试用期员工            
            if("0".equals(isProbation)){  //正式员工离职
                
                Date  formalDate=salary.getFormalDateStart();  //正式工作的起始日期                
                
                if(salary.getPayDate().equals(CommonUtils.getYearMonth(formalDate))) {  //试用期员工转正当月就离职
                     String tempDate=CommonUtils.getYearMonthDay(formalDate);   //正式工作的起始日期
                     String formalDay=tempDate.split("-")[2];  //正式工作的day                    
                     String leaveDay=CommonUtils.getYearMonthDay(leaveDate).split("-")[2];  //离职日期的day
                     String startDay="01";
                     if (CommonUtils.getYearMonth(formalDate).equals(CommonUtils.getYearMonth(salary.getProbationDateStart()))) { //试用期的年月和转正的年月相等  也就是说入职当月转正
                         startDay=CommonUtils.getYearMonthDay(salary.getProbationDateStart()).split("-")[2];
                    }
                     //试用期的天数
                     int proDays=dateMapper.countWorkDays(tempDate.split("-")[0], tempDate.split("-")[1], startDay,formalDay);
                     //正式工作的天数
                     int formalDays=dateMapper.countWorkDays(tempDate.split("-")[0], tempDate.split("-")[1], formalDay,leaveDay);
                     //posSalary=试用期工资*proDays/21.75+正式工资*formalDays/21.75;
                     BigDecimal probationSalary=new BigDecimal(Optional.ofNullable(salary.getProbationSalary()).orElse("0.00"));
                     BigDecimal formalSalary=new BigDecimal(Optional.ofNullable(salary.getPositionSalary()).orElse("0.00"));
                     posSalary=(probationSalary.multiply(new BigDecimal(proDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP))
                    		 .add(formalSalary.multiply(new BigDecimal(formalDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP));
                    
                    
                }else {   //普通正式员工离职
                     String[] dateArray=CommonUtils.getYearMonthDay(leaveDate).split("-");                    
                     int workDays=dateMapper.countWorkDays(dateArray[0], dateArray[1], "01", dateArray[2]);
                     //posSalary=posSalary*workDays/21.75;
                     BigDecimal formalSalary=new BigDecimal(Optional.ofNullable(salary.getPositionSalary()).orElse("0.00"));
                     posSalary=formalSalary.multiply(new BigDecimal(workDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP);
                }
                
            }
            else {   //试用期员工员工离职
                String startDay="01";
                String proDate=CommonUtils.getYearMonthDay(salary.getProbationDateStart());  //试用期起始日期
                String leaDate=CommonUtils.getYearMonthDay(leaveDate);
                if(salary.getPayDate().equals(proDate.substring(0,7))){ //试用期首月就离职
                    // String date=CommonUtils.getYearMonthDay(leaveDate);
                     startDay=proDate.split("-")[2];
                     //int workDays=dateMapper.countWorkDays(leaveDate.getYear()+1900+"", leaveDate.getMonth()+1+"", startDay+"", leaveDate.getDate()+"");
                }else {  //普通试用期离职(非首月离职)
                    //startDay="01";
                }
                
                 int workDays=dateMapper.countWorkDays(leaDate.split("-")[0], leaDate.split("-")[1], startDay, leaDate.split("-")[2]);
                // posSalary=试用期工资*workDays/21.75;
                BigDecimal proSalary=new BigDecimal(Optional.ofNullable(salary.getProbationSalary()).orElse("0.00"));
                posSalary=proSalary.multiply(new BigDecimal(workDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP);
                
            }
            
        
        }//end 离职员工
        
        
        if("0".equals(isLeave)) {  //在职员工
            String isProbation=salary.getIsProbation();
            if("0".equals(isProbation)) {  //正式员工在职
                String formalDate=CommonUtils.getYearMonthDay(salary.getFormalDateStart()); //正式工作起始日期
                
                if(salary.getPayDate().equals(formalDate.substring(0,7))) {  //试用期员工当月转正
                    String startDay="01";
                    String year=formalDate.split("-")[0];
                    String month=formalDate.split("-")[1];
                    String day=formalDate.split("-")[2];  //转正日期的 天(前些天是试用期，后几天是正式期)
                    if (formalDate.substring(0,7).equals(CommonUtils.getYearMonth(salary.getProbationDateStart()))) { //试用期的年月和转正的年月相等  也就是说入职当月转正
                         startDay=CommonUtils.getYearMonthDay(salary.getProbationDateStart()).split("-")[2];
                    }
                    
                    String pDay=Integer.parseInt(day)-1+"";
                    if(pDay.length()<=1) {
                    	pDay="0"+pDay;
                    }
                    //当月试用期天数
                    int probationDays=dateMapper.countWorkDays(year, month, startDay, pDay);
                    //当月正式工作天数
                   // String fDays=Integer.parseInt(day)+1+"";
                    
                   
                    int formalDays=dateMapper.countWorkDays(year, month, day,"31");
                    
                  //  posSalary=试用期工资*probationDays/21.75+正式工资*formalDays/21.75;
                    BigDecimal probationSalary=new BigDecimal(Optional.ofNullable(salary.getProbationSalary()).orElse("0.00"));
                    BigDecimal formalSalary=new BigDecimal(Optional.ofNullable(salary.getPositionSalary()).orElse("0.00"));
                    posSalary=probationSalary.multiply(new BigDecimal(probationDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP)
                    		  .add(formalSalary.multiply(new BigDecimal(formalDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP));
                    
                    
                }else {      //普遍情况: 在职正式员工 
                	posSalary=new BigDecimal(Optional.ofNullable(salary.getPositionSalary()).orElse("0.00"));
                	
                }
                
            }
            if ("1".equals(isProbation)) {   //试用期员工在职
                
                String tempDate=CommonUtils.getYearMonthDay(salary.getProbationDateStart()); //试用期起始日期
                String startDay=tempDate.split("-")[2];  //1-31  起始工作的天
                if(salary.getPayDate().equals(tempDate.substring(0,7))&&!("01".equals(startDay))){ //试用期员工入职当月                
                    String year=tempDate.split("-")[0]; //获取试用期起始日期的年份
                    String month=tempDate.split("-")[1];  //获取试用期起始日期的月份
                   
                    int countWorkDays=dateMapper.countWorkDays(year, month, startDay,"31");  //试用当月的工作天数                    
                  //  posSalary=试用期工资*countWorkDays/21.75;
                    BigDecimal probationSalary=new BigDecimal(Optional.ofNullable(salary.getProbationSalary()).orElse("0.00"));
                    posSalary=probationSalary.multiply(new BigDecimal(countWorkDays)).divide(new BigDecimal("21.75"),2,RoundingMode.HALF_UP);
                
                    
                }else {  //非首月试用期   试用期员工正常情况 
                    
                	posSalary=new BigDecimal(Optional.ofNullable(salary.getProbationSalary()).orElse("0.00"));
                }
                
            }
            
            
        }//end 在职员工        
        
        
        salary=computeSalary(salary, posSalary);
        

        
        return salary;
    }
    
    /**
     * 
     * @param salary        
     * @param postionSalary 岗位工资
     * @return  实发工资
     */
    public StaffSalary  computeSalary(StaffSalary salary,BigDecimal modPosSalary) {
    	String sheBao="0.00";
    	String house="0.00";
    	QueryStaffSalaryParamVO vo=new QueryStaffSalaryParamVO();
    	vo.setStaffNum(salary.getStaffNumber());
    	List<StaffSalary> salaryList=staffSalaryMapper.queryStaffSalaryByParams(vo);   //查询最近月份的工资信息
    	for(StaffSalary staffSalary : salaryList) {
    		 if (staffSalary.getPersonalTotal()!=null) {
				//社保
    			   sheBao=staffSalary.getPersonalTotal();  
    			//公积金
    			   if(!StringUtils.isEmpty(staffSalary.getHousePersonalTotal())) {
    				   house=staffSalary.getHousePersonalTotal();
    			   }
    			   
    			   salary.setPayBase(staffSalary.getPayBase());
    			   salary.setPensionPersonal(staffSalary.getPensionPersonal());
    			   salary.setMedicalPersonal(staffSalary.getMedicalPersonal());
    			   salary.setUnemploymentPersonal(staffSalary.getUnemploymentPersonal());
    			   salary.setPensionPersonalPercent(staffSalary.getPensionPersonalPercent());
    			   salary.setMedicalPersonalPercent(staffSalary.getMedicalPersonalPercent());
    			   salary.setUnemploymentPersonalPercent(staffSalary.getUnemploymentPersonalPercent());
    			   salary.setPersonalReserve1(staffSalary.getPersonalReserve1());
    			   salary.setPersonalReserve2(staffSalary.getPersonalReserve2());
    			   salary.setPensionCompany(staffSalary.getPensionCompany());
    			   salary.setPensionCompanyPercent(staffSalary.getPensionPersonalPercent());
    			   salary.setMedicalCompanyPercent(staffSalary.getMedicalCompanyPercent());
    			   salary.setMedicalCompany(staffSalary.getMedicalCompany());
    			   salary.setUnemploymentCompanyPercent(staffSalary.getUnemploymentCompanyPercent());
    			   salary.setUnemploymentCompany(staffSalary.getUnemploymentCompany());
    			   salary.setInjuryCompanyPercent(staffSalary.getInjuryCompanyPercent());
    			   salary.setInjuryCompany(staffSalary.getInjuryCompany());
    			   salary.setBirthCompayPercent(staffSalary.getBirthCompayPercent());
    			   salary.setBirthCompany(staffSalary.getBirthCompany());
    			   salary.setCompanyReserve1(staffSalary.getCompanyReserve1());
    			   salary.setCompanyReserve2(staffSalary.getCompanyReserve2());
    			   salary.setPersonalTotal(staffSalary.getPersonalTotal());
    			   salary.setCompanyTotal(staffSalary.getCompanyTotal());
    			   salary.setPaymentTotal(staffSalary.getPaymentTotal());
    			   
    			   salary.setHouseBasePersonalPercent(staffSalary.getHouseBasePersonalPercent());
    			   salary.setHouseBasePersonal(staffSalary.getHouseBasePersonal());
    			   salary.setHouseSupplyPersonalPercent(staffSalary.getHouseSupplyPersonalPercent());
    			   salary.setHouseSupplyPersonal(staffSalary.getHouseSupplyPersonal());
    			   salary.setHouseBaseCompany(staffSalary.getHouseBaseCompany());
    			   salary.setHouseSupplyCompany(staffSalary.getHouseSupplyCompany());
    			   salary.setHouseBaseCompanyPercent(staffSalary.getHouseBaseCompanyPercent());
    			   salary.setHouseSupplyCompanyPercent(staffSalary.getHouseSupplyCompanyPercent());
    			   salary.setHousePersonalTotal(staffSalary.getHousePersonalTotal());
    			   salary.setHouseCompanyTotal(staffSalary.getHouseCompanyTotal());
    			   salary.setHouseToatal(staffSalary.getHouseToatal());
    			   salary.setStaffDepartmentId(staffSalary.getStaffDepartmentId());
    			   
    			   break;    //getPersonalTotal不为null，就跳出循环
			}
    		
    		
    	}
        //计算 应发工资=岗位工资*系数+绩效工资+司龄工资+技能工资
        
        BigDecimal shouldSalary= modPosSalary                           
                                 .add(new BigDecimal(Optional.ofNullable(salary.getAchiementSalary()).orElse("0.00")))
                                 .add(new BigDecimal(Optional.ofNullable(salary.getWorkYears()).orElse("0.00")))
                                 .add(new BigDecimal(Optional.ofNullable(salary.getSkillSalary()).orElse("0.00")));
                                 
        
        shouldSalary=CommonUtils.getRound(shouldSalary);
        System.out.println("应发工资："+shouldSalary);

        //计算计税基数=应发工资-社保公积金代扣合计+取暖补贴-3500

		/*double taxBase=shouldSalary-Optional.ofNullable(salary.getPersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0)
				       -Optional.ofNullable(salary.getHousePersonalTotal()).map(i->Double.valueOf(i)).orElse((double) 0)
				       +Optional.ofNullable(salary.getWarmSubsidy()).map(i->Double.valueOf(i)).orElse((double)0)-3500;*/
        // double taxBase=shouldSalary-Double.valueOf(sheBao)-Double.valueOf(house)
		//	       +Optional.ofNullable(salary.getWarmSubsidy()).map(i->Double.valueOf(i)).orElse((double)0)-3500;
        BigDecimal taxBase=shouldSalary.subtract(new BigDecimal(sheBao)).subtract(new BigDecimal(house)).add(Optional.ofNullable(salary.getWarmSubsidy()).map(i->new BigDecimal(i)).orElse(new BigDecimal("0.00")))
        		            .subtract(new BigDecimal(3500));
        taxBase=CommonUtils.getRound(taxBase);
		System.out.println("计税基数："+taxBase);
		
		//计算个人所得税   ROUND(MAX(计税基数*5%*{0.6,2,4,5,6,7,9}-5*{0,21,111,201,551,1101,2701},0),2)		 
		ArrayList<BigDecimal> tempList=new ArrayList<BigDecimal>();
	
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*0.6-5*0));
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*2-5*21));
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*4-5*111));
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*5-5*201));
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*6-5*551));
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*7-5*1101));
		tempList.add(new BigDecimal(taxBase.doubleValue()*0.05*9-5*2701));
		tempList.add(new BigDecimal("0.00"));
		
		BigDecimal tax=CommonUtils.getRound(CommonUtils.getMax(tempList));  //个人所得税
		
		//计算 实发工资=应发工资+取暖补贴-社保公积金代扣合计-个人所得税+其他款项		
		
		/*double actualSalary=shouldSalary+Optional.ofNullable(salary.getWarmSubsidy()).map(i->Double.valueOf(i)).orElse((double)0)
				            -Double.valueOf(sheBao)
			                -Double.valueOf(house)
			                -tax
				            +Optional.ofNullable(salary.getSalaryOther()).map(i->Double.valueOf(i)).orElse((double)0);*/
		BigDecimal actualSalary=shouldSalary.add(Optional.ofNullable(salary.getWarmSubsidy()).map(i->new BigDecimal(i)).orElse(new BigDecimal("0.00")))
				               .subtract(new BigDecimal(sheBao)).subtract(new BigDecimal(house)).subtract(tax)
				               .add(Optional.ofNullable(salary.getSalaryOther()).map(i->new BigDecimal(i)).orElse(new BigDecimal("0.00")));
				               
		salary.setActualSalary(CommonUtils.getRound(actualSalary)+"");  //设置实发工资
		 
		
		salary.setGrossSalary(shouldSalary.toString());  //设置应发工资
		salary.setIncomeTax(tax.toString());  //设置个人所得税
		salary.setTaxBase(taxBase.toString());  //设置缴费基数
		salary.setPostionsSalary(Optional.ofNullable(salary.getPositionSalary()).map(v->CommonUtils.getRound(new BigDecimal(v)).toString()).orElse("0.00"));    //设置岗位工资
		salary.setSkillsSalary(Optional.ofNullable(salary.getSkillSalary()).map(v->CommonUtils.getRound(new BigDecimal(v)).toString()).orElse("0.00"));     //设置技能工资
		salary.setYearsSalary(Optional.ofNullable(salary.getWorkYears()).map(v->CommonUtils.getRound(new BigDecimal(v)).toString()).orElse("0.00"));      //设置司龄工资
		return  salary;
	}	
	

}
