
<jsp:include page="header.jsp" flush="true" />

		<div class="row-fluid">
					
			<div class="row-fluid">
				<div class="login-box">
					<div class="icons">
						<a href="index.html"><i class="halflings-icon home"></i></a>
						<a href="#"><i class="halflings-icon cog"></i></a>
					</div>
					<h2>Login to your account</h2>
					<form class="form-horizontal" action="index.html" method="post">
						<fieldset>
							
							<div class="input-prepend" title="Username">
								<span class="add-on"><i class="halflings-icon user"></i></span>
								<input class="input-large span10" name="username" id="username" type="text" placeholder="type username"/>
							</div>

							<div class="input-prepend" title="Password">
								<span class="add-on"><i class="halflings-icon lock"></i></span>
								<input class="input-large span10" name="password" id="password" type="password" placeholder="type password"/>
							</div>

							<div class="button-login">	
								<button type="button" onclick="login()" class="btn btn-primary">Login</button>
							</div>
					</form>
				</div><!--/span-->
			</div><!--/row-->
			

	</div><!--/.fluid-container-->


<!-- div style="margin:200px">

帐号:<input type="text" id="username" value=""><br>  
密码:<input type="password" id="password" value=""><br>  

<input type="button" onclick="login()"  value="登录">  

</div-->

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