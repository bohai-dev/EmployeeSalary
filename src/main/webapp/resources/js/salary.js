$(function(){  
	  //console.log(12345);
	var treeObj;
    if(userLocked=="1"){
    	treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
    	           {"href":"toCheck","selectable":false,"showCheckbox":false,"text":"申报信息审核"},
	               {"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
    }else{
    	
    	treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
    	               {"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
    }
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
    	                optionString += "<option value=''> </option>";
    	                 for (i = 0; i < len; i++) {
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
    
	var html=value;
/*	var html = value+'&nbsp;&nbsp;<button type="button" id="editButton'+index+'" class="btn btn-link btn-sm">修改'
    + '</button>';
            
        //添加修改事件
       $("#salaryTable").off("click","#editButton"+index);
     
       $("#salaryTable").on("click","#editButton"+index,row,function(event){
           config(row);
        });       */
  
   
       
       
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
    
    $("#pensionPersonal").val(row.pensionPersonal);
    $("#medicalPersonal").val(row.medicalPersonal);
    $("#unemploymentPersonal").val(row.unemploymentPersonal);
    $("#personalReserve1").val(row.personalReserve1);
    $("#personalReserve2").val(row.personalReserve2);
    $("#pensionCompany").val(row.pensionCompany);
    $("#medicalCompany").val(row.medicalCompany);
    $("#unemploymentCompany").val(row.unemploymentCompany);
    $("#injuryCompany").val(row.injuryCompany);
    $("#birthCompany").val(row.birthCompany);
    $("#companyReserve1").val(row.companyReserve1);
    $("#companyReserve2").val(row.companyReserve2);
    $("#houseBasePersonal").val(row.houseBasePersonal);
    $("#houseSupplyPersonal").val(row.houseSupplyPersonal);
    $("#houseBaseCompany").val(row.houseBaseCompany);
    $("#houseSupplyCompany").val(row.houseSupplyCompany);
    
    
    staffNum=row.staffNumber;
    $("#editModal").modal('show');
}


/**
 * 修改其他款项事件
 * @returns
 */
function updateSalary(){
	
	 var param = {
	       		staffNumber:staffNum,
	       		payDate:$('#month').val(),
	       		salaryOther: $("#otherSalary").val(),
	       		otherRemark:$("#remark").val(),
	       		
	       		pensionPersonal:$("#pensionPersonal").val(),
	       		medicalPersonal:$("#medicalPersonal").val(),
	       		unemploymentPersonal:$("#unemploymentPersonal").val(),
	       		personalReserve1:$("#personalReserve1").val(),
	       		personalReserve2:$("#personalReserve2").val(),
	       		pensionCompany:$("#pensionCompany").val(),
	       		medicalCompany:$("#medicalCompany").val(),
	       		unemploymentCompany:$("#unemploymentCompany").val(),
	       		injuryCompany:$("#injuryCompany").val(),
	       		birthCompany:$("#birthCompany").val(),
	       		companyReserve1:$("#companyReserve1").val(),
	       		companyReserve2:$("#companyReserve2").val(),
	       		houseBasePersonal:$("#houseBasePersonal").val(),
	       		houseSupplyPersonal:$("#houseSupplyPersonal").val(),
	       		houseBaseCompany:$("#houseBaseCompany").val(),
	       		houseSupplyCompany:$("#houseSupplyCompany").val()       		
	       		
	       		
	       		
	       		
	       		
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
   /*  $("#salaryTable").off("click","#mail"+index);
     $("#salaryTable").on("click","#mail"+index,row,function(event){
           send(row);
        });*/
    return html;
}
function operateFormate(value,row,index){
	
	var html = '<button type="button" id="editButton'+index+'" class="btn btn-link btn-sm">修改'
    + '</button>'
    +'&nbsp;&nbsp;<button type="button" id="mail'+index+'" class="btn btn-link btn-sm">发送工资条'
    + '</button>';;
            
        //添加修改事件
       $("#salaryTable").off("click","#editButton"+index);
     
       $("#salaryTable").on("click","#editButton"+index,row,function(event){
           config(row);
        });
       
       //添加发送工资条事件
       $("#salaryTable").off("click","#mail"+index);
       $("#salaryTable").on("click","#mail"+index,row,function(event){
             send(row);
          });
      return html;
	
}


/* 查看详情模态框 */
function detail(row){
	$('#staffNumber2').val(row.staffNumber);
	$('#name2').val(row.name);
	$('#depName2').val(row.depName);
	$('#positionSalary2').val(row.postionsSalary);
	$('#skillSalary2').val(row.skillsSalary);
	$('#achiementSalary2').val(row.achiementSalary);
	$('#workYears2').val(row.yearsSalary);
	$('#grossSalary2').val(row.grossSalary);
	$('#warmSubsidy2').val(row.warmSubsidy);
	$('#housePersonalTotal2').val(row.housePersonalTotal);
	$('#pensionPersonal2').val(row.pensionPersonal);
	$('#unemploymentPersonal2').val(row.unemploymentPersonal);
	$('#medicalPersonal2').val(row.medicalPersonal);
	$('#actualSalary2').val(row.actualSalary);
	$('#taxBase2').val(row.taxBase);
	$('#incomeTax2').val(row.incomeTax);
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
	console.log($('#selectDepName').val());
	console.log($('#selectMonth').val());

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

function exportSalary(){
	$("#exportModal").modal('show');
}

function exportDetail(){
	//console.log($('#exportDepName').val());
	//console.log($('#exportMonth').val());
    if($('#payDate').val()==null||$('#payDate').val()==''||isNull($('#depNum').val())){
    	alert('请选择月份和部门！');
    }
    
    else{ 
    	
    	
    	$('#exportForm').submit();
    	$('#exportModal').modal('hide');
    	
    
    };
    
/*	var param={
			 depNum:$('#exportDepName').val(),
			 payDate:$('#exportMonth').val()	 
	}
	  $.ajax({
          url: 'exportSalary',
          type: 'post',
          contentType: "application/json;charset=UTF-8",
          data: JSON.stringify(param),
          success: function (data,status) {
              $('#exportModal').modal('hide');
              $('#salaryTable').bootstrapTable('refresh');
          }
     });*/
	
}

function isNull(value){
    if(value == "" || value == undefined || value == null){
        return true;
    }else{
        return false;
    }
} 

function numberFormate(value,row,index) {
	//console.log(value);
	if(!isNull(value)){
		return (value+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');//使用正则替换，每隔三个数加一个','  
	}
}

//岗位工资合计
function tbfooter0(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].postionsSalary))
          interest += parseFloat(data[i].postionsSalary);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//绩效工资合计
function tbfooter1(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].achiementSalary))
          interest += parseFloat(data[i].achiementSalary);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//应发工资合计
function tbfooter2(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].grossSalary))
          interest += parseFloat(data[i].grossSalary);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//取暖补贴合计
function tbfooter3(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].warmSubsidy))
          interest += parseFloat(data[i].warmSubsidy);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//养老保险合计
function tbfooter4(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].pensionPersonal))
          interest += parseFloat(data[i].pensionPersonal);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//失业保险合计
function tbfooter5(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].unemploymentPersonal))
          interest += parseFloat(data[i].unemploymentPersonal);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//医疗保险合计
function tbfooter6(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].medicalPersonal))
          interest += parseFloat(data[i].medicalPersonal);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//个人社保缴费合计
function tbfooter7(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].personalTotal))
          interest += parseFloat(data[i].personalTotal);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//个人公积金缴费合计
function tbfooter8(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].housePersonalTotal))
          interest += parseFloat(data[i].housePersonalTotal);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//个人所得税合计
function tbfooter9(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].incomeTax))
          interest += parseFloat(data[i].incomeTax);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//其他款项合计
function tbfooter10(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].salaryOther))
          interest += parseFloat(data[i].salaryOther);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//实发工资合计
function tbfooter11(data){
    var interest = 0;
    for(var i=0;i<data.length;i++){
    	  if(!isNull(data[i].actualSalary))
          interest += parseFloat(data[i].actualSalary);
        }
    //保留两位小数
    return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
