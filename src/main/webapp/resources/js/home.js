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
	                 $('#depName3').html(optionString);
	                 $('#depName3').selectpicker('refresh'); 
	            }
	        });
	    });
	    
	   
	    $("#queryForm").keypress(function(e){
           var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
           if (eCode == 13){
               
               queryByCondition();
           }
       });
	    $("#queryForm2").keypress(function(e){
	           var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
	           if (eCode == 13){
	               
	               queryCheckMessageByCondition();
	           }
	       });
   });
   
/*------------------------------------------------------------------------------------------------*/ 
  
/**
  * 员工类型及状态字符判断
  */
  function probationFormatter(vaule,row,index){
  	var result=row.isProbation;
  	if(result=="0"){
  		result="正式员工";
  	}else if(result=="1"){
  		result="试用期员工";
  	}else{
  		result="其他";
  	}
  	return result;
  }
  
  function LeaveFormatter(vaule,row,index){
  	var result=row.isLeave;
  	if(result=="0"){
  		result="在职";
  	}else{
  		result="离职";
  	}
  	return result;
  }
/*------------------------------------------------------------------------------------------------*/   

/**
* select选择框onclick点击事件
*/
  function changeCoefficeient1(){
  	if($('#isProbation1').val()=="0"){
  	$('#coefficeient1').val("1.0");
  	}else if($('#isProbation1').val()=="1"){
  		$('#coefficeient1').val("0.8");
  	}else if($('#coefficeient2').val()=="2"){
  		$('#coefficeient1').val("请输入自定义系数");
  	}
  	
  }
  
  function changeCoefficeient2(){
  	if($('#isProbation2').val()=="0"){
  	$('#coefficeient2').val("1.0");
  	}else if($('#isProbation2').val()=="1"){
  		$('#coefficeient2').val("0.8");
  	}else if($('#coefficeient2').val()=="2"){
  		$('#coefficeient2').val("请输入自定义系数");
  	}
  	
  }
  
  function idFormatter(value,row,index){
	  return index+1;
  }
  function submitTypeFormatter(vaule,row,index){
	  var result=row.submitType;
	  if(result=="0"){
		  result="新建员工信息审核";
	  }else if(result=="1"){
		  result="修改员工信息审核";
	  }else if(result=="2"){
		  result="离职员工信息审核";
	  }
	  return result;
  }
  function tageFormatter(vaule,row,index){
	  var result=row.tage;
	  if(result=="0"){
		  result="待审核";
	  }else if(result=="1"){
		  result="审核通过";
	  }else if(result=="2"){
		  result="拒绝审核";
	  }
	  return result;
  }
/*------------------------------------------------------------------------------------------------*/   


/**
* 修改按钮
*/
	function operationFormatter(value, row, index) {
		var html = '<button type="button" id="cog'+index+'" class="btn btn-default btn-sm" title="设置">'
				+ '<i class="glyphicon glyphicon-cog"></i>'
				+ '</button>';

		$("#staffInfoTable").off("click", "#cog" + index);
		$("#staffInfoTable").on("click", "#cog" + index, row, function(event) {
			config(row);
		});

		return html;
	}
	/* 修改任务模态框 */
	function config(row) {
		// 	console.log('456');
		$('#staffNumber2').val(row.staffNumber);
		$('#name2').val(row.name);
		$('#depName2').selectpicker('val',row.departmentId);
		$('#positionSalary2').val(row.positionSalary);
		$('#skillSalary2').val(row.skillSalary);
		$('#workYears2').val(row.workYears);
		//$('#probationDateStart2').val(row.probationDateStart);
		$("#probationDateStart2").datepicker("setDate", row.probationDateStart);
		//$('#formalDateStart2').val(row.formalDateStart);
		$("#formalDateStart2").datepicker("setDate", row.formalDateStart);
		$('#isProbation2').selectpicker('val',row.isProbation)
		$('#coefficeient2').val(row.coefficeient);
		$('#isLeave2').selectpicker('val',row.isLeave);
		//$('#leaveDate2').val('val',row.leaveDate);
		$("#leaveDate2").datepicker("setDate", row.leaveDate);
		$('#email2').val(row.email);
		$('#remark2').val(row.remark);

		$("#editModal").modal('show');

	}
/*---------------------------------------------------------------------------------------------*/	
  
  /**
  *检查输入数据合法性
  */
  function checkData(){
  	if(($('#staffNumber1').val()=="")||($('#name1').val()=="")
  			||($('#idNumber1').val()=="")||($('#depName1').val()=="")
  			||($('#positionSalary1').val()=="")||($('#isProbation1').val()=="")
  			||($('#coefficeient1').val()=="")
  	){
  		alert("标★为必填项，请重新填写!");
  	}else{
  		submitStaffInfo();
  	}
  		
  }
  
  
  function checkData2(){
  	if($('#isLeave2').val()=="1"){
  		if($('#leaveDate').val()==""){
  			alert("正在执行员工离职操作，请输入离职日期!");
  		}else{
  		submitUpdateStaffInfo();
  		}
  	}
  	else{
  	if(($('#name2').val()=="")||($('#depName2').val()=="")
  			||($('#positionSalary2').val()=="")
  			||($('#isProbation2').val()=="")||($('#coefficeient2').val()=="")
  	){
  		alert("标★为必填项，请重新填写!");
  	}else{
  		submitUpdateStaffInfo();
  	}
  	}
  }
  
 /*-------------------------------------------------------------------------------*/  
  
 /**
 *定义的方法事件
 */
 
 //按条件查询
  function  queryByCondition(){
  	$('#staffInfoTable').bootstrapTable('refresh',{
  		url:"queryByCondition",
  					query:{
  						departmentId:$('#depName').val(),
  						staffNumber:$('#staffNumber').val(),
  						name:$('#name').val(),
  						isProbation:$('#isProbation').val(),
  						isLeave:$('#isLeave').val()
  					}
  				}
  		);
  }

// //保存员工信息事件
//  function saveStaffInfo(){
//      var param = {
//      		staffNumber:$('#staffNumber1').val(),
//      		name:$('#name1').val(),
//      		departmentId:$('#depName1').val(),
//      		positionSalary:$('#positionSalary1').val(),
//      		skillSalary:$('#skillSalary1').val(),
//      		workYears:$('#workYears1').val(),
//      		probationDateStart:$('#probationDateStart1').val(),
//      		formalDateStart:$('#formalDateStart1').val(),
//      		isProbation:$('#isProbation1').val(),
//      		coefficeient:$('#coefficeient1').val(),
//              email:$('#email1').val(),
//              remark:$('#remark1').val()
//              }
//       $.ajax({
//           url: 'saveStaffInfo',
//           type: 'post',
//           contentType: "application/json;charset=UTF-8",
//           data: JSON.stringify(param),
//           success: function (data,status) {
//               $('#addModal').modal('hide');
//               $('#staffInfoTable').bootstrapTable('refresh');
//           }
//      });
//  }
//  
  
//提交审核信息事件
function submitStaffInfo(){
    var param = {
    		staffNumber:$('#staffNumber1').val(),
    		name:$('#name1').val(),
    		departmentId:$('#depName1').val(),
    		positionSalary:$('#positionSalary1').val(),
    		skillSalary:$('#skillSalary1').val(),
    		workYears:$('#workYears1').val(),
    		probationDateStart:$('#probationDateStart1').val(),
    		formalDateStart:$('#formalDateStart1').val(),
    		isProbation:$('#isProbation1').val(),
    		coefficeient:$('#coefficeient1').val(),
            email:$('#email1').val(),
            remark:$('#remark1').val()
            }
     $.ajax({
         url: 'submitStaffInfo',
         type: 'post',
         contentType: "application/json;charset=UTF-8",
         data: JSON.stringify(param),
         success: function (data,status) {
             $('#addModal').modal('hide');
             alert("信息以提交审核，请等待!");
         }
    });
}

	//提交更新员工审核信息事件
  function submitUpdateStaffInfo(){
  	 var param = {
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
       		isLeave:$('#isLeave2').val(),
       		leaveDate:$('#leaveDate2').val(),
              email:$('#email2').val(),
              remark:$('#remark2').val()
               }
        $.ajax({
            url: 'submitUpdateStaffInfo',
            type: 'post',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(param),
            success: function (data,status) {
                $('#editModal').modal('hide');
                alert("信息以提交审核，请等待!");
                $('#staffInfoTable').bootstrapTable('refresh');
            }
       });
   }
  
 	//查询所有员工信息事件
  function queryStaffInfos() {

		$("#staffInfoTable").bootstrapTable('refresh', {
			url : "queryStaffInfos",	
			query : {
				staffNumber:$('#staffNumber').val(),
      		name:$('#name').val(),
      		departmentId:$('#departmentId').val(),
      		positionSalary:$('positionSalary').val(),
      		skillSalary:$('skillSalary').val(),
      		workYears:$('#workYears').val(),
      		isProbation:$('#isProbation').val(),
              email:$('#email').val(),
              remark:$('#remark').val()

			}
		});
	}
  
  function queryCheckMessagesBySubmitter(){
	  $("#submittercheckMessageTable").bootstrapTable('refresh', {
		  url:"queryCheckMessagesBySubmitter"
	  });
	  }
 
/*------------------------------------------------------------------------------------------*/   

//按条件查询
  function  queryCheckMessageByCondition(){
  	$('#submittercheckMessageTable').bootstrapTable('refresh',{
  		url:"queryCheckMessageByCondition",
  					query:{
  						departmentId:$('#depName3').val(),
  						staffNumber:$('#staffNumber3').val(),
  						name:$('#name3').val(),
  						tage:$('#tage').val(),
  						submitType:$('#submitType').val()
  					}
  				}
  		);
  }
   
   