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
   
    <script type="text/javascript" src="resources/js/check.js"></script>
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
            
            
            <li><a href="#">欢迎：${sessionScope.user.fullName}</a></li>
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
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="id" data-align="center" data-formatter="idFormatter">序号</th>
                    <th data-field="staffNumber" data-align="center" >员工编号</th>
                    <th data-field="name" data-align="center" >员工姓名</th>
                    <th data-field="departmentName" data-align="center">所属部门</th>
                    <th data-field="positionSalary" data-align="center" >岗位工资</th>
                    <th data-field="skillSalary" data-align="center">技能工资</th>
                    <th data-field="workYears" data-align="center" >司龄工资</th>
                    <th data-field="isProbation" data-align="center" data-formatter="probationFormatter">员工类型</th>
                    <th data-field="isLeave" data-align="center" data-formatter="LeaveFormatter">员工状态</th>
                    <th data-field="submitType" data-align="center" data-formatter="submitTypeFormatter">审批类型</th>
                    <th data-field="submitter" data-align="center">申请审核信息用户</th>
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
        <p class="text-muted">Place sticky footer content here.</p>
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
                    <label for="positionSalary2" class="col-sm-3 control-label">岗位工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="positionSalary2" placeholder="" disabled>         
                    </div>    
                  </div>
                  
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
                    <label for="formalDateStart2" class="col-sm-3 control-label">正式工作起始日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="formalDateStart2" placeholder="" disabled>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="isProbation2" class="col-sm-3 control-label">员工属性</label>
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
                    <label for="coefficeient2" class="col-sm-3 control-label">工资系数</label>
                    <div class="col-sm-8">
                       <input type="text" class="form-control" id="coefficeient2"  placeholder="" disabled>          
                    </div>
                  </div>
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
    
</body>
</html>