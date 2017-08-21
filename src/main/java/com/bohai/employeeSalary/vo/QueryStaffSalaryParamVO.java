package com.bohai.employeeSalary.vo;

public class QueryStaffSalaryParamVO extends PaginationParamVO{
	
	
	private String staffNum;
	private String depNum;
	private String payDate;
	
	
	public String getStaffNum() {
		return staffNum;
	}
	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}
	public String getDepNum() {
		return depNum;
	}
	public void setDepNum(String depNum) {
		this.depNum = depNum;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
	

}
