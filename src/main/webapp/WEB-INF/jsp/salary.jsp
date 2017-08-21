<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工工资管理</title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">   
    <link href="resources/favicon.ico" rel="shortcut icon"/>
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <!-- 文件上传 -->
    <link href="resources/fileInput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <!-- canvas-to-blob.min.js is only needed if you wish to resize images before upload.
     This must be loaded before fileinput.min.js -->
    <script src="resources/fileInput/js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
    <!-- sortable.min.js is only needed if you wish to sort / rearrange files in initial preview.
         This must be loaded before fileinput.min.js -->
    <script src="resources/fileInput/js/plugins/sortable.min.js" type="text/javascript"></script>
    <!-- purify.min.js is only needed if you wish to purify HTML content in your preview for HTML files.
         This must be loaded before fileinput.min.js -->
    <script src="resources/fileInput/js/plugins/purify.min.js" type="text/javascript"></script>
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
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
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
   
        <div class="col-sm-3 col-md-2 sidebar">
            <div id="tree"></div>
            
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="sub-header"><a href="toHome" style="text-decoration: none;">员工工资管理</a></h1>

           <div class="row">
            <div class="col-sm-9 col-md-4">
                <label class="control-label">请选择社保公积金表上传</label>
                 <input id="finput1" type="file" class="file" multiple >  
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
                <label class="control-label">请选择营销人员提成表上传</label>
                 <input id="finput2" type="file" class="file" multiple >  
                    <script type="text/javascript">
                    $("#finput2").fileinput({
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
             
           </div>
           <div class="row" >
                  <form class="form-horizontal" style="margin-top: 30px" id="customerForm">
                        <div class="form-group col-sm-18 col-md-8">
                        
                              <!--  <label for="reportMonth" class="col-sm-2 col-md-1 control-label">统计月份</label> -->
                               <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="reportMonth" placeholder="月份">
                               </div>
                            
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="mediatorNo" placeholder="员工姓名">
                              </div>
                              
                           
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="mediatorName" placeholder="员工编号">
                              </div>
                           
                               <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="depCode" name="depName" data-live-Search="true" placeholder="部门">
                                    </select>
                               </div>                     
                                                 
                              <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                  <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryMediator()">
                              </div>
                              
                             
                        </div>
                          
                   </form>
             </div>
              
             <div class="row">
                   <form class="form-horizontal" style="margin-top: 30px" id="customerForm2">
                       <div class="col-sm-10 col-md-2  ">
                            <input class="btn btn-default  col-xs-7" type="button" value="计算工资" onclick="queryMediator()">
                       </div>
                              
                       <div class="col-sm-10 col-md-2  ">
                            <input class="btn btn-default  col-xs-8" type="button" value="导出工资明细表" onclick="queryMediator()">
                       </div>
                       <div class="col-sm-10 col-md-2 ">
                            <input class="btn btn-default col-xs-7" type="button" value="发送邮件" onclick="queryMediator()">
                       </div>
                   </form>
           </div><br><br><br>
             
          
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
                   data-height="562"
                   data-url="querySalaryByParams"
                   data-pagination="true"
                   data-side-pagination="server"
                   data-query-params-type="pageNum"
                   data-query-params="queryParams"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>                  
                    <th data-field="staffNumber" data-align="center" >员工编号</th>
                    <th data-field="name" data-align="center" >员工姓名</th>
                    <th data-field="positionSalary" data-align="center" >岗位工资</th>
                    <th data-field="achiementSalary" data-align="center" >绩效工资</th>
                    <th data-field="personalTotal" data-align="center" >个人社保缴费合计</th>   
                    <th data-field="housePersonalTotal" data-align="center" >个人公积金缴费合计</th>                 
                    <!-- <th data-field="establishDate" data-align="center" >个人所得税</th> -->                    
                    <th data-field="salaryOther"  data-align="center"  data-formatter="operationFormatter">其他款项</th>
                    <th data-field="actualSalary" data-align="center" data-formatter="salaryFormatter">实发工资</th>
                </tr>
                </thead>
            </table>
            </div>   
          
		 
		  
		  </div>
		  
        </div>
     
      
    </div>
    <!-- 修改营业部信息 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
       <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">修改其他款项</h4>
             <div class="modal-body">
                <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="staffName" placeholder="" readonly>
                    </div>
                    </div>
                    <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">部门</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="departMentName" placeholder="" readonly>
                    </div> 
                    </div>
                   <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">其他款项</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="otherSalary" placeholder="" >
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="remark" placeholder="" >
                    </div>
                  </div>
                </form>
             </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="updateSalary()">修改</button>
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

</body>
</html>