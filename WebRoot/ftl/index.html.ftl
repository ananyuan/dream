<!DOCTYPE html>
<html lang="en">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>DREAM</title>

    <!-- Bootstrap Core CSS -->
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/dream.css"/>
	
	<script type="text/javascript" src="/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
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
                <a class="navbar-brand" href="/"><i class="glyphicon glyphicon-globe"></i>DREAM</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li> 
                        <a href="/views/about.jsp"><i class="glyphicon glyphicon-user"></i>About</a>
                    </li>
                    <li>
                        <a href="/task/timeline"><i class="glyphicon glyphicon-calendar"></i>TimeLine</a>
                    </li>
                </ul>
                
                 <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="/user/login">Login</a>
                    </li>
                </ul>               
            </div>   
        </div>
    </nav>


    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <!-- Blog Entries Column -->
            <div class="col-md-8">

                <h1 class="page-header">
                    You got a dream, you got to protect it. 
                    <small></small>
                </h1>
				<div id="mainArticleDiv">





<#if canEdit?? && canEdit>
<div>
<a href="/article/edit/_ADD_" class="btn btn-primary btn-sm">添加BLOG</a>
<a href="/task/list" class="btn btn-primary btn-sm">TASKS</a>
<a href="/task/edit/_ADD_" class="btn btn-primary btn-sm">添加TASK</a>
<hr>
</div>
</#if>	

<#list articles as article>
    <h2>
        <a href="#" onclick="window.open('${article.localurl}')">${article.title}</a>
		<#if canEdit?? && canEdit>
			<div style="float:right">
				<a class="btn btn-primary btn-sm" href="/article/edit/${article.id}">编辑</a>
				<a class="btn btn-danger btn-sm" href="#" onclick="deleteItem('${article.id}')">删除</a>
			</div>	
		</#if>	
    </h2>

    <p><span class="glyphicon glyphicon-time"></span> ${article.atime} &nbsp; ${article.channame}</p>
    
    <#if article.imgids?length gt 0> 
    <hr>
    <img class="img-responsive" src="/file/${article.imgids}" alt="">
    </#if>
    
    <hr>
    <p>${article.summary}</p>
    <a class="btn btn-primary" href="#" onclick="window.open('${article.localurl}')">Read More <span class="glyphicon glyphicon-chevron-right"></span></a>

    <hr>
</#list>

<nav>
    <ul class="pagination pagination-lg ">
        <#assign nowPage=_PAGE_.pageNo>
            <#if _PAGE_.pageNo !=1>
                <li>
                	<a href="#" onclick="goPage(${nowPage - 1})" aria-label="Next">
		        		<span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </#if>

            <#assign count=_PAGE_.totalPage>
                <#list 1..count as i>
                    <#if i==nowPage>
                        <li class="active"><a href="#">${i}</a>
                        </li>
                        <#else>
                            <li><a href="#" onclick="goPage(${i})">${i}</a>
                            </li>
                    </#if>
                </#list>

                <#if _PAGE_.pageNo < _PAGE_.totalPage>
                    <li>
                        <a href="#" onclick="goPage(${nowPage + 1})" aria-label="Next">
                            <span aria-hidden="false">&raquo;</span>
                        </a>
                    </li>
                </#if>
    </ul>
</nav>

















				</div>
            </div>

            <!-- Blog Sidebar Widgets Column -->
            <div class="col-md-4">

                <!-- Blog Search Well -->
                <div class="well">
                    <h4>Blog Search</h4>
                    <div class="input-group">
                        <input type="text" class="form-control" id="query_str" placeholder="输入查询内容">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="full_search_btn">
                                <span class="glyphicon glyphicon-search"></span>
                        	</button>
                        </span>
                    </div>
                </div>
                
                <div class="well">
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="android_download_url">
                            	<li><h3><a href='http://pan.baidu.com/s/1jGIfvIu' target="_blank">ANDROID客户端下载</a></h3></li>
                            </ul>
                        </div>
                    </div>
                </div>                
                
                <div class="well">
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="channelsDiv">
                            	<#list channels as channel>
                            		<li><h3><a href='#'>${channel.name}</a></h3></li>
                            	</#list>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <!-- Blog Categories Well -->
                <div class="well">
                    <h4><a href="#" onclick="toTaskList()">未完成</a></h4>
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="tasksTodoDiv">
                            	<#list todoTasks as task>
                            		<li>${task.title}</li>
                            	</#list>                            
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="well">
                    <h4><a href="#" onclick="toTaskList()">已完成</a></h4>
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="tasksFinishDiv">
                            	<#list finishTasks as task>
                            		<li>${task.title}</li>
                            	</#list>
                            </ul>
                        </div>
                    </div>
                </div>				
				
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


<script>
jQuery(document).ready(function(){
	jQuery("#full_search_btn").bind("click", function(){
		var queryStr = jQuery("#query_str").val();
		if (queryStr.length > 0) {
			window.location = "/search/page/" + queryStr + "/1";	
		}
	});
	
	jQuery("#channelsDiv a").each(function(index, item) {
		jQuery(item).bind("click", function() {
			window.location.href = "/article/list";
		});	
	});
});

function toTaskList() {
	window.location = "/task/list";	
}



</script>

</body>


</html>