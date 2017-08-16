package com.bohai.employeeSalary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalaryController {
	
	@RequestMapping(value="toSalary")
    public String toSalary(){
        return "salary";
    }

}
