$(function(){  
	  //console.log(12345);
            var treeObj = [{"href":"toHome","selectable":false,"showCheckbox":false,"text":"员工信息管理"},
            	{"href":"toSalary","selectable":false,"showCheckbox":false,"text":"员工工资管理"}];
            $('#tree').treeview({data: treeObj,enableLinks: true});
            
          
         
      
           
           
       
        });