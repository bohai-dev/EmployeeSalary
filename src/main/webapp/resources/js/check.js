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
  
  function idFormatter(value,row,index){
	  return index+1;
  }



/**
* 审批详情
*/
	function operationFormatter(value, row, index) {
		if(row.tage=="0"){
		var html = '<button type="button" id="cog'+index+'" class="btn btn-info" title="查看详情">'
				+ '审批'
				+ '</button>';
		}else if(row.tage=="1"){
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
		$('#id2').val(row.id);
		$('#staffNumber2').val(row.staffNumber);
		$('#name2').val(row.name);
		$('#depName2').selectpicker('val',row.departmentId);
		$('#positionSalary2').val(row.positionSalary);
		$('#skillSalary2').val(row.skillSalary);
		$('#workYears2').val(row.workYears);
		$('#probationDateStart2').val(row.probationDateStart);
		$('#formalDateStart2').val(row.formalDateStart);
		$('#isProbation2').selectpicker('val',row.isProbation)
		$('#coefficeient2').val(row.coefficeient);
		$('#isLeave2').selectpicker('val',row.isLeave);
		$('#leaveDate2').selectpicker('val',row.leaveDate);
		$('#email2').val(row.email);
		$('#remark2').val(row.remark);

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
 	//查询所有员工信息事件
  function queryqueryCheckMessages() {

		$("#checkMessageTable").bootstrapTable('refresh', {
			url : "queryCheckMessages",	
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
   