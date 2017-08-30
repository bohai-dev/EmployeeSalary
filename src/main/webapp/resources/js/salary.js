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
                             + '</button>'
                             +'&nbsp;&nbsp;<button type="button" id="mail'+index+'" class="btn btn-link btn-sm">发送工资条'
                             + '</button>';
            
    //添加查看事件
     $("#salaryTable").off("click","#cog"+index);
     $("#salaryTable").on("click","#cog"+index,row,function(event){
           detail(row);
        });
     $("#salaryTable").off("click","#mail"+index);
     $("#salaryTable").on("click","#mail"+index,row,function(event){
           send(row);
        });
    return html;
}

/* 修改模态框 */
function detail(row){
    
    $("#deailModal").modal('show');
}


/**
 * 单发送工资条
 * @author CY
 * */
function send(row){
	console.log(row);
	if (confirm("给用户：" + row.name + "发送工资条,确定吗？")) {
		var param = {
			staffNum : row.staffNumber,
			payDate:row.payDate
		};
		$.ajax({
			type : "post",
			url : "sendMail",
			//dataType: 'json',
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify(param),
			success : function(date, status) {
				alert("发送成功");
			}
		});
	}

}

/**
 * 群发送工资条
 * @author CY
 * */
function openSendMails(){
	console.log("aaaaaa");
	$("#sendMailsModal").modal('show');
}

/**
 * 群发送工资条
 * @author CY
 * */
function sendMails(){
	if (confirm("给所有用户发送"+$('#selectSalaryMonth').val()+"月份工资条,确定吗？  发送成功后会弹出发送状态框")) {
		$("#sendMailsModal").modal('hide');
	var param={
			 payDate:$('#selectSalaryMonth').val()	 
	};
	$.ajax({
		type : "post",
		url : "sendMails",
		//dataType: 'json',
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify(param),
		success : function(date, status) {
			alert("发送成功");
		},
		error:function(date,status){
			alert("发送失败，请重试");
		}
	});
}
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


//确定计算工资 
function compSalary(){
//	console.log($('#selectDepName').val());
//	console.log($('#selectMonth').val());

	var param={
			 depNum:$('#selectDepName').val(),
			 payDate:$('#selectMonth').val()	 
	}
	  $.ajax({
          url: 'calculateSalary',
          type: 'post',
          contentType: "application/json;charset=UTF-8",
          data: JSON.stringify(param),
          success: function (data,status) {
              $('#selectModal').modal('hide');
              $('#salaryTable').bootstrapTable('refresh');
          }
     });
}


