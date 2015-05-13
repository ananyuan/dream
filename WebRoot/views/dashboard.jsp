<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>BOARD</title>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

	<link href="/css/dashboard.css" rel="stylesheet">
	
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">

    <!-- MetisMenu CSS -->
    <link href="/css/metisMenu/metisMenu.min.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0;">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/index/dashboard">XX管理系统</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" title="退出" href="#">
                        <i class="glyphicon glyphicon-off user-logout"></i> 
                    </a>
                </li>
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="/index/dashboard"><i class="fa fa-dashboard fa-fw"></i> 首页</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div id="page-wrapper">
			<iframe src="/views/dash/main.html" id="right_main_iframe"></iframe>
        </div>

    </div>

    <!-- jQuery -->
    <script src="/js/jquery-1.11.2.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/js/metisMenu/metisMenu.min.js"></script>
    
    <script src="/js/tools.js"></script>
    
    <script src="/js/metisMenu/menu.js"></script>

<script type="text/javascript">
var menu = new dr.menu();
menu.render();


jQuery(document).ready(function(){
	jQuery(".user-logout").bind("click", function(){
		if (confirm("确定退出?")) {
			sendAjax("/user/logout", {}, "get");
			
			window.location.href = "/user/login";
		}
	});
	
	
	//设置右边的最小高度
	var screenHeight = top.document.documentElement.clientHeight - 51;
	
	jQuery("#page-wrapper").css("min-height", screenHeight);
	
	var frameId = "right_main_iframe";
	var iframe = document.getElementById(frameId);
	if (!iframe) {
		iframe = parent.document.getElementById(frameId);
	}	
	iframe.style.height = screenHeight - 5 + "px";
	

	resetFrameHei();
});
	
</script>
</body>

</html>
