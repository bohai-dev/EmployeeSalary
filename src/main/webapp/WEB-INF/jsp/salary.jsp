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
          <h1 class="sub-header"><a href="toHome" style="text-decoration: none;">员工工资管理</a></h1>

           <div class="row placeholders">
            <div class="col-sm-12 col-md-6">
                <label class="control-label">请选择文件上传</label>
                 <input id="finput" type="file" class="file" multiple >  
                    <script type="text/javascript">
                    $("#finput").fileinput({
                        language: 'zh',
                        uploadAsync: true,
                        uploadUrl: "fileUpload", //异步上传地址
                        maxFileCount: 10,//最大上传文件数限制
                        showCaption: true,//是否显示标题
                        showUpload: true,//是否显示上传按钮
                        allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀 
                        //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>" //选择文件后缩略图
                    }); 
                 </script>           
            </div>
              <div class="col-sm-12 col-md-6">
               
              </div>
          </div>
            <table id="deptTable"
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
                   data-url="queryCrmDept"
                   data-pagination="true"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="deptCode" data-align="center" >员工编号</th>
                    <th data-field="deptName" data-align="center" >员工姓名</th>
                    <th data-field="deptHead" data-align="center" >岗位工资</th>
                    <th data-field="deptTelephone" data-align="center" >绩效工资</th>
                    <th data-field="deptAddress" data-align="center" >个人缴费合计</th>                 
                    <th data-field="establishDate" data-align="center" >个人所得税</th>
                    <th data-field="establishDate" data-align="center" >实发工资</th>
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

</body>
</html>