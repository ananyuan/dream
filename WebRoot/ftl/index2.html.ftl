<!DOCTYPE html>
<html lang="en">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>袁安安的三分田</title>

    <!-- Bootstrap Core CSS -->
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="/css/zTreeStyle/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="/css/flexslider.css">
	<link rel="stylesheet" type="text/css" href="/css/dream.css"/>
	
	<script type="text/javascript" src="/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    
	<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="/js/jquery.flexslider.js"></script>
    
    <script type="text/javascript" src="/js/tools.js"></script>		
</head>

<body>
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



    <div class="container">
        <div class="row" style="margin-bottom:20px;padding: 15px;">
		     <div class="col-md-12" style="color:red;font-weight: bold;text-align:center;padding: 10px;">该网站仅用作学习目的，若需汇款/转账等操作转向本域名，请勿信!!!</div>
             <form onSubmit="return fullQuery();">
             <div class="col-md-2"></div>
             <div class="input-group col-md-8">
                 <input type="text" class="form-control" style="border: 1px solid #46b8da; border-radius: 0px;" id="query_str" placeholder="输入查询内容">
                 <span class="input-group-btn">
                     <button class="btn btn-default btn-info" type="submit" id="full_search_btn">
                         <span class="">飕一下</span>
                 	</button>
                 </span>
             </div>
             </form> 
        </div>
        
        <div class="row">
			<div class="col-md-6" style="height: 350px;">
				<div class="flexslider">
				  <ul class="slides">
					<#list imgArticles as article>
					<li class="list-group-item" style="height: 350px;">
						<img src="${article.imgids}" class="search-image" style="width:100%;max-height:350px"/>
						<p class="flex-caption" style="position: absolute;bottom: 0px;">
							<a href="#" onclick="window.open('${article.localurl}')" style="color:#31708f;text-decoration: none;">${article.title}</a>
						</p>
					</li>
					</#list>
				  </ul>
				</div>
			</div>
	
			<div class="col-md-6">
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="text-left" style="font-weight: bold;display: inline-block;">最新笔记</div>
						<div class="text-right" style="display: inline-block;float: right;">
							<a href="/index.html">查看更多>></a>
						</div>
					</div>
					<ul class="list-group">	
						<#list textArticles as article>
						<li class="list-group-item">
							<a href="#" onclick="window.open('${article.localurl}')" style="text-decoration: none;">${article.title}</a>
						</li>
						</#list>
					</ul>
				</div>
			</div>
		</div>

    </div>
    
    <footer>
        <div class="row" style="margin:0px">
            <div class="col-lg-12 text-center">
                <p>Copyright © yuananan.cn 2015 鄂ICP备15004825号</p>
            </div>
        </div>
    </footer>

<script>
jQuery(document).ready(function(){
	$('.flexslider').flexslider({
		animation: "slide"
	});
});

function fullQuery() {
	var queryStr = jQuery("#query_str").val();
	if (queryStr.length > 0) {
		window.location = "/search/page/" + queryStr + "/1";
	}	
	
	return false;
}

</script>

</body>

</html>