<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>诺克家装产品管理平台首页</title>
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/online/css/common/supersized.css" /> --%>
<link href="<%=request.getContextPath()%>/pages/app/css/bootstarp/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/pages/app/css/common/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/online/js/jquery/jquery.js"></script>
<!-- <script type="text/javascript">  
         function validate_channel_info(channelform)  
         {  
             if(channelform.account.value=="")  
             {  
                 alert("请输入账号!");  
                 return false;  
             }  
             if(channelform.password.value=="")  
             {  
                 alert("请输入密码!");  
                 return false;  
             }  
             return true;  
         }            
</script> --> 
 
</head> 
<body>
<div class="login_top">
	<div class="login_top_content">
    	<img src="<%=request.getContextPath()%>/pages/online/images/nork_login.png" width="112" height="71" align="absbottom" />
    <span>三度云享家平台</span>
	</div>
</div>
<div class="mid_wrap">
    <div class="login_mid">
        <div class="login_mid_left">
        	<img src="<%=request.getContextPath()%>/pages/online/images/pic.png" width="438" height="496" />
        </div>
        <div class="login_mid_right">
        		
	<div class="main_box">
		<div class="login_box">
			<div class="login_logo"><img src="<%=request.getContextPath()%>/pages/online/images/pic2.png" /></div>
			<div class="login_form">
				<form id="loginForm" name="loginForm"  method="post"  action="<%=request.getContextPath() %>/jsp/system/sysUser/jsplogin.htm" >  
					<div class="form-group">
						<label for="j_username" class="t">账  号：</label>
						<input id="account" name="account" value="" type="text" class="form-control x319 in" />
					</div>
					<div class="form-group">
						<label for="j_password" class="t">密  码：</label> 
						<input id="password" name="password" value="" type="password" class="password form-control x319 in"/>
					</div>
					<font color="red">${loginerror}</font> 
			    <div class="form-group"></div>
					<div class="form-group">
						<label class="t"></label>
						<label for="j_remember" class="m">
						<input id="j_remember" type="checkbox" value="true"/>&nbsp;&nbsp;记住登录账号!</label>
					</div>
				  <div class="form-group space">
						<label class="t"></label> 
					<button id="login" type="button" onclick="loginsubmit();" class="btn btn-primary btn-lg" >&nbsp;&nbsp;&nbsp;&nbsp;登 录&nbsp;&nbsp;&nbsp;&nbsp;</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="reset" type="reset" value="&nbsp;&nbsp;&nbsp;&nbsp;重  置&nbsp;&nbsp;&nbsp;&nbsp;" class="btn btn-default btn-lg" />
				  </div>
				</form>
			</div>
		</div>
		
	</div>
         </div>
    </div> 
</div>
<div class="login_foot">
	<div class="login_foot_centent"><span>版权所有&copy; 三度云享家    2017</span></div>
</div> 
<script language="JavaScript" type="text/javascript">
var path = '<%=request.getContextPath()%>';

jQuery(document).ready(function () {
 	if(navigator.userAgent.indexOf("Mozilla/") != -1 
 	&& (navigator.userAgent.indexOf("Firefox/") != -1 
 	|| navigator.userAgent.indexOf("Chrome/") != -1
 	   )){
 		//跳转到登录界面自动清空
 		$.get(path + "/jsp/system/sysUser/clear.htm");
 	 	
 		
 	}else{
		$("#account").attr("disabled","true");
		$("#password").attr("disabled","true");
		$("#j_remember").attr("disabled","true");
		$("#login").attr("disabled","true");
		$("#reset").attr("disabled","true");
		alert("本系统仅支持火狐和google浏览器!");
	}
});

if (top.location !== self.location) {
	top.location = path + "pages/app/login.jsp";
}

document.onkeydown = function(event) {
	var e = event || window.event
			|| arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		loginsubmit();	
	}
}

function loginsubmit(){
	
	var account = document.getElementById("account").value;
	var password = document.getElementById("password").value;
	if(account==null || account ==""||password =="" || password ==null){
		alert("账号或密码为空");
		return;
	}

 	$.ajax({
        type: "POST",
        datatype: "json",
        url: path + "/jsp/system/sysUser/sysUserCheck.htm",
        data:{"account":account,"password":password},
		timeout: 5000,
		async: false,
		error: function(request) {
            alert("登陆异常!");
        },
		success: function(res) {
        	    var data=eval("("+res+")");
        	    //alert(123);
        	    if(data.success){
        	    	 loginForm.submit();
		        }else{
		    	    alert(data.message);
		        }
        }
    });  
	// loginForm.submit();
}
</script> 
</body> 
</html>
   