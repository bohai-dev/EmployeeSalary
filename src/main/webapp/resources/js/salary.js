$(function(){  
	  //console.log(12345);
            var treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
            	{"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
            $('#tree').treeview({data: treeObj,enableLinks: true});
           
            //初始化
            $('.selectpicker').selectpicker();
    	    
            //绑定初始化方法
    	    $('.selectpicker').on('loaded.bs.select', function (e) {
    	        $.ajax({
    	            url: 'queryDepartment',
    	            type: 'post',
    	            dataType: 'json',
    	            success: function (data) {
    	                var len = data.length;
    	                var optionString = '';
    	                 for (i = 0; i < len; i++) {
    	                	 optionString += "<option value=''> </option>";
    	                     optionString += "<option value=\'"+ data[i].depNumber +"\'>" + data[i].depName + "</option>";
    	                 }
    	                 
    	                 $('.selectpicker').html(optionString);
    	                 $('.selectpicker').selectpicker('refresh'); 
    	                
    	            }
    	        });
    	    });    
    	    
    	    /* 初始化datepicker */
            $('.month').datepicker({
                format: "yyyy-mm",
                  startView: 1,
                  minViewMode: 1,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            
            
          

  });

function queryParams(params){
    return {
   /* 	 staffName:$('#queryName').val(),
    	 staffNum:$('#queryNum').val(),
    	 depNum:$("#queryDepName").val(),
    	 payDate:$("#reportMonth").val(),
      	 reportMonth:$("#reportMonth").val(),    */  	
         pageNumber:params.pageNumber,
         pageSize:params.pageSize
         }
  }

function operationFormatter(value,row,index) {
	if(value==null){
		value='0.00';
	}  
    
	var html = value+'&nbsp;&nbsp;<button type="button" id="editButton'+index+'" class="btn btn-link btn-sm">修改'
    + '</button>';
            
        //添加修改事件
       $("#salaryTable").off("click","#editButton"+index);
     
       $("#salaryTable").on("click","#editButton"+index,row,function(event){
           config(row);
        });       
  
   
    return html;
}

var staffNum='';
/* 修改模态框 */
function config(row){ 
    console.log(row);
    var other=row.salaryOther;
    if(other==null){
    	other='0.00';
    }
    $("#staffName").val(row.name);
    $("#departMentName").val(row.depName);
    $("#month").val(row.payDate);
    $("#otherSalary").val(other);   
    $("#remark").val(row.otherRemark);
    staffNum=row.staffNumber;
    $("#editModal").modal('show');
}


/**
 * 修改事件
 * @returns
 */
function updateSalary(){
	
	 var param = {
	       		staffNumber:staffNum,
	       		payDate:$('#month').val(),
	       		salaryOther: $("#otherSalary").val(),
	       		otherRemark:$("#remark").val()
	       		
	            };
	        $.ajax({
	            url: 'updateSalary',
	            type: 'post',
	            contentType: "application/json;charset=UTF-8",
	            data: JSON.stringify(param),
	            success: function (data,status) {
	                $('#editModal').modal('hide');
	                $('#salaryTable').bootstrapTable('refresh');
	            }
	       });
	
	
}

function salaryFormatter(value,row,index) {
	if(value==null){
		value='0.00';
	}
    
    var html = value+'&nbsp;&nbsp;<button type="button" id="cog'+index+'" class="btn btn-link btn-sm">查看详情'
                             + '</button>';
            
    //添加查看事件
     $("#salaryTable").off("click","#cog"+index);
     $("#salaryTable").on("click","#cog"+index,row,function(event){
           detail(row);
        });       
    return html;
}

/* 修改模态框 */
function detail(row){
    
    $("#editModal").modal('show');
}



/**
 * 邮件发送模态框
 * @author Cy
 * */

function sendMailFormatter(value,row,index) {
	var html = '<button type="button" id="cog'+index+'" class="btn btn-default btn-sm" title="设置">'
	+ '<i class="glyphicon glyphicon-cog"></i>'
	+ '</button>';

$("#salaryTable").off("click", "#cog" + index);
$("#salaryTable").on("click", "#cog" + index, row, function(event) {
sendMail(row);
});

return html;
}

/**
 * 发送邮件
 * @author Cy
 * */
function sendMail(row){
	
	$('#sendMailModel').modal('show');
}


/*查询事件*/
function querySalary(){
	
	

		$("#salaryTable").bootstrapTable('refresh', {
			url : "querySalaryByParams",	
			query : {
				 staffName:$('#queryName').val(),
		    	 staffNum:$('#queryNum').val(),
		    	 depNum:$("#queryDepName").val(),
		    	 payDate:$("#queryMonth").val()
		      

			}
		});	
}

function calculateSalary(){
	$("#selectModal").modal('show');
	
}


