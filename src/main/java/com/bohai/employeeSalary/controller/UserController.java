package com.bohai.employeeSalary.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.dao.SysUserMapper;
import com.bohai.employeeSalary.entity.SysUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	
	@RequestMapping("changeUserPW")
	@ResponseBody
	public void changeUserPW(@RequestBody(required = true)SysUser param){
		//获取当前登录用户姓名
		Subject currentUser = SecurityUtils.getSubject();
		SysUser user=this.sysUserMapper.selectByPrimaryKey(((SysUser)currentUser.getSession().getAttribute("user")).getId());
		user.setPassword(param.getPassword());
		this.sysUserMapper.updateByPrimaryKey(user);
	}
	
	@RequestMapping("checkOldPW")
	@ResponseBody
	public String CheckOldPW(@RequestParam String password){
		Subject currentUser = SecurityUtils.getSubject();
		SysUser user=this.sysUserMapper.selectByPrimaryKey(((SysUser)currentUser.getSession().getAttribute("user")).getId());
		Boolean result=false;
		if(!password.equals(user.getPassword())){
			result=false;
		}else{
			result=true;
		}
		Map<String,Boolean> map=new HashMap<>();
		map.put("valid", result);
		ObjectMapper mapper=new ObjectMapper();
		String resultString="";
		try{
			resultString=mapper.writeValueAsString(map);
		}catch(JsonProcessingException e){
			e.printStackTrace();
		}
		return resultString;
	}
	
}
