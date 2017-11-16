package com.bohai.employeeSalary.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StaffInfo {

    private String staffNumber;

    
    private Date probationDateStart;

    private Date formalDateStart;

    private Date leaveDate;

    private String name;

    private String idNumber;

    private String positionSalary;

    private String workYears;

    private String email;

    private String remark;

    private String departmentId;

    
    private String departmentName;

    private String isLeave;

    private Date createTime;

    private Date updateTime;

    private String isProbation;

    
    private String coefficeient;
    
    private String skillSalary;
    
    private String submitStatus;
    
    private String probationSalary;
    
    

    public String getProbationSalary() {
		return probationSalary;
	}

	public void setProbationSalary(String probationSalary) {
		this.probationSalary = probationSalary;
	}

	public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber == null ? null : staffNumber.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date getProbationDateStart() {
        return probationDateStart;
    }

    public void setProbationDateStart(Date probationDateStart) {
        this.probationDateStart = probationDateStart;
    }

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date getFormalDateStart() {
        return formalDateStart;
    }

    public void setFormalDateStart(Date formalDateStart) {
        this.formalDateStart = formalDateStart;
    }

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getPositionSalary() {
        return positionSalary;
    }

    public void setPositionSalary(String positionSalary) {
        this.positionSalary = positionSalary == null ? null : positionSalary.trim();
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears == null ? null : workYears.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    
    public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

    public String getIsLeave() {
        return isLeave;
    }

    public void setIsLeave(String isLeave) {
        this.isLeave = isLeave == null ? null : isLeave.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsProbation() {
        return isProbation;
    }

    public void setIsProbation(String isProbation) {
        this.isProbation = isProbation == null ? null : isProbation.trim();
    }

	public String getCoefficeient() {
		return coefficeient;
	}

	public void setCoefficeient(String coefficeient) {
		this.coefficeient = coefficeient;
	}

	public String getSkillSalary() {
		return skillSalary;
	}

	public void setSkillSalary(String skillSalary) {
		this.skillSalary = skillSalary;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}

	
    
    
}