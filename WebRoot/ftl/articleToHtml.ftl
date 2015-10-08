<!DOCTYPE html>
<html lang="en">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${article.title}</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/dream.css"/>
	
	<script type="text/javascript" src="/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/tools.js"></script>		
    <script type="text/javascript" src="/js/clickcount.js"></script>	
    <script type="text/javascript" src="/js/model/comment.js"></script>	
</head>


<body style="background-color: #EEE;">
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" style="text-shadow: none; font-size: 24px;width: 240px;" href="/">袁安安的三分田</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                 <ul class="nav navbar-nav navbar-right">
                    <li> 
                        <a href="/views/about.jsp">关于</a>
                    </li>
                    <li>
                        <a href="/task/timeline">时间轴</a>
                    </li>
                    <li>
                        <a href="/user/login">登录</a>
                    </li>
                </ul>               
            </div>   
        </div>
    </nav>

    <!-- Page Content -->
    <div class="container well">

        <div class="article-content">
						<h1><a href="#" style="text-decoration: none;">${article.title}</a></h1>
						
						<p style="font-size: 14px; color: gray;"><span class="glyphicon glyphicon-time"></span> ${article.atime} &nbsp; ${article.channame} &nbsp; 被浏览<span id="clickcount"></span>次</p>
						
						<div class="content article-font" style="padding-top: 20px;">
							<p>${article.content}</p>
						</div>        
        
        </div>

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright © yuananan.cn 2015 鄂ICP备15004825号</p>
                </div>
            </div>
        </footer>

    </div>
</body>

<script>

jQuery(document).ready(function(){
	var ddcommnet = new dcommnet({});
	ddcommnet.render();
	
});

</script>

</html>