<#list articles as article>
	<div class="well">
    <h2>
        <a href="#" onclick="window.open('${article.localurl}')" style="text-decoration: none;">${article.title}</a>
    </h2>

    <p><span class="glyphicon glyphicon-time"></span> ${article.atime} &nbsp; ${article.channame}</p>
    
    <#if article.imgids?length gt 0> 
    <hr>
    <img class="img-responsive" src="/file/${article.imgids}" alt="">
    </#if>
    
    <hr>
    <p>${article.summary}</p>
    <!--a class="btn btn-primary" href="#" onclick="window.open('${article.localurl}')">Read More <span class="glyphicon glyphicon-chevron-right"></span></a-->

    </div>
</#list>

<div class="well">
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
</div>
