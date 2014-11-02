
<jsp:include page="header.jsp" flush="true" />

帐号:<input type="text" id="username" value=""><br>  
密码:<input type="password" id="password" value=""><br>  
<input type="button" onclick="login()"  value="登录">  


<script>

function login() {
	
	var param = {};
	param.username = jQuery("#username").val();
	param.password = jQuery("#password").val();
	
	var user = sendAjax("/user/authenticate", param);
	
	if (user && user.username && user.username.length > 0) {
		window.location.href = "/task/list";
	} else {
		alert("用户名密码错误");
	}
}



</script>
  
</html>  