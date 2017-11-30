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
	    
	    checkAddForm(); //输入表单验证
	    checkEditForm();//修改表单验证
	 

   });
 
var rownum;
//输入表单验证
function checkAddForm(){	
	   
    $('#addForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
           
        	
        	staffNumber1: {
                validators: {
                    notEmpty: {
                        message: '员工编号不能为空'
                    },
                    threshold :  2 , // 有2字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                    remote: {// ajax验证。server result:{"valid",true or
								                      // false} 向服务发送当前input
								                       // name值，获得一个json数据。例表示正确：{"valid",true}
                        url: 'checkStaffNumber',// 验证地址
                        delay :  2000,// 每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'// 请求方式
                        /**
						 * 自定义提交数据，默认值提交当前input value data:
						 * function(validator) { return { password:
						 * $('[name="passwordNameAttributeInYourForm"]').val(),
						 * whatever:
						 * $('[name="whateverNameAttributeInYourForm"]').val() }; }
						 */
                    },
                   
                   
                }
            },
            
            name1: {
            	  validators: {
                      notEmpty: {
                          message: '姓名不能为空'
                      }
                      
                  }
              },
             
              depName1: {
            	  validators: {
                      notEmpty: {
                          message: '部门不能为空'
                      }
               
                  }
              	},
              positionSalary1: {
               	  validators: {
                      notEmpty: {
                             message: '正式工资不能为空'
                         },                      
                      numeric: {message: '工资只能输入数字'}     
                     }
                 	},
                probationSalary1: {
                     	  validators: {
                            notEmpty: {
                                   message: '试用期工资不能为空'
                               },                      
                            numeric: {message: '工资只能输入数字'}     
                           }
                      },   	
              isProbation1: {
               	  validators: {
                       notEmpty: {
                             message: '员工类型不能为空'
                         }
                     }
                 	}            
               
            }
       
    }).on('success.form.bv', function(e) {// 点击提交之后
    	// Prevent form submission
    	  e.preventDefault(); 
    	  submitStaffInfo();
    });
    
}

//修改表单验证
function checkEditForm(){
	
	
    $('#editForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	name2: {
                  validators: {
                     notEmpty: {
                             message: '姓名不能为空'
                                  }
                              }
                              },
                              depName2: {
                            	  validators: {
                                      notEmpty: {
                                          message: '部门不能为空'
                                      }
                                  }
                              	},
                              positionSalary2: {
                               	  validators: {
                                      notEmpty: {
                                             message: '岗位工资不能为空'
                                         },
                                       numeric: {message: '工资只能输入数字'}    
                                     }
                                 	},
                              isProbation2: {
                               	  validators: {
                                       notEmpty: {
                                             message: '员工类型不能为空'
                                         }
                                     }
                                 	}
           }
        });
    /* .on('success.form.bv', function(e) {// 点击提交之后,提交更新信息
        	
           e.preventDefault();         
	       if($('#isLeave2').val()=="1"){
	       		if($('#leaveDate2').val()==""){
	       			alert("正在执行员工离职操作，请输入离职日期!");
	       		}else{
	       		     submitUpdateStaffInfo();
	       		}
	       	}
	       	else{
	       		submitUpdateStaffInfo();
	       	}
	    });*/
    
}
  
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
  function salaryFormatter(vaule,row,index){
	  
        var html='<a id="cog'+index+'"> 查看详情 </a>'
        $("#submittercheckMessageTable").off("click", "#cog" + index);
        $("#submittercheckMessageTable").on("click", "#cog" + index, row, function(event) {
        	 console.log(row);
        	 selectSalaryDetail(row.id); 
	         $("#salaryDetailModal").modal('show');
       });

      return html;	  
  }
  
  function selectSalaryDetail(id){
		 $('#salaryRecordTable').bootstrapTable('destroy');
		 $('#salaryRecordTable').bootstrapTable({  
		        url: 'salaryDetail/selectByCheckMessageId/'+id,  	        
		        method: 'get',	     
		        striped: true,  
		        cache: false,		       
		        sortName: 'createTime',  
		        sortOrder: 'desc', 	       
		        clickToSelect: true,       
		        
		        columns: [
		        	{  
			            field: 'index',  
			            title: '编号', 
			            formatter: idFormatter
			            
			        },{  
		            field: 'salary',  
		            title: '工资',  
		            
		          }, {  
		            field: 'startTime',  
		            title: '开始时间',  
		        }, {  
		            field: 'endTime',  
		            title: '结束时间',  
		        }]  
		    });  
		   
		
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
  function statusFormatter(vaule,row,index){
	  var result=row.submitStatus;
	  if(result=="0"){
		  result="是";
	  }else if(result=="1"||result==null||result==""){
		  result="否";
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
		console.log(row.staffNumber);
		
		
		
		setSalaryDetail(row.staffNumber);		
		
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
		//$('#coefficeient2').val(row.coefficeient);
		$('#isLeave2').selectpicker('val',row.isLeave);
		//$('#leaveDate2').val('val',row.leaveDate);
		$("#leaveDate2").datepicker("setDate", row.leaveDate);
		$('#email2').val(row.email);
		$('#remark2').val(row.remark);
		$('#probationSalary2').val(row.probationSalary);
		
		//重新加载验证
	    $("#editForm").data('bootstrapValidator').destroy();
	    $('#editForm').data('bootstrapValidator', null);
	    checkEditForm();
		$("#editModal").modal('show');

	}

  
  
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
  						isLeave:$('#isLeave').val(),
  						submitStatus:$('#submitStatus').val()
  					}
  				}
  		);
  }


  
//提交审核信息事件
function submitStaffInfo(){
	//alert(45);
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
            remark:$('#remark1').val(),
            probationSalary:$('#probationSalary1').val()
            }
     $.ajax({
         url: 'submitStaffInfo',
         type: 'post',
         contentType: "application/json;charset=UTF-8",
         data: JSON.stringify(param),
         success: function (data,status) {
        	 
        	 alert(data['message']);
             $('#addModal').modal('hide');
             //重新加载验证
             $("#addForm").data('bootstrapValidator').destroy();
             $('#addForm').data('bootstrapValidator', null);
             checkAddForm();
             
        	// $('#addForm').bootstrapValidator('disableSubmitButtons', false);
        	 $('#staffNumber1').val("");
     		 $('#name1').val("");
     		 $('#depName1').selectpicker('val','');
     		 $('#positionSalary1').val("");
     		 $('#skillSalary1').val("");
     		 $('#workYears1').val("");
     		 $('#probationDateStart1').val("");
     		 $('#formalDateStart1').val("");
     		 $('#isProbation1').selectpicker('val','');
     		 $('#coefficeient1').val("");
             $('#email1').val("");
             $('#remark1').val("");
             $('#probationSalary1').val("");
         }
    });
}


	//提交更新员工审核信息事件
  function submitUpdateStaffInfo(){
	  
	
	 //验证工资表格数据
	 var vertifyResult=verifyTable();
	 if(vertifyResult){
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
	            remark:$('#remark2').val(),
	            probationSalary:$('#probationSalary2').val(),
	            salaryDetails:$('#editTable').bootstrapTable('getData'),
	               }
	        $.ajax({
	            url: 'submitUpdateStaffInfo',
	            type: 'post',
	            contentType: "application/json;charset=UTF-8",
	            data: JSON.stringify(param),
	            success: function (data,status) {
	            	if(data["status"]=="false"){
	           		 alert("该员工的修改信息正在审核中，请勿重复提交！");
	           	    }else if(data["status"]=="success"){
	           		    alert("信息已提交审核，请等待!");
	           		    $('#editModal').modal('hide'); 
	                   
	   	        	 }
	            	$('#staffInfoTable').bootstrapTable('refresh');
	            	//重新加载验证
	                $("#editForm").data('bootstrapValidator').destroy();
	                $('#editForm').data('bootstrapValidator', null);
	                checkEditForm();
	                
	            	$('#staffNumber2').val(""),
	        		$('#name2').val(""),
	        		$('#depName2').selectpicker('val','');	        		
	        		$('#positionSalary2').val(""),
	        		$('#skillSalary2').val(""),
	        		$('#workYears2').val(""),
	        		$('#probationDateStart2').val(""),
	        		$('#formalDateStart2').val(""),
	        		$('#isProbation2').selectpicker('val',''),
	        		$('#coefficeient2').val(""),
	                $('#email2').val(""),
	                $('#remark2').val(""),
	                $('#probationSalary2').val("")
	            }
	       });
	   }	 
	 
 	
   }
   //验证工资表格数据
  function verifyTable(){
  	 var datas = $('#editTable').bootstrapTable('getData');
       for(var row in datas){
           if(datas[row].salary == null || datas[row].salary == ''){
               alert("第"+(parseInt(row)+1)+"行工资不能为空");
               return false;
           }
           if(datas[row].startTime == null ||datas[row].startTime == ''){
               alert("第"+(parseInt(row)+1)+"行生效时间不能为空");
               return false;
           }
       }
     //重新加载验证
       $("#editForm").data('bootstrapValidator').destroy();
       $('#editForm').data('bootstrapValidator', null);
       checkEditForm();
       return true;
       
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
            remark:$('#remark').val(),
            submitStatus:$('#submitStatus').val()

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
//模板下载
  function downloadModel(){
	// alert(123);
	  document.getElementById("templeteFile").submit();
  }
  
  //positionSalary1的onblur事件
  function positionSalary1Blur(){
	  var f=parseFloat($('#positionSalary1').val());
	  if(isNaN(f)){
		  return;
		  
	  }else{
		  
		  //默认正式工资的80% 
		  $('#probationSalary1').val((f*0.8).toFixed(2));  
	     
	  }
   }
  
  function showSalaryDetail(){
	    
	    $("#editDiv").fadeToggle();
		
	}



function setSalaryDetail(staffNumber){
	//console.log(staffNumber);
	rownum=null;
			
	$('#editTable').bootstrapTable('destroy');
		 $('#editTable').bootstrapTable({  
		        url: 'salaryDetail/queryByStaffNumber/'+staffNumber,  	        
		        method: 'get',
		        toolbar: '#addToolbar',  
		        pagination: true,  
		        sidePagination: 'client',  
		        pageNumber: 1,  
		        pageSize: 5,  	        
		        pageList: [5, 10, 20, 40],  
		        clickToSelect: true,       
		        
		        columns: [
		        	{  
			            field: 'rownum',  
			            title: '编号', 
			            visible:false
			            
			        },
			        {  
		            field: 'salary',  
		            title: '工资', 
		            editable: {  
		                type: 'number'
		                }  
		            
		            }, 
		            {  
		            field: 'startTime',  
		            title: '开始时间', 
		            editable: {  
		                type: 'date', 
		                datepicker:{language:'zh-CN'}
		               } ,
		                 
		           }, 
		           {  
		            field: 'endTime',  
		            title: '结束时间',
		            editable: {  
		                type: 'date',  
		                datepicker:{language:'zh-CN'},
		                }  
		          },
		          {  
		            field: 'operation',  
		            title: '操作',  
		            width: 100,  
		            formatter: function (value, row, index) { 		           
		                var d = '<a class = "remove" href="javascript:void(0)">删除</a>';  
		                return  d;  
		            },  
		            events: 'operateEvents'  
		         }]  
		    });  
		 
		 
		 window.operateEvents = {  
			        
			'click .remove': function (e, value, row, index) {  
				 $('#editTable').bootstrapTable('remove', {
		                field: 'rownum',
		                values: [row.rownum]
		            });
			     }  // remove end 
			}; 
		
		
	}


function addSalaryDetail(){
	  if(rownum == null){
          rownum = $('#editTable').bootstrapTable('getOptions').totalRows+1;
      }
      
      $('#editTable').bootstrapTable('insertRow', {
          index: 0,
          row: {
              rownum: rownum++,
              startTime:'',
              endTime:''
          }
      });   //
}  

function submitMod(){
	/*手动验证表单，当是普通按钮时。*/ 
	$('#editForm').data('bootstrapValidator').validate(); 
	if(!$('#editForm').data('bootstrapValidator').isValid()){ 
	        return ; 
	}
	
	if($('#isLeave2').val()=="1"){
    		if($('#leaveDate2').val()==""){
    			alert("正在执行员工离职操作，请输入离职日期!");
    		}else{
    		     submitUpdateStaffInfo();
    		}
    	}
    	else{
    		submitUpdateStaffInfo();
    	}
	
	
}


  
  
  

  
  
   
   