package com.bohai.employeeSalary.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StaffSalary {

    private String staffNumber;

    private String staffDepartmentId;

    private String achiementSalary;

    private String payDate;

    private String salaryOther;

    private String otherRemark;

    private String createTime;

    private String updateTime;

    private String payBase;

    private String pensionPersonal;

    private String pensionCompany;

    private String medicalPersonal;

    private String medicalCompany;

    private String unemploymentPersonal;

    private String unemploymentCompany;

    private String injuryCompany;

    private String birthCompany;

    private String personalReserve1;

    private String personalReserve2;

    private String companyReserve1;

    private String companyReserve2;

    private String warmSubsidy;

    private String pensionPersonalPercent;

    private String pensionCompanyPercent;

    private String medicalPersonalPercent;

    private String medicalCompanyPercent;

    private String unemploymentPersonalPercent;

    private String unemploymentCompanyPercent;

    private String injuryCompanyPercent;

    private String birthCompayPercent;

    private String actualSalary;

    private String personalTotal;

    private String companyTotal;

    private String paymentTotal;

    private String houseBasePersonal;

    private String houseBaseCompany;

    private String houseSupplyPersonal;

    private String houseSupplyCompany;

    private String houseBasePersonalPercent;

    private String houseBaseCompanyPercent;

    private String houseSupplyPersonalPercent;

    private String houseSupplyCompanyPercent;

    private String housePersonalTotal;

    private String houseCompanyTotal;

    private String houseToatal;
    

    private Date probationDateStart;

    private Date formalDateStart;

    private Date leaveDate;

    private String name;

    private String idNumber;

    private String positionSalary;

    private String workYears;

    private String email; 
    
    private String depName;

    private String isLeave;

    private String isProbation;

    private String coefficeient;
   
    private String skillSalary;
    
    private String incomeTax;
    
    private String grossSalary;
    
    private String taxBase;
    
    private String postionsSalary;
    
    private String skillsSalary;
    
    private String yearsSalary;
    
    private String  probationSalary;
    
    
   
    public String getProbationSalary() {
		return probationSalary;
	}

	public void setProbationSalary(String probationSalary) {
		this.probationSalary = probationSalary;
	}

	public String getPostionsSalary() {
		return postionsSalary;
	}

	public void setPostionsSalary(String postionsSalary) {
		this.postionsSalary = postionsSalary;
	}

	public String getSkillsSalary() {
		return skillsSalary;
	}

	public void setSkillsSalary(String skillsSalary) {
		this.skillsSalary = skillsSalary;
	}

	public String getYearsSalary() {
		return yearsSalary;
	}

	public void setYearsSalary(String yearsSalary) {
		this.yearsSalary = yearsSalary;
	}

	@JsonFormat(pattern="yyyy-MM-dd ",timezone="GMT+8")
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
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPositionSalary() {
		return positionSalary;
	}

	public void setPositionSalary(String positionSalary) {
		this.positionSalary = positionSalary;
	}

	public String getWorkYears() {
		return workYears;
	}

	public void setWorkYears(String workYears) {
		this.workYears = workYears;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public String getIsProbation() {
		return isProbation;
	}

	public void setIsProbation(String isProbation) {
		this.isProbation = isProbation;
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

	public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber == null ? null : staffNumber.trim();
    }

    public String getStaffDepartmentId() {
        return staffDepartmentId;
    }

    public void setStaffDepartmentId(String staffDepartmentId) {
        this.staffDepartmentId = staffDepartmentId == null ? null : staffDepartmentId.trim();
    }

    public String getAchiementSalary() {
        return achiementSalary;
    }

    public void setAchiementSalary(String achiementSalary) {
        this.achiementSalary = achiementSalary == null ? null : achiementSalary.trim();
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate == null ? null : payDate.trim();
    }

    public String getSalaryOther() {
        return salaryOther;
    }

    public void setSalaryOther(String salaryOther) {
        this.salaryOther = salaryOther == null ? null : salaryOther.trim();
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark == null ? null : otherRemark.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getPayBase() {
        return payBase;
    }

    public void setPayBase(String payBase) {
        this.payBase = payBase == null ? null : payBase.trim();
    }

    public String getPensionPersonal() {
        return pensionPersonal;
    }

    public void setPensionPersonal(String pensionPersonal) {
        this.pensionPersonal = pensionPersonal == null ? null : pensionPersonal.trim();
    }

    public String getPensionCompany() {
        return pensionCompany;
    }

    public void setPensionCompany(String pensionCompany) {
        this.pensionCompany = pensionCompany == null ? null : pensionCompany.trim();
    }

    public String getMedicalPersonal() {
        return medicalPersonal;
    }

    public void setMedicalPersonal(String medicalPersonal) {
        this.medicalPersonal = medicalPersonal == null ? null : medicalPersonal.trim();
    }

    public String getMedicalCompany() {
        return medicalCompany;
    }

    public void setMedicalCompany(String medicalCompany) {
        this.medicalCompany = medicalCompany == null ? null : medicalCompany.trim();
    }

    public String getUnemploymentPersonal() {
        return unemploymentPersonal;
    }

    public void setUnemploymentPersonal(String unemploymentPersonal) {
        this.unemploymentPersonal = unemploymentPersonal == null ? null : unemploymentPersonal.trim();
    }

    public String getUnemploymentCompany() {
        return unemploymentCompany;
    }

    public void setUnemploymentCompany(String unemploymentCompany) {
        this.unemploymentCompany = unemploymentCompany == null ? null : unemploymentCompany.trim();
    }

    public String getInjuryCompany() {
        return injuryCompany;
    }

    public void setInjuryCompany(String injuryCompany) {
        this.injuryCompany = injuryCompany == null ? null : injuryCompany.trim();
    }

    public String getBirthCompany() {
        return birthCompany;
    }

    public void setBirthCompany(String birthCompany) {
        this.birthCompany = birthCompany == null ? null : birthCompany.trim();
    }

    public String getPersonalReserve1() {
        return personalReserve1;
    }

    public void setPersonalReserve1(String personalReserve1) {
        this.personalReserve1 = personalReserve1 == null ? null : personalReserve1.trim();
    }

    public String getPersonalReserve2() {
        return personalReserve2;
    }

    public void setPersonalReserve2(String personalReserve2) {
        this.personalReserve2 = personalReserve2 == null ? null : personalReserve2.trim();
    }

    public String getCompanyReserve1() {
        return companyReserve1;
    }

    public void setCompanyReserve1(String companyReserve1) {
        this.companyReserve1 = companyReserve1 == null ? null : companyReserve1.trim();
    }

    public String getCompanyReserve2() {
        return companyReserve2;
    }

    public void setCompanyReserve2(String companyReserve2) {
        this.companyReserve2 = companyReserve2 == null ? null : companyReserve2.trim();
    }

    public String getWarmSubsidy() {
        return warmSubsidy;
    }

    public void setWarmSubsidy(String warmSubsidy) {
        this.warmSubsidy = warmSubsidy == null ? null : warmSubsidy.trim();
    }

    public String getPensionPersonalPercent() {
        return pensionPersonalPercent;
    }

    public void setPensionPersonalPercent(String pensionPersonalPercent) {
        this.pensionPersonalPercent = pensionPersonalPercent == null ? null : pensionPersonalPercent.trim();
    }

    public String getPensionCompanyPercent() {
        return pensionCompanyPercent;
    }

    public void setPensionCompanyPercent(String pensionCompanyPercent) {
        this.pensionCompanyPercent = pensionCompanyPercent == null ? null : pensionCompanyPercent.trim();
    }

    public String getMedicalPersonalPercent() {
        return medicalPersonalPercent;
    }

    public void setMedicalPersonalPercent(String medicalPersonalPercent) {
        this.medicalPersonalPercent = medicalPersonalPercent == null ? null : medicalPersonalPercent.trim();
    }

    public String getMedicalCompanyPercent() {
        return medicalCompanyPercent;
    }

    public void setMedicalCompanyPercent(String medicalCompanyPercent) {
        this.medicalCompanyPercent = medicalCompanyPercent == null ? null : medicalCompanyPercent.trim();
    }

    public String getUnemploymentPersonalPercent() {
        return unemploymentPersonalPercent;
    }

    public void setUnemploymentPersonalPercent(String unemploymentPersonalPercent) {
        this.unemploymentPersonalPercent = unemploymentPersonalPercent == null ? null : unemploymentPersonalPercent.trim();
    }

    public String getUnemploymentCompanyPercent() {
        return unemploymentCompanyPercent;
    }

    public void setUnemploymentCompanyPercent(String unemploymentCompanyPercent) {
        this.unemploymentCompanyPercent = unemploymentCompanyPercent == null ? null : unemploymentCompanyPercent.trim();
    }

    public String getInjuryCompanyPercent() {
        return injuryCompanyPercent;
    }

    public void setInjuryCompanyPercent(String injuryCompanyPercent) {
        this.injuryCompanyPercent = injuryCompanyPercent == null ? null : injuryCompanyPercent.trim();
    }

    public String getBirthCompayPercent() {
        return birthCompayPercent;
    }

    public void setBirthCompayPercent(String birthCompayPercent) {
        this.birthCompayPercent = birthCompayPercent == null ? null : birthCompayPercent.trim();
    }

    public String getActualSalary() {
        return actualSalary;
    }

    public void setActualSalary(String actualSalary) {
        this.actualSalary = actualSalary == null ? null : actualSalary.trim();
    }

    public String getPersonalTotal() {
        return personalTotal;
    }

    public void setPersonalTotal(String personalTotal) {
        this.personalTotal = personalTotal == null ? null : personalTotal.trim();
    }

    public String getCompanyTotal() {
        return companyTotal;
    }

    public void setCompanyTotal(String companyTotal) {
        this.companyTotal = companyTotal == null ? null : companyTotal.trim();
    }

    public String getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(String paymentTotal) {
        this.paymentTotal = paymentTotal == null ? null : paymentTotal.trim();
    }

    public String getHouseBasePersonal() {
        return houseBasePersonal;
    }

    public void setHouseBasePersonal(String houseBasePersonal) {
        this.houseBasePersonal = houseBasePersonal == null ? null : houseBasePersonal.trim();
    }

    public String getHouseBaseCompany() {
        return houseBaseCompany;
    }

    public void setHouseBaseCompany(String houseBaseCompany) {
        this.houseBaseCompany = houseBaseCompany == null ? null : houseBaseCompany.trim();
    }

    public String getHouseSupplyPersonal() {
        return houseSupplyPersonal;
    }

    public void setHouseSupplyPersonal(String houseSupplyPersonal) {
        this.houseSupplyPersonal = houseSupplyPersonal == null ? null : houseSupplyPersonal.trim();
    }

    public String getHouseSupplyCompany() {
        return houseSupplyCompany;
    }

    public void setHouseSupplyCompany(String houseSupplyCompany) {
        this.houseSupplyCompany = houseSupplyCompany == null ? null : houseSupplyCompany.trim();
    }

    public String getHouseBasePersonalPercent() {
        return houseBasePersonalPercent;
    }

    public void setHouseBasePersonalPercent(String houseBasePersonalPercent) {
        this.houseBasePersonalPercent = houseBasePersonalPercent == null ? null : houseBasePersonalPercent.trim();
    }

    public String getHouseBaseCompanyPercent() {
        return houseBaseCompanyPercent;
    }

    public void setHouseBaseCompanyPercent(String houseBaseCompanyPercent) {
        this.houseBaseCompanyPercent = houseBaseCompanyPercent == null ? null : houseBaseCompanyPercent.trim();
    }

    public String getHouseSupplyPersonalPercent() {
        return houseSupplyPersonalPercent;
    }

    public void setHouseSupplyPersonalPercent(String houseSupplyPersonalPercent) {
        this.houseSupplyPersonalPercent = houseSupplyPersonalPercent == null ? null : houseSupplyPersonalPercent.trim();
    }

    public String getHouseSupplyCompanyPercent() {
        return houseSupplyCompanyPercent;
    }

    public void setHouseSupplyCompanyPercent(String houseSupplyCompanyPercent) {
        this.houseSupplyCompanyPercent = houseSupplyCompanyPercent == null ? null : houseSupplyCompanyPercent.trim();
    }

    public String getHousePersonalTotal() {
        return housePersonalTotal;
    }

    public void setHousePersonalTotal(String housePersonalTotal) {
        this.housePersonalTotal = housePersonalTotal == null ? null : housePersonalTotal.trim();
    }

    public String getHouseCompanyTotal() {
        return houseCompanyTotal;
    }

    public void setHouseCompanyTotal(String houseCompanyTotal) {
        this.houseCompanyTotal = houseCompanyTotal == null ? null : houseCompanyTotal.trim();
    }

    public String getHouseToatal() {
        return houseToatal;
    }

    public void setHouseToatal(String houseToatal) {
        this.houseToatal = houseToatal == null ? null : houseToatal.trim();
    }

	public String getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(String incomeTax) {
		this.incomeTax = incomeTax;
	}

	public String getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(String grossSalary) {
		this.grossSalary = grossSalary;
	}

	public String getTaxBase() {
		return taxBase;
	}

	public void setTaxBase(String taxBase) {
		this.taxBase = taxBase;
	}
      
}