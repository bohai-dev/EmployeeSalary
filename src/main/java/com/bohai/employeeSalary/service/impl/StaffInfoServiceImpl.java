package com.bohai.employeeSalary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.service.StaffInfoService;
import com.bohai.employeeSalary.vo.PaginationParamVO;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;

@Service("staffInfoService")
public class StaffInfoServiceImpl implements StaffInfoService{

	@Autowired
	private StaffInfoMapper staffInfoMapper;
	
	@Override
	public List<StaffInfo> queryStaffInfoPagination(PaginationParamVO paramVO) {
		// TODO Auto-generated method stub
	 List<StaffInfo> staffInfoList=this.staffInfoMapper.queryStaffInfos(null);
		return staffInfoList;
	}

    @Override
    public List<StaffInfo> queryStaffInfoByName(String name) {
        
        QueryStaffInfoParamVO paramVO = new QueryStaffInfoParamVO();
        paramVO.setName(name);
        return this.staffInfoMapper.selectByCondition(paramVO);
        
    }

	
}
