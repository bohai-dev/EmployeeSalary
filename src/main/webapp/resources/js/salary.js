$(function(){  
	  //console.log(12345);
            var treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
            	{"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
            $('#tree').treeview({data: treeObj,enableLinks: true});
            
          
  });

function queryParams(params){
    return {
    	/* mediatorNo:$('#mediatorNo').val(),
      	 mediatorName:$("#mediatorName").val(),
      	 depNo:$("#depNo").val(),
      	 reportMonth:$("#reportMonth").val(),
      	 isChange:$("#isChange").val(),*/
         pageNumber:params.pageNumber,
         pageSize:params.pageSize
         }
  }

function operationFormatter(value,row,index) {
	if(value==null){
		value='0.00';
	}
    
    var html = value+'&nbsp;&nbsp;<button type="button" id="cog'+index+'" class="btn btn-link btn-sm">修改'
                             + '</button>';
            
    //添加修改事件
     $("#salaryTable").off("click","#cog"+index);
     $("#salaryTable").on("click","#cog"+index,row,function(event){
           config(row);
        });       
    return html;
}

/* 修改模态框 */
function config(row){
    console.log(row);
    var other=row.salaryOther;
    if(other==null){
    	other='0.00';
    }
    $("#otherSalary").val()==other;
    
    $("#editModal").modal('show');
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
           config(row);
        });       
    return html;
}