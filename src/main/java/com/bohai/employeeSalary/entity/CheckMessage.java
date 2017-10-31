package com.bohai.employeeSalary.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CheckMessage {

    private String id;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date probationDateStart;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date formalDateStart;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date leaveDate;

    private String name;

    private String idNumber;

    private String staffNumber;

    private String positionSalary;

    private String workYears;

    private String email;

    private String remark;

    private String departmentId;

    private String departmentName;
    
    private String isLeave;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date updateTime;

    private String isProbation;

    private String coefficeient;

    private String skillSalary;

    private String tage;

    private String submitter;
    
    private String checker;
   
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date submitTime;
   
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date checkTime;
    
    private String submitType;
    
    private String approvalOpinion;
    
  
    
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getProbationDateStart() {
        return probationDateStart;
    }

    public void setProbationDateStart(Date probationDateStart) {
        this.probationDateStart = probationDateStart;
    }

    public Date getFormalDateStart() {
        return formalDateStart;
    }

    public void setFormalDateStart(Date formalDateStart) {
        this.formalDateStart = formalDateStart;
    }

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

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber == null ? null : staffNumber.trim();
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

    public String getIsLeave() {
        return isLeave;
    }

    public void setIsLeave(String isLeave) {
        this.isLeave = isLeave == null ? null : isLeave.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
        this.coefficeient = coefficeient == null ? null : coefficeient.trim();
    }

    public String getSkillSalary() {
        return skillSalary;
    }

    public void setSkillSalary(String skillSalary) {
        this.skillSalary = skillSalary == null ? null : skillSalary.trim();
    }

    public String getTage() {
        return tage;
    }

    public void setTage(String tage) {
        this.tage = tage == null ? null : tage.trim();
    }

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String string) {
		this.submitType = string;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}