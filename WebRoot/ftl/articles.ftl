<#if canEdit?? && canEdit>
<div>
<a href="/article/edit/_ADD_">添加</a>
</div>
</#if>	

<#list articles as article>
    <h2>
        <a href="#" onclick="window.open('${article.localurl}')">${article.title}</a>
		<#if canEdit?? && canEdit>
			<div style="float:right">
				<a href="/article/edit/${article.id}">编辑</a>
				<a href="#" onclick="deleteItem('${article.id}')">删除</a>
			</div>	
		</#if>	
    </h2>

    <p><span class="glyphicon glyphicon-time"></span> ${article.atime}</p>
    
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

