
<jsp:include page="header.jsp" flush="true" />


<div style="margin:200px">

帐号:<input type="text" id="username" value="admin"><br>  
密码:<input type="password" id="password" value="123456"><br>  

<input type="button" onclick="login()"  value="登录">  

</div>

<script>

function login() {
	
	var param = {};
	param.username = jQuery("#username").val();
	param.password = jQuery("#password").val();
	
	var user = sendAjax("/user/authenticate", param);
	
	if (user && user.username && user.username.length > 0) {
		window.location.href = "/";
	} else {
		alert("用户名密码错误");
	}
}

jQuery(document).ready(function(){
	jQuery("#skin").remove();
	jQuery(".banner").remove();
	jQuery("#").remove();
});

</script>
  
</html>  