<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>dream</title>
	
	<link id="cssfile" rel="stylesheet" href="/css/dream-red.css">
    
    <script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/tools.js"></script>
</head>

<body>
	 <div class="paper">
		<div id="skin" style="border:1px">
			<ul id="skinul">
				<li id="dream-blue" title="蓝色" style="background-color:blue">蓝</li>
				<li id="dream-red" title="红色" style="background-color:red">红</li>
				<li id="dream-green" title="绿色" style="background-color:green">绿</li>
				<li id="dream-gray" title="灰色" style="background-color:gray">灰</li>
			</ul>
		</div>	 
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