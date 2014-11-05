<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>dream</title>
	
	<link rel="stylesheet" href="/css/dream-red.css">
    
    <script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/tools.js"></script>
</head>

<body>
	 <div class="paper">
		<div class="banner paper-border">
			<div>
				<h1>DREAM</h1>
				<p>If you have a dream,you got to protect it!</p>
			</div>
			<div class="menu">
				<ul>
					<li><a href="/index.jsp">Home</a></li>
					<li><a href="#">Blog</a></li>
					<li><a href="#">Gallery</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Contact</a></li>
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
</body>
</html>