<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">   
    <link href="resources/favicon.ico" rel="shortcut icon"/>
    <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    <!-- table -->    
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    
    <script type="text/javascript" src="resources/js/home.js"></script>
    
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    
    <!-- bootstrap-select -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    
    
    <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    
    <script type="text/javascript">
   
    
    $(function(){
	    $('#probationDateStart').datepicker({
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

	    $('#formalDateStart').datepicker({
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
	            }
	        });
	    });
	    
	   
	    $("#queryForm").keypress(function(e){
            var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
            if (eCode == 13){
                //自己写判断函数
                queryByCondition();
            }
        });
    });
    
   
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
  //导出excel
    function exportStaffInfo(){
        $('#queryForm').submit();
    }
    
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
    
    function changeCoefficeient(){
    	if($('#isProbation1').val()=="0"){
    	$('#coefficeient1').val("1.0");
    	}else if($('#isProbation1').val()=="1"){
    		$('#coefficeient1').val("0.8");
    	}else{
    		$('#coefficeient1').val("请输入自定义系数");
    	}
    	
    }
    
    
    /* 修改任务模态框 */
	function config(row) {
		// 	console.log('456');
		$('#editUserName').val(row.username);
		$('#editPassword').val(row.password);
		$('#editFullName').val(row.fullName);	
		$('#editUserId').val(row.id);
		$("#editModal").modal('show');

	}
	/*删除*/
	function trash(row) {
		if (confirm("删除用户：" + row.username + "后将不可恢复,确定吗？")) {
			var param = {
				id : row.id
			};
			$.ajax({
				type : "post",
				url : "removeUser",
				//dataType: 'json',
				contentType : "application/json;charset=UTF-8",
				data : JSON.stringify(param),
				success : function(date, status) {
					alert("删除成功");
					$("#userTable").bootstrapTable('refresh');
				}
			});
		}

	}
    
    
    /* 修改任务模态框 */
    function config(row){
        
    	$('#name2').val(row.name);
    	$('#depName2').val(row.departmentId);
    	$('#positionSalary2').val(row.positionSalary);
    	$('#workYears2').val(row.workYears);
    	$('#formalDateStart2').val(row.formalDateStart);
    	$('#coefficeient2').val(row.coefficeient);
    	$('#email2').val(row.email);
    	$('#remark2').val(row.remark);
    	
    	
    	$('#depName2').selectpicker('val', row.departmentId);
    	$('#isProbation2').selectpicker('val', row.isProbation);
    	
    	
    	$("#editModal").modal('show');
    	
    }
    
    /* 删除客户信息 */
    function trash(row){
        if(confirm("删除客户："+row.investorName+"("+row.investorNo+")后将不可恢复,确定吗？")){
            var param = {investorNo:row.investorNo};
            $.ajax({
                type: "post",
                url: "removeCrmCustomer",
                //dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                success: function (date, status){
                    alert("删除成功");
                    $("#investorTable").bootstrapTable('refresh');
                }
            });
        }
    }
    
    function checkData(){
    	if(($('#staffNumber1').val()=="")||($('#name1').val()=="")
    			||($('#idNumber1').val()=="")||($('#depName1').val()=="")
    			||($('#positionSalary1').val()=="")||($('#workYears1').val()=="")
    			||($('#isProbation1').val()=="")||($('#coefficeient1').val()=="")
    	){
    		alert("标★为必填项，请重新填写!");
    	}else{
    		saveStaffInfo();
    	}
    		
    }
    
    
    function saveStaffInfo(){
        var param = {
        		staffNumber:$('#staffNumber1').val(),
        		name:$('#name1').val(),
        		idNumber:$('#idNumber1').val(),
        		departmentId:$('#depName1').val(),
        		positionSalary:$('#positionSalary1').val(),
        		workYears:$('#workYears1').val(),
        		probationDateStart:$('#probationDateStart1').val(),
        		formalDateStart:$('#formalDateStart1').val(),
        		isProbation:$('#isProbation1').val(),
        		coefficeient:$('#coefficeient1').val(),
                email:$('#email1').val(),
                remark:$('#remark1').val()
                }
         $.ajax({
             url: 'saveStaffInfo',
             type: 'post',
             contentType: "application/json;charset=UTF-8",
             data: JSON.stringify(param),
             success: function (data,status) {
                 $('#addModal').modal('hide');
                 $('#deptTable').bootstrapTable('refresh');
             }
        });
    }
    
    function queryStaffInfos() {

		$("#staffInfoTable").bootstrapTable('refresh', {
			url : "queryStaffInfos",	
			query : {
				staffNumber:$('#staffNumber').val(),
        		name:$('#name').val(),
        		departmentId:$('#departmentId').val(),
        		positionSalary:$('positionSalary').val(),
        		workYears:$('#workYears').val(),
        		isProbation:$('#isProbation').val(),
                email:$('#email').val(),
                remark:$('#remark').val()

			}
		});
	}
    
  
    
    </script>
</head>
<body>

  <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">渤海期货</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">       
            
            
            <li><a href="#">欢迎：caoxingyu</a></li>
            <li><a href="logout">退出</a></li>
          </ul>
         
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div id="tree"></div>
            
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="sub-header"><a href="toStaffInfo" style="text-decoration: none;">员工信息管理</a></h1>

      <div class="row placeholders">
            
            <!-- 查询条件表单 -->
                      <form id="queryForm" method="post" action="exportCustomer" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">  
                                <label for="depName" class="col-sm-2 col-md-1 control-label">归属部门</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="depName" data-live-Search="true" name="depname">
                                    </select>
                                </div>

                                <label for="staffNumber" class="col-sm-2 col-md-1 control-label">员工编号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="staffNumber" name="investorName">
                                </div>
                                <label for="name" class="col-sm-2 col-md-1 control-label">员工姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="name" name="investorName">
                                </div>
                                
                          </div>
                          <div class="form-group">
                                <label for="isProbation" class="col-sm-2 col-md-1 control-label">员工类型</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="isProbation">
                      	   	 			<option value=""> </option>
                          				<option value="0">正式员工</option>
                          				<option value="1">试用期员工</option>
                          				<option value="2">其他类型</option>
                      				</select>
                                </div>
                                
                                <label for="isLeave" class="col-sm-2 col-md-1 control-label">员工状态</label>
                                <div class="col-sm-10 col-md-2">
                                 <select class="selectpicker form-control" id="isLeave">
                      	   	 			<option value=""> </option>
                          				<option value="0">在职</option>
                          				<option value="1">离职</option>
                      				</select>
                                </div>
                                
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-4 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryByCondition()">
                            </div>
                           <!-- <div class=" col-sm-10 col-md-2 ">
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportStaffInfo()">
                            </div> --> 
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
          </div>




			<h2 class="sub-header">客户基本信息</h2>
           <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus">新建</i>
                </button>
            </div>
            <table id="staffInfoTable"
                   class="table table-striped"
                   data-toggle="table" 
                   data-toolbar="#toolbar"
                   data-show-refresh="true"
                   data-show-toggle="true"
                   data-show-columns="true"
                   data-show-export="true"
                   data-detail-view="false"
                   data-detail-formatter="detailFormatter"
                   data-height="562"
                   data-url="queryStaffInfos"
                   data-pagination="true"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="staffNumber" data-align="center" >员工编号</th>
                    <th data-field="name" data-align="center" >员工姓名</th>
                    <th data-field="departmentName" data-align="center">所属部门</th>
                    <th data-field="positionSalary" data-align="center" >岗位工资</th>
                    <th data-field="workYears" data-align="center" >司龄</th>
                    <th data-field="isProbation" data-align="center" data-formatter="probationFormatter">员工类型</th>  
                    <th data-field="" data-formatter="operationFormatter">操作</th>
                </tr>
                </thead>
            </table>
          </div>  
		 
		  
		  </div>
		  
        </div>
      </div>
      
    </div>
    
    <footer class="footer">
      <div class="container">
        <p class="text-muted">Place sticky footer content here.</p>
      </div>
    </footer>


<!-- 新增员工 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新增员工</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="staffNumber1" class="col-sm-3 control-label">员工编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="staffNumber1" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="name1" class="col-sm-3 control-label">员工姓名</label>
                    <div class="col-sm-8">
                            <input type="text" class="form-control" id="name1" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  
                  <div class="form-group">
                    <label for="certType1" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="certType1" >
                        <option value="0">身份证</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="idNumber1" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="idNumber1" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                
                  <div class="form-group">
                    <label for="depName1" class="col-sm-3 control-label">所属部门</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="depName1" data-live-Search="true" name="depname">
                         <!-- <option value="0">A部</option>
                          <option value="1">B部</option>
                          <option value="2">C部</option>
                          <option value="3">D部</option>
                          <option value="4">E部</option> --> 
                      </select>
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="positionSalary1" class="col-sm-3 control-label">岗位工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="positionSalary1" placeholder="">         
                    </div>
                    <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="workYears1" class="col-sm-3 control-label">司龄</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears1" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="probationDateStart1" class="col-sm-3 control-label">试用期起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart1" placeholder="">
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="formalDateStart1" class="col-sm-3 control-label">正式工作起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="isProbation1" class="col-sm-3 control-label">员工属性</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isProbation1" onchange="changeCoefficeient()">
                          <option value=""> </option>
                          <option value="0">正式员工</option>
                          <option value="1">试用期员工</option>
                          <option value="2">其他类型</option>
                      </select>
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                 
                  <div class="form-group">
                    <label for="coefficeient1" class="col-sm-3 control-label">工资系数</label>
                    <div class="col-sm-8">
                       <input type="text" class="form-control" id="coefficeient1"  placeholder="">          
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  <hr>
 
                  <div class="form-group">
                    <label for="email1" class="col-sm-3 control-label">电子邮箱</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="email1" placeholder="">
                    </div>
                  </div>
                  
               
                  <div class="form-group">
                    <label for="remark1" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark1"></textarea>
                    </div>
                  </div>            
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="checkData()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改员工信息 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">修改员工信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="staffNumber2" class="col-sm-3 control-label">员工编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="staffNumber2" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="name2" class="col-sm-3 control-label">员工姓名</label>
                    <div class="col-sm-8">
                            <input type="text" class="form-control" id="name2" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  
                  <div class="form-group">
                    <label for="certType2" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="certType2" >
                        <option value="0">身份证</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="idNumber2" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="idNumber2" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                
                  <div class="form-group">
                    <label for="depName2" class="col-sm-3 control-label">所属部门</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="depName2" data-live-Search="true" name="depname">
                         <!-- <option value="0">A部</option>
                          <option value="1">B部</option>
                          <option value="2">C部</option>
                          <option value="3">D部</option>
                          <option value="4">E部</option> --> 
                      </select>
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="positionSalary2" class="col-sm-3 control-label">岗位工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="positionSalary2" placeholder="">         
                    </div>
                    <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="workYears2" class="col-sm-3 control-label">司龄</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears2" placeholder="">
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="probationDateStart2" class="col-sm-3 control-label">试用期起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart2" placeholder="">
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="formalDateStart2" class="col-sm-3 control-label">正式工作起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart2" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="isProbation2" class="col-sm-3 control-label">员工属性</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isProbation2" onchange="changeCoefficeient()">
                          <option value=""> </option>
                          <option value="0">正式员工</option>
                          <option value="1">试用期员工</option>
                          <option value="2">其他类型</option>
                      </select>
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                 
                  <div class="form-group">
                    <label for="coefficeient2" class="col-sm-3 control-label">工资系数</label>
                    <div class="col-sm-8">
                       <input type="text" class="form-control" id="coefficeient2"  placeholder="">          
                    </div>
                      <div>
                    		<p style="padding-top: 5px;">★</p>
                    </div>
                  </div>
                  <hr>
 
                  <div class="form-group">
                    <label for="email2" class="col-sm-3 control-label">电子邮箱</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="email2" placeholder="">
                    </div>
                  </div>
                  
               
                  <div class="form-group">
                    <label for="remark2" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark2"></textarea>
                    </div>
                  </div>            
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="checkData()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
</body>
</html>