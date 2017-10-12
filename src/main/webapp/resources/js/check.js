 /**
   * home页面初始化
   */ 
$(function(){
		var treeObj;
	    if(userLocked=="1"){
	    	treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
	    	           {"href":"toCheck","selectable":false,"showCheckbox":false,"text":"申报信息审核"},
    	               {"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
	    }else{
	    	
	    	treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
	    	               {"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
	    }
	   	$('#tree').treeview({data: treeObj,enableLinks: true});
	   
	    $('#probationDateStart1').datepicker({
	        format: "yyyy-mm-dd",
	          startView: 0,
	          minViewMode: 0,
	          maxViewMode: 2,
	          todayBtn: "linked",
	          clearBtn: true,
	          language: "zh-CN",
	          autoclose: true,
	          todayHighlight: true
	    });

	    $('#formalDateStart1').datepicker({
	        format: "yyyy-mm-dd",
	          startView: 0,
	          minViewMode: 0,
	          maxViewMode: 2,
	          todayBtn: "linked",
	          clearBtn: true,
	          language: "zh-CN",
	          autoclose: true,
	          todayHighlight: true
	    });
	    
	    $('#probationDateStart2').datepicker({
	        format: "yyyy-mm-dd",
	          startView: 0,
	          minViewMode: 0,
	          maxViewMode: 2,
	          todayBtn: "linked",
	          clearBtn: true,
	          language: "zh-CN",
	          autoclose: true,
	          todayHighlight: true
	    });

	    $('#formalDateStart2').datepicker({
	        format: "yyyy-mm-dd",
	          startView: 0,
	          minViewMode: 0,
	          maxViewMode: 2,
	          todayBtn: "linked",
	          clearBtn: true,
	          language: "zh-CN",
	          autoclose: true,
	          todayHighlight: true
	    });
	    
	    $('#leaveDate2').datepicker({
	        format: "yyyy-mm-dd",
	          startView: 0,
	          minViewMode: 0,
	          maxViewMode: 2,
	          todayBtn: "linked",
	          clearBtn: true,
	          language: "zh-CN",
	          autoclose: true,
	          todayHighlight: true
	    });
	    
	  //初始化
	    $('.selectpicker').selectpicker();
	    
	    
	    //绑定初始化方法
	    $('#depName').on('loaded.bs.select', function (e) {
	        $.ajax({
	            url: 'queryDepartment',
	            type: 'post',
	            dataType: 'json',
	            success: function (data) {
	                var len = data.length;
	                 var optionString = "<option > </option>";
	                 for (i = 0; i < len; i++) {
	                     optionString += "<option value=\'"+ data[i].depNumber +"\'>" + data[i].depName + "</option>";
	                 }
	                 
	                 $('#depName').html(optionString);
	                 $('#depName').selectpicker('refresh'); 
	                 $('#depName1').html(optionString);
	                 $('#depName1').selectpicker('refresh'); 
	                 $('#depName2').html(optionString);
	                 $('#depName2').selectpicker('refresh'); 
	            }
	        });
	    });
	    
	   
	    $("#queryForm").keypress(function(e){
           var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
           if (eCode == 13){
               
               queryByCondition();
           }
       });
   });
   
/*------------------------------------------------------------------------------------------------*/ 
/**
 * 判断姓名是否更改
 * */
function compareName(vaule,row,index){
	var temp1=row.NAME;
	var temp2=row.NAME1;
	if(row.SUBMIT_TYPE!=0){
	if(temp1!=temp2){
//		document.getElementById("name").style.color="red"; 
		return temp2+"/<span style='color:red;'>"+temp1+"</span>";
	}
	else{
		return temp2;
	}
	}else{
		return temp1;
	}
}  
/**
 * 判断部门是否更改
 * */
function compareDep(vaule,row,index){
	var temp1=row.DEP_NAME;
	var temp2=row.DEP_NAME1;
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
	}  
/**
 * 岗位工资
 * */
function comparePositionSalary(vaule,row,index){
	var temp1=row.POSITION_SALARY;
	var temp2=row.POSITION_SALARY1;
	if(temp2==null){
		temp2="0";
	}
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
	 }else{
			return temp1;
		}
	} 
/**
 * 技能工资
 * 
 * */
function compareSkillSalary(vaule,row,index){
	var temp1=row.SKILL_SALARY;
	var temp2=row.SKILL_SALARY1;
	if(temp1==temp2&&temp2==null){
		temp1="-";
		temp2="-";
	}
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
	}  

/**
 * 司龄工资
 * */
function compareWorkYears(vaule,row,index){
	var temp1=row.WORK_YEARS;
	var temp2=row.WORK_YEARS1;
	if(temp1==temp2&&temp2==null){
		temp1="-";
		temp2="-";
	}
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
	}  
/**
 * 试用期起始日期
 * */
function compareProbationDateStart(vaule,row,index){
	var temp1=row.PROBATION_DATE_START;
	var temp2=row.PROBATION_DATE_START1;
	if(temp1==temp2&&temp2==null){
		temp1="-";
		temp2="-";
	}
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
	}  
/**
 * 判断转正日期
 * */
function compareFormalDateStart(vaule,row,index){
	var temp1=row.FORMAL_DATE_START;
	var temp2=row.FORMAL_DATE_START1;
	if(temp1==temp2&&temp2==null){
		temp1="-";
		temp2="-";
	}
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
	}  
/**
  * 员工类型及状态字符判断
  */
  function probationFormatter(vaule,row,index){
  	var temp1=row.IS_PROBATION;
  	var temp2=row.IS_PROBATION1;
  	if(temp1=="0"){
  		temp1="正式员工";
  	}else if(temp1=="1"){
  		temp1="试用期员工";
  	}else{
  		temp1="其他";
  	}
  	
  	if(temp2=="0"){
  		temp2="正式员工";
  	}else if(temp2=="1"){
  		temp2="试用期员工";
  	}else{
  		temp2="其他";
  	}

	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
	}  
  
  /**
   * 判断工资系数
   * */
  function compareCoefficeient(vaule,row,index){
	  var temp1=row.COEFFICEIENT;
	  var temp2=row.COEFFICEIENT1;
	  if(row.SUBMIT_TYPE!=0){
			if(temp1!=temp2){
				return temp2+"/<span style='color:red;'>"+temp1+"</span>";
			}
			else{
				return temp2;
			}
			}else{
				return temp1;
			}
		}  
  
  	
 /**
  * 比较离职状态判断
  * */ 
  function LeaveFormatter(vaule,row,index){
  	var temp1=row.IS_LEAVE;
  	var temp2=row.IS_LEAVE1;
  	if(temp1=="0"){
  		temp1="在职";
  	}else{
  		temp1="离职";
  	}
  	
  	if(temp2=="0"){
  		temp2="在职";
  	}else{
  		temp2="离职";
  	}
  	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
  
  }
  /**
   * 比较离职日期
   * */
  function compareLeaveDate(vaule,row,index){
	var temp1=row.LEAVE_DATE;
	var temp2=row.LEAVE_DATE1;
	if(row.SUBMIT_TYPE!=0){
		if(temp1!=temp2){
			return temp2+"/<span style='color:red;'>"+temp1+"</span>";
		}
		else{
			return temp2;
		}
		}else{
			return temp1;
		}
  }
  
  /**
   * 比较邮箱
   * */
  function compareEmail(vaule,row,index){
	  var temp1=row.EMAIL;
	  var temp2=row.EMAIL1;
	  if(row.SUBMIT_TYPE!=0){
			if(temp1!=temp2){
				return temp2+"/<span style='color:red;'>"+temp1+"</span>";
			}
			else{
				return temp2;
			}
			}else{
				return temp1;
			}
  }
  /**
   * 比较备注
   * */
  function compareRemark(vaule,row,index){
	  var temp1=row.REMARK;
	  var temp2=row.REMARK1;
	  if(row.SUBMIT_TYPE!=0){
			if(temp1!=temp2){
				return temp2+"/<span style='color:red;'>"+temp1+"</span>";
			}
			else{
				return temp2;
			}
			}else{
				return temp1;
			}
  }
  function submitTypeFormatter(vaule,row,index){
	  var result=row.SUBMIT_TYPE;
	  if(result=="0"){
		  result="新建员工信息审核";
	  }else if(result=="1"){
		  result="修改员工信息审核";
	  }else if(result=="2"){
		  result="离职员工信息审核";
	  }
	  return result;
  }
  
  function idFormatter(value,row,index){
	  return index+1;
  }



/**
* 审批详情
*/
	function operationFormatter(value, row, index) {
		if(row.TAGE=="0"){
		var html = '<button type="button" id="cog'+index+'" class="btn btn-info" title="查看详情">'
				+ '审批'
				+ '</button>';
		}else if(row.TAGE=="1"){
			var html='<label>'+'审批通过'+'</label>';
		}else{
			var html='<label>'+'审批拒绝'+'</label>';
		}

		$("#checkMessageTable").on("click", "#cog" + index, row, function(event) {
			config(row);
		});

		return html;
	}
	/* 修改任务模态框 */
	function config(row) {
		// 	console.log('456');
		$('#id2').val(row.ID);
		$('#staffNumber2').val(row.STAFF_NUMBER);
		$('#name2').val(row.NAME);
		$('#depName2').selectpicker('val',row.DEPARTMENT_ID);
		$('#positionSalary2').val(row.POSITION_SALARY);
		$('#skillSalary2').val(row.SKILL_SALARY);
		$('#workYears2').val(row.WORK_YEARS);
		$('#probationDateStart2').val(row.PROBATION_DATE_START);
		$('#formalDateStart2').val(row.FORMAL_DATE_START);
		$('#isProbation2').selectpicker('val',row.IS_PROBATION)
		$('#coefficeient2').val(row.COEFFICEIENT);
		$('#isLeave2').selectpicker('val',row.IS_LEAVE);
		$('#leaveDate2').selectpicker('val',row.LEAVE_DATE);
		$('#email2').val(row.EMAIL);
		$('#remark2').val(row.REMARK);

		$("#allMessageModal").modal('show');

	}
 /**
 *定义的方法事件
 */
 
 //按条件查询
  function  queryByCondition(){
  	$('#checkMessageTable').bootstrapTable('refresh',{
  		url:"queryByCheckCondition",
  					query:{
  						departmentId:$('#depName').val(),
  						staffNumber:$('#staffNumber').val(),
  						name:$('#name').val(),
  						tage:$('#tage').val(),
  						submitType:$('#submitType').val()
  					}
  				}
  		);
  }
/*------------------------------------------------------------------------------------------*/   

function agree(){
	if(confirm("同意通过本条待审核信息,确定吗？")){
		var param = {
				id:$('#id2').val(),
	    		staffNumber:$('#staffNumber2').val(),
	    		name:$('#name2').val(),
	    		departmentId:$('#depName2').val(),
	    		positionSalary:$('#positionSalary2').val(),
	    		skillSalary:$('#skillSalary2').val(),
	    		workYears:$('#workYears2').val(),
	    		probationDateStart:$('#probationDateStart2').val(),
	    		formalDateStart:$('#formalDateStart2').val(),
	    		isProbation:$('#isProbation2').val(),
	    		coefficeient:$('#coefficeient2').val(),
	            email:$('#email2').val(),
	            remark:$('#remark2').val(),
	            isLeave:$('#isLeave2').val(),
	            LeaveDate:$('#leaveDate2').val(),
	            approvalOpinion:$('#approvalOpinion2').val()
	            }
		 $.ajax({
	         url: 'agreeStaffInfo',
	         type: 'post',
	         contentType: "application/json;charset=UTF-8",
	         data: JSON.stringify(param),
	         success: function (data,status) {
	             $('#allMessageModal').modal('hide');
	             alert("信息已通过审核!");
	             $('#checkMessageTable').bootstrapTable('refresh');
	         }
	    });
	}
}

function refuse(vaule,row,index){
	if(confirm("拒绝通过本条待审核信息,确定吗？")){
		var param = {
				id:$('#id2').val(),
	    		staffNumber:$('#staffNumber2').val(),
	    		name:$('#name2').val(),
	    		departmentId:$('#depName2').val(),
	    		positionSalary:$('#positionSalary2').val(),
	    		skillSalary:$('#skillSalary2').val(),
	    		workYears:$('#workYears2').val(),
	    		probationDateStart:$('#probationDateStart2').val(),
	    		formalDateStart:$('#formalDateStart2').val(),
	    		isProbation:$('#isProbation2').val(),
	    		coefficeient:$('#coefficeient2').val(),
	            email:$('#email2').val(),
	            remark:$('#remark2').val(),
	            isLeave:$('#isLeave2').val(),
	            LeaveDate:$('#leaveDate2').val(),
	            approvalOpinion:$('#approvalOpinion2').val()
	            }
		 $.ajax({
	         url: 'refuseStaffInfo',
	         type: 'post',
	         contentType: "application/json;charset=UTF-8",
	         data: JSON.stringify(param),
	         success: function (data,status) {
	             $('#allMessageModal').modal('hide');
	             alert("拒绝通过审核!");
	             $('#checkMessageTable').bootstrapTable('refresh');
	         }
	    });
	}
}

function checkSome(){
	
	var selectData= $("#checkMessageTable").bootstrapTable('getSelections');
	 if(selectData.length<=0){
		 alert("请选中一行")
	}else{
		var dataArray=new Array();
		for(i=0;i<selectData.length;i++){
			var param = {
					id:selectData[i]["ID"],
		    		staffNumber:selectData[i]["STAFF_NUMBER1"],
		    		name:selectData[i]["NAME1"],
		    		departmentId:selectData[i]["DEPARTMENT_ID1"],
		    		positionSalary:selectData[i]["ID"],
		    		skillSalary:selectData[i]["ID"],
		    		workYears:selectData[i]["ID"],
		    		probationDateStart:selectData[i]["ID"],
		    		formalDateStart:selectData[i]["ID"],
		    		isProbation:selectData[i]["ID"],
		    		coefficeient:selectData[i]["ID"],
		            email:selectData[i]["ID"],
		            remark:selectData[i]["ID"],
		            isLeave:selectData[i]["ID"],
		            LeaveDate:selectData[i]["ID"],
		            approvalOpinion:selectData[i]["ID"]
		            }
		   	
		}
		
		var param=JSON.stringify( selectData );
		console.log(param);
		 $.ajax({
	         url: 'agreeStaffInfoList',
	         type: 'post',
	         contentType: "application/json;charset=UTF-8",
	         data: param,
	         success: function (data,status) {
	           
	             alert("信息已通过审核!");
	             $('#checkMessageTable').bootstrapTable('refresh');
	         },
	         error : function (data,status){
		            alert(data.responseText);
		        }
	         
	    });
		/*var url="${pageContext.request.contextPath}/login/datalist";
 		    $.ajax({
 		        dataType: "json",
 		        traditional:true,//这使json格式的字符不会被转码
 		        data: {"datalist":b},
 		        type: "post", 
 		        url: url,
 		        success : function (data) {
 		            alert("成功！");
 		        },
 		        error : function (data){
 		            alert(data.responseText);
 		        }
 		    });*/
	} 
	
}
   