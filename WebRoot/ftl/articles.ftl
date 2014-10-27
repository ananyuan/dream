
<#list articles as article>
	<article>
		<div class="heading">
			<h2><a href="#" onclick="window.open('${article.localurl}')">${article.title}</a></h2>
		</div>
		<div class="content">
			<img src="" width="250px" height="100px"/>
			<p>${article.summary}[...]</p>
		</div>
		<div class="info">
			<p>${article.atime} - <a href="#">01 Commnets</a></p>
		</div>
	</article>
</#list>
