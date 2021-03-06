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
   
    <script type="text/javascript" src="resources/js/check.js?<%=Math.random()%>"></script>
    <script type="text/javascript">
    var userLocked = ${sessionScope.user["locked"]}
    </script>
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
          <h1 class="sub-header"><a href="toStaffInfo" style="text-decoration: none;">申报信息审核</a></h1>

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
                               
                                 <label for="tage" class="col-sm-2 col-md-1 control-label">审核状态</label>
                                <div class="col-sm-10 col-md-2">
                                 <select class="selectpicker form-control" id="tage">
                      	   	 			<option value=""> </option>
                          				<option value="0">等待审核</option>
                          				<option value="1">通过审核</option>
                          				<option value="2">拒绝审核</option>
                      				</select>
                                </div>
                                
                                <label for="submitType" class="col-sm-2 col-md-1 control-label">审核类型</label>
                                <div class="col-sm-10 col-md-2">
                                 <select class="selectpicker form-control" id="submitType">
                      	   	 			<option value=""> </option>
                          				<option value="0">新建</option>
                          				<option value="1">修改</option>
                          				<option value="2">离职</option>
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




			<h2 class="sub-header">申报信息</h2>
           <div class="table-responsive">
            <div id="toolbar" >
                <button type="button" class="btn btn-info" onclick="checkSome()">批量审核 </button>
                &nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">红色代表修改后的最新值,黑色代表原值，如果没有黑色值则代表是新添加的值</span>          
                
                
            </div>
            <table id="checkMessageTable"
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
                   data-url="queryCheckMessages"
                   data-pagination="true"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true" data-formatter="checkFormatter" ></th> 
                    <th data-field="id" data-align="center" data-formatter="idFormatter">序号</th>
                    <th data-field="SUBMIT_TIME" data-align="center" data-sortable="true">提交时间</th>
                    <th data-field="STAFF_NUMBER" data-align="center" >员工编号</th>
                    <th data-field="NAME" data-align="center" data-formatter="compareName" id="name">员工姓名</th>
                    <th data-field="DEP_NAME" data-align="center" data-formatter="compareDep">所属部门</th>
                    <th data-field="POSITION_SALARY" data-align="center" data-formatter="comparePositionSalary">岗位工资</th>
                    <th data-field="PROBATION_SALARY" data-align="center" data-formatter="compareProbationSalary">试用期工资</th>
                    <th data-field="SKILL_SALARY" data-align="center" data-formatter="compareSkillSalary">技能工资</th>
                    <th data-field="WORK_YEARS" data-align="center" data-formatter="compareWorkYears">司龄工资</th>
                    <th data-field="PROBATION_DATE_START" data-align="center" data-formatter="compareProbationDateStart">试用期起始日期</th>
                    <th data-field="FORMAL_DATE_START" data-align="center" data-formatter="compareFormalDateStart">&nbsp;&nbsp;&nbsp;转正日期&nbsp;&nbsp;&nbsp;</th>
                    <th data-field="IS_PROBATION" data-align="center" data-formatter="probationFormatter">员工类型</th>
                    <!-- <th data-field="COEFFICEIENT" data-align="center" data-formatter="compareCoefficeient">工资系数</th> -->
                    <th data-field="IS_LEAVE" data-align="center" data-formatter="LeaveFormatter">员工状态</th>
                    <th data-field="LEAVE_DATE" data-align="center" data-formatter="compareLeaveDate">离职日期</th>
                    <th data-field="EMAIL" data-align="center" data-formatter="compareEmail">邮箱</th>
                    <th data-field="REMARK" data-align="center" data-formatter="compareRemark">备注</th>
                    <th data-field="SUBMIT_TYPE" data-align="center" data-formatter="submitTypeFormatter">审批类型</th>
                    <th data-field="SUBMITTER" data-align="center" >申请审核信息用户</th>
                    <th data-field="" data-formatter="operationFormatter">审批操作</th>
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
        <p class="text-muted text-center">渤海期货 版权所有</p>
      </div>
    </footer>
 
    <!-- 查看详情 -->
    <div class="modal fade" id="allMessageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">待审批详细信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                   <div class="form-group">
                    <label for="id2" class="col-sm-3 control-label">序号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="id2" placeholder=""  disabled>
                    </div>        
                  </div>
                  
                  <div class="form-group">
                    <label for="staffNumber2" class="col-sm-3 control-label">员工编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="staffNumber2" placeholder=""  disabled>
                    </div>        
                  </div>
                  
                  <div class="form-group">
                    <label for="name2" class="col-sm-3 control-label">员工姓名</label>
                    <div class="col-sm-8">
                            <input type="text" class="form-control" id="name2" placeholder="" disabled>
                    </div>
                  </div>
                  
                  
                
                  <div class="form-group">
                    <label for="depName2" class="col-sm-3 control-label">所属部门</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="depName2" data-live-Search="true" name="depname" disabled>
                      </select>
                    </div>
                  </div>
                  
                  <hr>
                  
                 
                  
                  <div class="form-group">
                    <label for="skillSalary2" class="col-sm-3 control-label">技能工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="skillSalary2" placeholder="" disabled>         
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="workYears2" class="col-sm-3 control-label">司龄工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears2" placeholder="" disabled>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="probationDateStart2" class="col-sm-3 control-label">试用期起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart2" placeholder="" disabled>
                    </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="formalDateStart2" class="col-sm-3 control-label">转正日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart2" placeholder="" disabled>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="isProbation2" class="col-sm-3 control-label">员工类型</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isProbation2" onchange="changeCoefficeient2()" disabled>
                          <option value=""> </option>
                          <option value="0">正式员工</option>
                          <option value="1">试用期员工</option>
                          <option value="2">其他类型</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="positionSalary2" class="col-sm-3 control-label">岗位工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="positionSalary2" placeholder="" disabled>         
                    </div>    
                  </div>
                   <div class="form-group">
                    <label for="probationSalary2" class="col-sm-3 control-label">试用期工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="probationSalary2" placeholder="" disabled>         
                    </div>    
                  </div>
                 <!--  <div class="form-group">
                    <label for="coefficeient2" class="col-sm-3 control-label">工资系数</label>
                    <div class="col-sm-8">
                       <input type="text" class="form-control" id="coefficeient2"  placeholder="" disabled>          
                    </div>
                  </div> -->
				<hr>
                   <div class="form-group">
                    <label for="isLeave2" class="col-sm-3 control-label">员工状态</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isLeave2" disabled>
                          <option value=""> </option>
                          <option value="0">在职</option>
                          <option value="1">离职</option>
                      </select>
                    </div>
                 </div>     
                  <hr>
 
                  <div class="form-group">
                    <label for="email2" class="col-sm-3 control-label">电子邮箱</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="email2" placeholder="" disabled>
                    </div>
                  </div>
                  
               
                  <div class="form-group">      
                    <label for="remark2" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark2" disabled></textarea>
                    </div>
                  </div>
                  
                   <div class="form-group">      
                    <label for="approvalOpinion2" class="col-sm-3 control-label">审批意见</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="approvalOpinion2"></textarea>
                    </div>
                  </div>                        
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-lg" data-dismiss="modal" onclick="refuse()">拒绝</button>
            <button type="button" class="btn btn-success btn-lg" onclick="agree()">同意</button>
          </div>
        </div>
      </div>
    </div>


	<div class="modal fade" id="checkListModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">审批意见</h4>
				</div>
				<div class="modal-body">
					<textarea id="checkOpinion" rows="4" cols="70"></textarea>
				</div>
				 <div class="modal-footer">
            <button type="button" class="btn btn-default " data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-default " onclick="agreeList()">确定</button>
          </div>
			</div>
		</div>
	</div>
</body>
</html>