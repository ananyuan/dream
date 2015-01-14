<!DOCTYPE html>
<html lang="en">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Blog Home - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/dream.css"/>
	
	<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
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
						<div class="content">
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


<!--body>
	 <div class="paper">
		<div class="banner paper-border">
			<div>
				<h1>DREAM</h1>
				<p>If you have a dream,you got to protect it!</p>
			</div>
			<div class="menu">
				<ul>
					<li><a href="/">Home</a></li>
					<li><a href="#">Blog</a></li>
					<li><a href="/task/timeline">TimeLine</a></li>
					<li><a href="/views/about.jsp">About</a></li>
				</ul>
			</div>
		</div>

		<div class="content paper-border">
			<div id="mainArticleDiv" class="content-page">
				<div class="article">
					<div class="heading">
						<h2><a href="#">${article.title}</a></h2>
					</div>
					<div class="content">
						<p>${article.content}</p>
					</div>
				</div>
			</div>
		</div>

		<div class="footer">
			<div class="copyright">
				<p>Copyright ? 2014</p>
			</div>			
		</div>
	 </div>
</body-->
</html>