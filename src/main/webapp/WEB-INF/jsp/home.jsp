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
   
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    
    <!-- bootstrap-select -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    
    
    <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
   
    <script type="text/javascript" src="resources/js/home.js?<%=Math.random()%>"></script>
    
    <script type="text/javascript">
    var userLocked = ${sessionScope.user["locked"]}
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
            
            
            <li  class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">欢迎：${sessionScope.user.fullName}<strong class="caret"></strong></a>
            <ul class="dropdown-menu">
            <li>
				 <a href="tochangePw">修改密码</a>
			</li>
			 <li><a href="logout">退出</a></li>
			</ul>
            </li>
            
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


<div class="panel-group" id="panel-642522">
				<div class="panel panel-default">
					<div class="panel-heading">
						 <a class="panel-title" data-toggle="collapse" data-parent="#panel-642522" href="#collapse">审核信息状态详情</a>
					</div>
					<div id="collapse" class="accordion-body collapse">
						<div class="panel-body">
				
      <div class="row placeholders">
            
            <!-- 查询条件表单 -->
                      <form id="queryForm2" method="post" action="exportCustomer" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">  
                                <label for="depName3" class="col-sm-2 col-md-1 control-label">归属部门</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="depName3" data-live-Search="true" name="depname3">
                                    </select>
                                </div>

                                <label for="staffNumber3" class="col-sm-2 col-md-1 control-label">员工编号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="staffNumber3" name="investorName">
                                </div>
                                <label for="name3" class="col-sm-2 col-md-1 control-label">员工姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="name3" name="investorName">
                                </div>
                          </div>
                          <div class="form-group">
                               
                                 <label for="tage" class="col-sm-2 col-md-1 control-label">审核状态</label>
                                <div class="col-sm-10 col-md-2">
                                 <select class="selectpicker form-control" id="tage">
                      	   	 			<option value=""> </option>
                          				<option value="0">等待审核</option>
                          				<option value="1">通过审核</option>
                          				<option value="2">拒绝审核</option>
                      				</select>
                                </div>
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-4 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCheckMessageByCondition()">
                            </div>
                           <!-- <div class=" col-sm-10 col-md-2 ">
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportStaffInfo()">
                            </div> --> 
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
          </div>
				<table id="submittercheckMessageTable"
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
                   data-url="queryCheckMessagesBySubmitter"
                   data-pagination="true"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="id" data-align="center" data-formatter="idFormatter">序号</th>
                    <th data-field="staffNumber" data-align="center" data-sortable="true">员工编号</th>
                    <th data-field="name" data-align="center" >员工姓名</th>
                    <th data-field="departmentName" data-align="center">所属部门</th>
                    <th data-field="positionSalary" data-align="center" >岗位工资</th>
                    <th data-field="skillSalary" data-align="center">技能工资</th>
                    <th data-field="workYears" data-align="center" >司龄工资</th>
                    <th data-field="isProbation" data-align="center" data-formatter="probationFormatter">员工类型</th>
                    <th data-field="isLeave" data-align="center" data-formatter="LeaveFormatter">员工状态</th>
                    <th data-field="submitType" data-align="center" data-formatter="submitTypeFormatter">审批类型</th>
                    <th data-field="submitter" data-align="center">提交人</th>
                    <th data-field="checker" data-align="center">审批人</th>
                    <th data-field="approvalOpinion" data-align="center">审核意见</th>
                     <th data-field="tage" data-align="center" data-formatter="tageFormatter">审核状态</th>
                </tr>
                </thead>
            </table>
						</div>
					</div>
				</div>
			</div>


			<h2 class="sub-header"></h2>
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
                    <th data-field="skillSalary" data-align="center">技能工资</th>
                    <th data-field="workYears" data-align="center" >司龄工资</th>
                    <th data-field="isProbation" data-align="center" data-formatter="probationFormatter">员工类型</th>
                    <th data-field="isLeave" data-align="center" data-formatter="LeaveFormatter">员工状态</th>
                    <th data-field="submitStatus" data-align="center" data-formatter="statusFormatter">是否已提交申请</th>
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
                  
                  <hr>
                  
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
                    <label for="skillSalary1" class="col-sm-3 control-label">技能工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="skillSalary1" placeholder="">         
                    </div> 
                  </div>
                  
                   <div class="form-group">
                    <label for="workYears1" class="col-sm-3 control-label">司龄工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears1" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="probationDateStart1" class="col-sm-3 control-label">试用期起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart1" placeholder="">
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="formalDateStart1" class="col-sm-3 control-label">转正日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart1" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="isProbation1" class="col-sm-3 control-label">员工属性</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isProbation1" onchange="changeCoefficeient1()">
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
            <button type="button" class="btn btn-primary" onclick="checkData()">提交审核</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改员工信息 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                      <input type="text" class="form-control" id="staffNumber2" placeholder=""  disabled>
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
                  
                  <hr>
                  
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
                    <label for="skillSalary2" class="col-sm-3 control-label">技能工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="skillSalary2" placeholder="">         
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="workYears2" class="col-sm-3 control-label">司龄工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears2" placeholder="">
                    </div> 
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="probationDateStart2" class="col-sm-3 control-label">试用期起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart2" placeholder="">
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="formalDateStart2" class="col-sm-3 control-label">转正日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart2" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="isProbation2" class="col-sm-3 control-label">员工属性</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isProbation2" onchange="changeCoefficeient2()">
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
                    <label for="isLeave2" class="col-sm-3 control-label">员工状态</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isLeave2">
                          <option value=""> </option>
                          <option value="0">在职</option>
                          <option value="1">离职</option>
                      </select>
                    </div>
                 </div>
                  
                   <div class="form-group">
                    <label for="leaveDate2" class="col-sm-3 control-label">离职日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="leaveDate2" placeholder="">
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
            <button type="button" class="btn btn-primary" onclick="checkData2()">提交审核</button>
          </div>
        </div>
      </div>
    </div>
    
</body>
</html>