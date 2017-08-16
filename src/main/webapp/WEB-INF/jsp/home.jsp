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
	            }
	        });
	    });
    });
    
    
    
    function saveStaffInfo(){
        var param = {
        		staffNumber:$('#staffNumber').val(),
        		name:$('#name').val(),
        		idNumber:$('#idNumber').val(),
        		departmentId:$('#depName').val(),
        		positionSalary:$('positionSalary').val(),
        		workYears:$('#workYears').val(),
        		probationDateStart:$('#probationDateStart').val(),
        		formalDateStart:$('#formalDateStart').val(),
        		isProbation:$('#isProbation').val(),
                email:$('#email').val(),
                remark:$('#remark').val()
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
        		probationDateStart:$('#probationDateStart').val(),
        		formalDateStart:$('#formalDateStart').val(),
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
                  <!--  <th data-field="establishDate" data-align="center" >个人所得税</th>-->  
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
                    <label for="staffNumber" class="col-sm-3 control-label">员工编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="staffNumber" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="name" class="col-sm-3 control-label">员工姓名</label>
                    <div class="col-sm-8">
                            <input type="text" class="form-control" id="name" placeholder="">
                    </div>
                  </div>
                  
                  
                  <div class="form-group">
                    <label for="certType" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="certType" >
                        <option value="0">身份证</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="idNumber" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="idNumber" placeholder="">
                    </div>
                  </div>
                
                  <div class="form-group">
                    <label for="depName" class="col-sm-3 control-label">所属部门</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="depName" data-live-Search="true">
                         <!-- <option value="0">A部</option>
                          <option value="1">B部</option>
                          <option value="2">C部</option>
                          <option value="3">D部</option>
                          <option value="4">E部</option> --> 
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="positionSalary" class="col-sm-3 control-label">岗位工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="positionSalary" placeholder="">
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="workYears" class="col-sm-3 control-label">司龄</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="probationDateStart" class="col-sm-3 control-label">试用期起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart" placeholder="">
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="formalDateStart" class="col-sm-3 control-label">正式工作起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="isProbation" class="col-sm-3 control-label">员工属性</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isProbation" >
                          <option value="0">正式员工</option>
                          <option value="1">试用期员工</option>
                      </select>
                    </div>
                  </div>
                  <hr>
                  
                  
                  <div class="form-group">
                    <label for="email" class="col-sm-3 control-label">电子邮箱</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="email" placeholder="">
                    </div>
                  </div>
                  
               
                  <div class="form-group">
                    <label for="remark" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark"></textarea>
                    </div>
                  </div>
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="saveStaffInfo()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
</body>
</html>