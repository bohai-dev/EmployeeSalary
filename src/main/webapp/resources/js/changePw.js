
$(function(){
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
	   
	   
   });
   
/*------------------------------------------------------------------------------------------------*/ 
  
function changePW(){
	 var param = {
	    		password:$('#inputPassword1').val(),
	            };
	 $.ajax({
         url: 'changeUserPW',
         type: 'post',
         contentType: "application/json;charset=UTF-8",
         data: JSON.stringify(param),
         success: function (data,status) {
             alert("新密码修改成功!");
             top.location='toHome'; 

         }
    });
}