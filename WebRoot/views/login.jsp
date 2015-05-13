
<jsp:include page="header_banner_no.jsp" flush="true" />

    <div class="container" style="margin-top:72px">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="用户名" id="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" id="password" type="password" value="">
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="#" onclick="login()" class="btn btn-lg btn-primary btn-block">Login</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>



<script>

function login() {
	
	var param = {};
	param.username = jQuery("#username").val();
	param.password = jQuery("#password").val();
	
	var user = sendAjax("/user/authenticate", param);
	
	if (user && user.username && user.username.length > 0) {
		window.location.href = "/index/dashboard";
	} else {
		alert("用户名密码错误");
	}
}

jQuery(document).ready(function(){
	jQuery("#skin").remove();
	jQuery(".banner").remove();
	jQuery("#").remove();
	resetFrameHei();
});

</script>
  
</html>  