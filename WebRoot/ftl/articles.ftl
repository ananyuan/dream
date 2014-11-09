
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

<div id="page" >
	<#assign nowPage= _PAGE_.pageNo>
	<#if _PAGE_.pageNo < _PAGE_.totalPage>	
		<div class="page" id="nextPage"><span><a style="text-decoration: none;" href="#" onclick="goPage(${nowPage + 1})">下一页</a></span></div>
	</#if>
	<div class="page">
		<#assign count= _PAGE_.totalPage>
		<#list 1..count as i>
			<#if i==nowPage>
				<span class="select"><a href="#">${i}</a></span>
			<#else>
				<span class="normal"><a href="#" onclick="goPage(${i})">${i}</a></span>
			</#if>
		</#list>
	</div>
	<#if _PAGE_.pageNo != 1>
		<div class="page" id="prePage"><span><a style="text-decoration: none;" href="#" onclick="goPage(${nowPage - 1})">上一页</a></span></div>
	</#if>
</div>



