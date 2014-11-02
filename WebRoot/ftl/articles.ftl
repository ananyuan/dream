
<#list articles as article>
	<div class="article">
		<div class="heading">
			<h2><a href="#" onclick="window.open('${article.localurl}')">${article.title}</a></h2>
		</div>
		<div class="content">
			<p>${article.summary}[...]</p>
		</div>
	</div>	
</#list>

