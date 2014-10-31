<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>dream</title>
	
  	<link rel="stylesheet" href="/css/zerogrid.css">
	<link rel="stylesheet" href="/css/dream.css">
    
    
    <script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="/js/tools.js"></script>
</head>
<body>


<div class="wrap-body zerogrid">


<header>
	<div class="wrap-header">
		<div id="logo">
			<h1>DREAM</h1>
			<p> If you have a dream,you got to protect it!</p>
		</div>
		
		<nav>
			<div class="wrap-nav">
				<div class="menu">
					<ul>
						<li><a href="index.jsp">Home</a></li>
						<li><a href="#">Blog</a></li>
						<li><a href="#">Gallery</a></li>
						<li><a href="#">About</a></li>
						<li><a href="#">Contact</a></li>
					</ul>
				</div>
			</div>
		</nav>
		
	</div>
</header>


<section id="content">
	<div class="wrap-content">
		<div class="row block">
			<div id="main-content" class="col-2-3">
				<div class="wrap-col">
					<article>
						<div class="heading">
							<h2><a href="#">${article.title}</a></h2>
						</div>
						<div class="content">
							${article.content}
						</div>
					</article>
				</div>
			</div>
		</div>
	</div>
</section>					

<footer>
	<div class="copyright">
		<p>Copyright © 2014</p>
	</div>
</footer>

</div>
</body>

</html>