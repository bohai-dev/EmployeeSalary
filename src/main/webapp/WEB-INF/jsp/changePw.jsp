<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
	
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">   
    <link href="resources/favicon.ico" rel="shortcut icon"/>
    <link rel="stylesheet" href="resources/dist/css/bootstrapValidator.css"/>
    <script type="text/javascript" src="resources/dist/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="resources/dist/js/language/zh_CN.js"></script>
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <script type="text/javascript" src="resources/js/changePw.js"></script>
    <script type="text/javascript">
    var userLocked = ${sessionScope.user["locked"]}
    </script>
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
            
            
            <li  class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">欢迎：${sessionScope.user.fullName}<strong class="caret"></strong></a>
            <ul class="dropdown-menu">
            <li>
				 <a href="tochangePw">修改密码</a>
			</li>
			 <li><a href="logout">退出</a></li>
			</ul>
            </li>
            
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
          <h1 class="sub-header"><a href="tochangePw" style="text-decoration: none;">修改用户密码</a></h1>

   
   <form class="form-horizontal" role="form">
				<div class="form-group">
					 <label for="oldPsw" class="col-sm-2 control-label">请输入旧密码</label>
					<div class="col-sm-6">
						 <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Old Password..." required>
					</div>
					<script type="text/javascript">
					
					</script>
				</div>
				
				<div class="form-group">
					 <label for="inputPassword3" class="col-sm-2 control-label">请输入新密码</label>
					<div class="col-sm-6">
						 <input type="password" id="inputPassword1" name="password1" class="form-control" placeholder="New Password..." required>
					</div>
				</div>
				
				<div class="form-group">
					 <label for="inputPassword3" class="col-sm-2 control-label">请确认新密码</label>
					<div class="col-sm-6">
						 <input type="password" id="inputPassword2" name="password2" class="form-control" placeholder="Affirm New Password..." required>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						 <button type="button" class="btn btn-default" onclick="changePW()">确认</button>
					</div>
				</div>
			</form>

<script>

$(function () {
    $('form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
           
        	
        	password: {
                validators: {
                    notEmpty: {
                        message: '旧密码不能为空'
                    },
                    threshold :  6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: 'checkOldPW',//验证地址
                        delay :  1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                        /**自定义提交数据，默认值提交当前input value
                         *  data: function(validator) {
                              return {
                                  password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                  whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                              };
                           }
                         */
                    },
                   
                   
                }
            },
            
            password1: {
            	  validators: {
                      notEmpty: {
                          message: '新密码不能为空'
                      },
                      stringLength: {
                          min: 6,
                          message: '新密码长度必须大于6位'
                      },
                      regexp: {
                          regexp: /^[a-zA-Z0-9_]+$/,
                          message: '密码只能包含大写、小写、数字和下划线'
                      },
                      different: {
                    	  field: 'password',
                    	  message: '新密码不能与旧密码相同'
                      }
                  }
              },
             
              password2: {
            	  validators: {
                      notEmpty: {
                          message: '确认新密码不能为空'
                      },
                      stringLength: {
                          min: 6,
                          message: '新密码长度必须大于6位'
                      },
                      regexp: {
                          regexp: /^[a-zA-Z0-9_]+$/,
                          message: '密码只能包含大写、小写、数字和下划线'
                      },
                      identical: {
                    	  field: 'password1',
                    	   message: '确认新密码与新密码两次输入不同'
                      },
                      different: {
                    	  field: 'username',
                    	  message: '新密码不能与旧密码相同'
                      }
                  }
              	}
              
              
              
            }
       
    });
});

</script>

	
    
</body>
</html>