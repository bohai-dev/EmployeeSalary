package com.bohai.employeeSalary.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.service.FileUploadService;

@Controller
public class FileUploadController {
	static Logger logger = Logger.getLogger(FileUploadController.class);
	
	@Autowired
	FileUploadService salaryUploadService;
	@Autowired
	FileUploadService commissionUploadService;
	@Autowired
	FileUploadService staffInfoUploadService;
	
	
	@RequestMapping(value="salaryFileUpload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> fileUpload(@RequestParam("file_data") MultipartFile file, HttpServletRequest request) throws BohaiException {
		logger.debug("文件上传");
		
		String fileName;
		try {
			fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			fileName = file.getOriginalFilename();
			logger.debug(fileName);
			String message=salaryUploadService.upload(file);
			logger.debug(message);
			
		} catch (UnsupportedEncodingException e1) {
			logger.error("格式不支持",e1);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", "success");
		return map;
	}
	
	
	
	@RequestMapping(value="CommissionFileUpload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> commissionFileUpload(@RequestParam("file_data") MultipartFile file, HttpServletRequest request) throws BohaiException {
		logger.debug("文件上传");
		
		String fileName;
		try {
			fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			fileName = file.getOriginalFilename();
			logger.debug(fileName);
			String message=commissionUploadService.upload(file);
			logger.debug(message);
			
		} catch (UnsupportedEncodingException e1) {
			logger.error("格式不支持",e1);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", "success");
		return map;
	}
	
	//员工信息表上传
	@RequestMapping(value="StaffInfoFileUpload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> staffInfoFileUpload(@RequestParam("file_data") MultipartFile file, HttpServletRequest request) throws BohaiException {
		logger.debug("员工信息表上传");
		
		String fileName;
		String message="";
		try {
			fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			fileName = file.getOriginalFilename();
			logger.debug(fileName);
			message=staffInfoUploadService.upload(file);
			logger.debug(message);
			
		} catch (UnsupportedEncodingException e1) {
			logger.error("格式不支持",e1);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", message);
		return map;
	}
	
	

	
	

}
