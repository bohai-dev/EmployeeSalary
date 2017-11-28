<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>

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


<!-- table -->
<link rel="stylesheet"
	href="resources/bootstrap-table/bootstrap-table.css">
	
<!-- editable -->	
<!-- <link rel="stylesheet"
	href="resources/bootstrap3-editable/css/bootstrap-editable.css"> -->
	
<!-- datepicker -->	
<link
    href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css"
    rel="stylesheet">


<script type="text/javascript"
	src="resources/tree/bootstrap-treeview.min.js"></script>
	
	

    
<script src="resources/bootstrap-table/bootstrap-table.js"></script>
<script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="resources/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>
<script type="text/javascript" src="resources/bootstrap3-editable/js/bootstrap-editable.js"></script>
<script
    src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<!-- datepicker -->
<script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script
    src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<!-- bootstrap-select -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script> -->


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
<!-- 文件上传插件fileInput -->
<script src="resources/fileInput/themes/fa/theme.js"></script>
<!-- optionally if you need translation for your language then include 
        locale file as mentioned below -->
<script src="resources/fileInput/js/locales/zh.js"></script>

<link rel="stylesheet" href="resources/dist/css/bootstrapValidator.css" />
<script type="text/javascript"
	src="resources/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="resources/dist/js/language/zh_CN.js"></script>


<script type="text/javascript">
	$(function () {
	    $('#table').bootstrapTable({
	        url: 'queryStaffInfos',  
	        method: 'post',  
	        toolbar: '#toolbar',  
	        striped: true,  
	        cache: false,  
	        pagination: true,  
	        sortName: 'Id',  
	        sortOrder: 'desc',  
	        sidePagination: 'client',  
	        pageNumber: 1,  
	        pageSize: 50,  
	        pageList: [10, 25, 50, 100],  
	        strictSearch: true,  
	        clickToSelect: true,  
	        height: 600,  
	        uniqueId: 'Id',  
	        cardView: false,  
	        detailView: false,
            
	        columns: [
	                  {  
                field: 'rownum',  
                title: 'rownum'
            },{  
	            field: 'staffNumber',  
	            title: '编号',  
	            sortable: true  
	        }, {  
	            field: 'name',  
	            title: '姓名',  
	            editable: {  
	                type: 'text',  
	                validate: function (value) {  
	                    if ($.trim(value) == '') {  
	                        return '单元编号不能为空!';  
	                    }  
	                }  
	            }  
	        }, {  
	            field: 'departmentName',  
	            title: '部门',
                editable: {
	                type: 'date', 
	                datepicker:{
	                    format: "yyyy-mm-dd",
	                    startView: 0,
	                    minViewMode: 0,
	                    maxViewMode: 2,
	                    todayBtn: "linked",
	                    language: "zh-CN",
	                    autoclose: true,
	                    todayHighlight: true
			                    },
	                validate: function (value) {  
	                    if ($.trim(value) == '') {  
	                        return '名称不能为空!';  
	                    }  
	                }  
	            }
	        }, {  
	            field: 'positionSalary',  
	            title: '岗位工资',  
	            editable: {  
	                type: 'text',  
	                /* validate: function (value) {
	                    if ($.trim(value) == '') {  
	                        return '岗位工资不能为空!';  
	                    }  
	                }   */
	            }  
	        }, {  
	            field: 'operation',  
	            title: '操作',  
	            width: 100,  
	            formatter: function (value, row, index) {  
	                var s = '<a class = "save" href="javascript:void(0)">保存</a>';  
	                var d = '<a class = "remove" href="javascript:void(0)">删除</a>';  
	                return s + ' ' + d;  
	            },  
	            events: 'operateEvents'  
	        }]  
	    });  
	    window.operateEvents = {
	        'click .save': function (e, value, row, index) {
	            $.ajax({  
	                type: "post",  
	                data: row,  
	                url: 'Webservice.asmx/ModifyResourceList',
	                success: function (data) {  
	                    alert('修改成功');  
	                }  
	            });  
	        },  
	        'click .remove': function (e, value, row, index) {
	            /* $.ajax({  
	                type: "post",  
	                data: row,  
	                url: 'Webservice.asmx/RemoveResourceList',  
	                success: function (data) {  
	                    alert('删除成功');  
	                    $('#table').bootstrapTable('remove', {  
	                        field: 'Id',  
	                        values: [row.Id]  
	                    });  
	                }  
	            });   */
	            $('#table').bootstrapTable('remove', {
	                field: 'rownum',
	                values: [row.rownum]
	            });
	            
	        }  
	    };
	    
	    
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
	   
	    
	    var rownum ;
	    
	    //动态添加
	    $('#addSalaryBtn').click(function () {
            
	        if(rownum == null){
	            rownum = $('#table').bootstrapTable('getOptions').totalRows+1;
	        }
	        
            $('#table').bootstrapTable('insertRow', {
                index: 0,
                row: {
                    rownum: rownum++,
                    name:'',
                    positionSalary:''
                }
            });
        });
	    
	});  
	
	
</script>
</head>
<body>


    <div id="toolbar" >
                <button type="button" id="addSalaryBtn" class="btn btn-default" title="添加">
                    <i class="glyphicon glyphicon-plus">添加</i>
                </button>&nbsp;&nbsp;&nbsp;&nbsp;             
                
                
    </div>
            
    <table id="table">
    </table>  
        
    <div class="col-sm-8">
                        <input type="text" class="form-control" id="probationDateStart1" placeholder="">
    </div>
    
    
    
    


</body>
</html>