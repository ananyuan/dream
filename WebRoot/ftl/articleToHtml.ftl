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
                <a class="navbar-brand" href="/">DREAM</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="/views/about.jsp">About</a>
                    </li>
                    <li>
                        <a href="/task/timeline">TimeLine</a>
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
						<h1><a href="#">${article.title}</a></h1>
						
						<p><span class="glyphicon glyphicon-time"></span> ${article.atime} &nbsp; ${article.channame}</p>
						
						<div class="content" style="padding-top:20px">
							<p>${article.content}</p>
						</div>        
        
        </div>

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright © Your Website 2014</p>
                </div>
            </div>
        </footer>

    </div>
</body>


</html>