<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工工资管理</title>
<!-- jquery -->
<script type="text/javascript"
	src="resources/jquery/jquery-3.1.1.min.js"></script>
<!-- Bootstrap -->
<script type="text/javascript"
	src="resources/bootstrap/js/bootstrap.min.js"></script>
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/dashboard.css" rel="stylesheet">
<link href="resources/css/sticky-footer.css" rel="stylesheet">
<link href="resources/favicon.ico" rel="shortcut icon" />
<script type="text/javascript"
	src="resources/tree/bootstrap-treeview.min.js"></script>
<!-- 文件上传 -->
<link href="resources/fileInput/css/fileinput.min.css" media="all"
	rel="stylesheet" type="text/css" />
<!-- canvas-to-blob.min.js is only needed if you wish to resize images before upload.
     This must be loaded before fileinput.min.js -->
<script src="resources/fileInput/js/plugins/canvas-to-blob.min.js"
	type="text/javascript"></script>
<!-- sortable.min.js is only needed if you wish to sort / rearrange files in initial preview.
         This must be loaded before fileinput.min.js -->
<script src="resources/fileInput/js/plugins/sortable.min.js"
	type="text/javascript"></script>
<!-- purify.min.js is only needed if you wish to purify HTML content in your preview for HTML files.
         This must be loaded before fileinput.min.js -->
<script src="resources/fileInput/js/plugins/purify.min.js"
	type="text/javascript"></script>
<!-- the main fileinput plugin file -->
<script src="resources/fileInput/js/fileinput.min.js"></script>
<!-- table插件 -->
<script src="resources/bootstrap-table/bootstrap-table.js"></script>
<!-- put your locale files after bootstrap-table.js -->
<script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- 文件上传插件fileInput -->
<script src="resources/fileInput/themes/fa/theme.js"></script>
<!-- optionally if you need translation for your language then include 
        locale file as mentioned below -->
<script src="resources/fileInput/js/locales/zh.js"></script>
<script type="text/javascript" src="resources/js/salary.js"></script>
<!-- table -->
<link rel="stylesheet"
	href="resources/bootstrap-table/bootstrap-table.css">
<!-- bootstrap-select -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
<!-- datepicker -->
<script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script
	src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<!-- datepicker -->
<link
	href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css"
	rel="stylesheet">
<script type="text/javascript">
    var userLocked = ${sessionScope.user["locked"]}
</script>
	
</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
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

		<div class="col-sm-3 col-md-2 sidebar">
			<div id="tree"></div>

		</div>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="sub-header">
				<a href="toHome" style="text-decoration: none;">员工工资管理</a>
			</h1>

			<div class="row">
				<!-- 	<label for="reportMonth" class="col-sm-2 col-md-1 control-label" ></label>       -->
				<div class="col-sm-9 col-md-4">
					<label class="control-label">请选择社保公积金表上传</label> <input
						id="finput1" type="file" class="file" multiple>
					<script type="text/javascript">
                    $("#finput1").fileinput({
                        language: 'zh',
                        uploadAsync: true,
                        uploadUrl: "salaryFileUpload", //异步上传地址
                        maxFileCount: 10,//最大上传文件数限制
                        showCaption: true,//是否显示标题
                        showUpload: true,//是否显示上传按钮
                        showPreview:false,//默认true

                        allowedFileExtensions: ["xls", "xlsx"]  //接收的文件后缀 
                        //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>" //选择文件后缩略图
                    }); 
                 </script>
				</div>
				<div class="col-sm-9 col-md-4">
					<label class="control-label">请选择营销人员提成表上传</label> <input
						id="finput2" type="file" class="file" multiple>
					<script type="text/javascript">
                    $("#finput2").fileinput({
                        language: 'zh',
                        uploadAsync: true,
                        uploadUrl: "CommissionFileUpload", //异步上传地址
                        maxFileCount: 10,//最大上传文件数限制
                        showCaption: true,//是否显示标题
                        showUpload: true,//是否显示上传按钮
                        showPreview:false,//默认true
                        allowedFileExtensions: ["xls", "xlsx"]  //接收的文件后缀 
                        
                        //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>" //选择文件后缩略图
                    }); 
                 </script>
				</div>

			</div>

			<form class="form-horizontal" style="margin-top: 30px"
				id="customerForm">
				<div class="form-group" style="margin-left: -85px;">

					<label for="queryMonth" class="col-sm-2 col-md-1 control-label">月份</label>
					<div class="col-sm-4 col-md-2">
						<input type="text" class="form-control month" id="queryMonth">
					</div>

					<label for="queryName" class="col-sm-2 col-md-1 control-label">员工姓名</label>
					<div class="col-sm-4 col-md-2">
						<input type="text" class="form-control" id="queryName"
							placeholder="员工姓名">
					</div>

					<label for="queryNum" class="col-sm-2 col-md-1 control-label">员工编号</label>
					<div class="col-sm-4 col-md-2">
						<input type="text" class="form-control" id="queryNum"
							placeholder="员工编号"> 
					</div>
				</div>
				
				<div class="form-group" style="margin-left: -85px;">
					<label for="queryDepName"
							class="col-sm-2 col-md-1 control-label">部门</label>
					<div class="col-sm-4 col-md-2">
						<select class="selectpicker form-control " id="queryDepName"
							data-live-Search="true" >

						</select>
					</div>

					<div class="col-sm-10 col-md-2 col-md-offset-1 ">
						<input class="btn btn-default col-xs-7" type="button" value="查询"
							onclick="querySalary()">
					</div>


				</div>

			</form>

			<div class="row">
				<form class="form-horizontal" style="margin-top: 30px"
					id="customerForm2">
					<div class="col-sm-10 col-md-2  ">
						<input class="btn btn-default  col-xs-7" type="button"
							value="生成工资" onclick="calculateSalary()">
					</div>

					<div class="col-sm-10 col-md-2  ">
						<input class="btn btn-default  col-xs-8" type="button"
							value="导出工资明细表" onclick="exportSalary()">
					</div>
					<div class="col-sm-10 col-md-2 ">
						<input class="btn btn-default col-xs-7" type="button"
							value="发送邮件"	onclick="openSendMails()">
					</div>
				</form>
			</div>
			
			<table id="salaryTable" 
			    class="table table-striped"
				data-toggle="table" 
				data-toolbar="#toolbar"
				data-show-refresh="true"
				data-show-toggle="true" 
				data-show-columns="true"
				data-show-export="true" 
				data-detail-view="false"
				data-detail-formatter="detailFormatter" 
				data-height="566"
				data-url="querySalaryByParams" 
				data-pagination="true"
				data-side-pagination="server" 
				data-query-params-type="pageNum"
				data-query-params="queryParams" 
				data-method="post"
				data-page-list="[5, 10, 20, 50]" 
				data-search="true"
				data-show-footer="true">
				<thead>
					<tr>
					    <th data-field="payDate" data-align="center"  data-footer-formatter="合计">日期</th>
						<th data-field="staffNumber" data-align="center" >员工编号</th>
						<th data-field="name" data-align="center">员工姓名</th>
						<th data-field="depName" data-align="center">部门</th>
						<th data-field="postionsSalary" data-align="center" data-footer-formatter="tbfooter0" data-formatter="numberFormate">岗位工资</th>
						<th data-field="achiementSalary" data-align="center" data-footer-formatter="tbfooter1" data-formatter="numberFormate">绩效工资</th>
						<th data-field="grossSalary" data-align="center" data-footer-formatter="tbfooter2" data-formatter="numberFormate">应发工资</th>
						<th data-field="warmSubsidy" data-align="center" data-footer-formatter="tbfooter3" data-formatter="numberFormate">取暖补贴</th>
						<th data-field="pensionPersonal" data-align="center" data-footer-formatter="tbfooter4" data-formatter="numberFormate">养老保险</th>
						<th data-field="unemploymentPersonal" data-align="center" data-footer-formatter="tbfooter5" data-formatter="numberFormate">失业保险</th>
						<th data-field="medicalPersonal" data-align="center" data-footer-formatter="tbfooter6" data-formatter="numberFormate">医疗保险</th>
						<th data-field="personalTotal" data-align="center" data-footer-formatter="tbfooter7" data-formatter="numberFormate">个人社保缴费合计</th>
						<th data-field="housePersonalTotal" data-align="center" data-footer-formatter="tbfooter8" data-formatter="numberFormate">个人公积金缴费合计</th>
						 <th data-field="incomeTax" data-align="center"  data-footer-formatter="tbfooter9" data-formatter="numberFormate">个人所得税</th>
						<th data-field="salaryOther" data-align="center"
							data-footer-formatter="tbfooter10" > &nbsp;其他款项 &nbsp; &nbsp;</th>
						<th data-field="actualSalary" data-align="right"
							data-formatter="salaryFormatter" data-footer-formatter="tbfooter11" >实发工资 &nbsp; &nbsp; &nbsp; </th>
					    <th data-align="center" data-formatter="operateFormate">操作</th>
					</tr>
				</thead>
			</table>
		</div>



	</div>

	</div>


	</div>
	<!-- 修改其他款项 -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">修改(正数表示增加款项，负数表示扣款)</h4>
					
				</div>	
				<div class="modal-body">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="staffName" class="col-sm-4 control-label">姓名</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="staffName"
										placeholder="" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="departMentName" class="col-sm-4 control-label">部门</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="departMentName"
										placeholder="" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="month" class="col-sm-4 control-label">月份</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="month"
										placeholder="" readonly="readonly">
								</div>
							</div>
							<hr/>
							<!--  -->
							<div class="form-group">
								<label  class="col-sm-4 control-label">个人缴纳养老保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="pensionPersonal"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">个人缴纳医疗保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="medicalPersonal"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">个人缴纳失业保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="unemploymentPersonal"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">个人备用条目1</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="personalReserve1"
										>
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">个人备用条目2</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="personalReserve2"
										>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公司缴纳养老保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="pensionCompany"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公司缴纳医疗保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="medicalCompany"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">公司缴纳失业保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="unemploymentCompany"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公司缴纳工伤保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="injuryCompany"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公司缴纳生育保险</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="birthCompany"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公司备用条目1</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="companyReserve1"
										>
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公司备用条目2</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="companyReserve2"
										>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公积金个人缴纳基本</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="houseBasePersonal"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-4 control-label">公积金个人缴纳补充</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="houseSupplyPersonal"
										placeholder="">
								</div>
							</div>
							
							<div class="form-group">
								<label for="mediatorName1" class="col-sm-4 control-label">公积金公司缴纳基本</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="houseBaseCompany"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label for="mediatorName1" class="col-sm-4 control-label">公积金公司缴纳补充</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="houseSupplyCompany"
										placeholder="">
								</div>
							</div>
							
							<hr/>
							<div class="form-group">
								<label for="mediatorName1" class="col-sm-4 control-label">其他款项</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="otherSalary"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label for="remark" class="col-sm-4 control-label">备注</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="remark" 
										placeholder="">
								</div>
							</div>
						</form>
				</div>
				<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary"
							onclick="updateSalary()">修改</button>
				</div>
			</div>

		</div>
	</div>

    <!-- 计算工资月份和部门模态框 -->
	<div class="modal fade" id="selectModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">请选择要计算的部门和月份</h4>
				</div>	
					
				<div class="modal-body">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="selectMonth" class="col-sm-3 control-label">月份</label>
								<div class="col-sm-8 col-md-4">
						             <input type="text" class="form-control month" id="selectMonth">
					             </div>
							</div>
							<div class="form-group">
								<label for="selectDepName" class="col-sm-3 control-label">部门</label>
								<div class="col-sm-8 col-md-4">
									<select class="selectpicker form-control " id="selectDepName"
										data-live-Search="true" >

									</select>
								</div>
							</div>


						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="compSalary()">确定</button>
				</div>
			</div>

			
		</div>
	</div>
<!-- 	 <!-- 导出工资 月份和部门模态框 -->
	<div class="modal fade" id="exportModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel3">请选择要导出的部门和月份</h4>
				</div>	
					
				<div class="modal-body">
						<form class="form-horizontal" role="form" action="exportSalary" id="exportForm">
							<div class="form-group">
								<label for="exportMonth" class="col-sm-3 control-label">月份</label>
								<div class="col-sm-8 col-md-4">
						             <input type="text" class="form-control month" name="payDate" id="payDate">
					             </div>
							</div>
							<div class="form-group">
								<label for="exportDepName" class="col-sm-3 control-label">部门</label>
								<div class="col-sm-8 col-md-4">
									<select class="selectpicker form-control " name="depNum" id="depNum"
										data-live-Search="true" >

									</select>
								</div>
							</div>


						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="exportDetail()">确定</button>
				</div>
			</div>

			
		</div>
	</div>// -->
	
	        <!-- 查看详情模态框 -->
    <div class="modal fade" id="deailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">工资信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
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
                    <label for="depName2" class="col-sm-3 control-label">员工姓名</label>
                    <div class="col-sm-8">
                            <input type="text" class="form-control" id="depName2" placeholder="" disabled>
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
                    <label for="achiementSalary2" class="col-sm-3 control-label">绩效工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="achiementSalary2" placeholder="" disabled>         
                    </div>
                  </div>
                  
                  
                   <div class="form-group">
                    <label for="workYears2" class="col-sm-3 control-label">司龄工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="workYears2" placeholder="" disabled>
                    </div>
                  </div>
               
               		<div class="form-group">
                    <label for="grossSalary2" class="col-sm-3 control-label">应发工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="grossSalary2" placeholder="" disabled>
                    </div>
                  </div>
                  <hr>
                  
                  <div class="form-group">
                    <label for="warmSubsidy2" class="col-sm-3 control-label">取暖补贴</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="warmSubsidy2" placeholder="" disabled>         
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="housePersonalTotal2" class="col-sm-3 control-label">住房公积金</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="housePersonalTotal2" placeholder="" disabled>         
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="pensionPersonal2" class="col-sm-3 control-label">养老保险</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="pensionPersonal2" placeholder="" disabled>         
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="unemploymentPersonal2" class="col-sm-3 control-label">失业保险</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="unemploymentPersonal2" placeholder="" disabled>         
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="medicalPersonal2" class="col-sm-3 control-label">医疗保险</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="medicalPersonal2" placeholder="" disabled>         
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="taxBase2" class="col-sm-3 control-label">计税基数</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="taxBase2" placeholder="" disabled>         
                    </div>
                  </div> 
                  <div class="form-group">
                    <label for="incomeTax2" class="col-sm-3 control-label">个人所得税</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="incomeTax2" placeholder="" disabled>         
                    </div>
                  </div> 
                  
                   <div class="form-group">
                    <label for="actualSalary2" class="col-sm-3 control-label">实发工资</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="actualSalary2" placeholder="" disabled>         
                    </div>
                  </div> 
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
	
	<!-- 群发工资日期选择模态框   -->
	<div class="modal fade" id="sendMailsModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">请选择</h4>
				</div>	
					
				<div class="modal-body">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="selectSalaryMonth" class="col-sm-3 control-label">发送工资月份</label>
								<div class="col-sm-8 col-md-4">
						             <input type="text" class="form-control month" id="selectSalaryMonth">
					             </div>
							</div>
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="sendMails()">发送</button>
				</div>
			</div>

			
		</div>
	</div>
	



	<footer class="footer">
	<div class="container">
		<p class="text-muted">Place sticky footer content here.</p>
	</div>
	</footer>

</body>
</html>