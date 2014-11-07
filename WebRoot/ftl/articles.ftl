
<#list articles as article>
	<div class="article">
		<#if canEdit?? && canEdit>
			<div style="float:right">
				<a href="/article/edit/${article.id}">编辑</a>
			</div>	
		</#if>	
		<div class="heading">
			<h2><a href="#" onclick="window.open('${article.localurl}')">${article.title}</a></h2>
		</div>

		<div class="content">
			<p>${article.summary}[...]</p>
		</div>
	</div>	
</#list>

<div id="page" style="float:right">
	<div style="float:right;padding:4px" id="nextPage"><a href="#">下一页</a></div>
	<div style="float:right">
		<#assign count= _PAGE_.totalPage>
		<#list 1..count as i>
			<#if i==_PAGE_.pageNo>
				<span style="background-color:green;padding:4px" class="nowPage"><a href="#">${i}</a></span>
			<#else>
				<span style="background-color:gray;padding:4px"><a href="#">${i}</a></span>
			</#if>
		</#list>
	</div>
	<div style="float:right;padding:4px" id="prePage"><a href="#">上一页</a></div>
</div>




