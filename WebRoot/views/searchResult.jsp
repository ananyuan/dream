
<jsp:include page="header.jsp" flush="true" />

<style>
#b_results {
	display: inline-block;
	color: #333;
}
#b_results>li:first-child {
  padding-top: 7px;
}
#b_results>.b_algo {
  padding: 7px 20px 5px;
}
#b_results>li {
  padding: 7px 20px;
  margin: 0 0 3px;
    background-color: #fff;
}
#b_results p {
  word-wrap: break-word;
}
html, body, h1, h2, h3, h4, h5, h6, p, img, ol, ul, li, form, table, tr, th, td {
  border: 0;
  border-collapse: collapse;
  border-spacing: 0;
  list-style: none;
  margin: 0;
  padding: 0;
}
li {
  display: list-item;
  text-align: -webkit-match-parent;
}
h2, h2 strong {
  font-size: 18px;
  line-height: 23px;
  padding-bottom: 3px;
}
h1, h2, h3 {
  font: 18px/1.2em 'Microsoft YaHei',Arial,Helvetica,sans-serif;
}
.b_attribution, cite strong {
  color: green;
}
cite {
  font-style: normal;
  word-wrap: break-word;
}

#search_div {
    margin-top:60px;
}

#r_content {
}

#r_content nav{
 padding-left:20px;
}

#search_div .padding20 {
    padding-left:20px;
    padding-right:20px;
}
</style>

<%@ page import="com.dream.base.Page" %>
<%@ page import="com.dream.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>



<% 
List<HashMap<String, Object>> dataList;
if (null != request.getAttribute("_DATA_")) {
	dataList = (List<HashMap<String, Object>>)request.getAttribute("_DATA_");	
} else {
	dataList = new ArrayList<HashMap<String, Object>>();
}

String queryStr = (String)request.getAttribute("_queryStr_");

Page rpage;
if (null != request.getAttribute("_PAGE_")) {
	rpage = (Page)request.getAttribute("_PAGE_");	
} else {
	rpage = new Page();
}



%>
<div id="search_div">
    <div class="input-group padding20">
        <input type="text" class="form-control" id="query_str" placeholder="输入查询内容">
        <span class="input-group-btn">
            <button class="btn btn-default" type="button" id="full_search_btn">
                <span class="glyphicon glyphicon-search"></span>
        	</button>
        </span>
    </div>	
</div>
<div id="r_content">
	<ul id="b_results">
		<% for (HashMap<String, Object> dataMap: dataList) {%>
			<li class="b_algo">
				<h2>
					<a href="<%=dataMap.get("url") %>" target="_blank">
						<%=dataMap.get("title") %>
					</a>
				</h2>
				<div class="b_caption">
					<p><%=dataMap.get("summery") %></p>
					<div class="b_attribution">
						<cite><%=dataMap.get("url") %></cite>
						<%=dataMap.get("create_time") %>
					</div>
				</div>
			</li>
		<% } %>	
		<% if (dataList.size() == 0) { %>
			<li class="b_algo">
				<h2>
					没有查询到结果
				</h2>
			</li>			
		<% } %>	
	</ul>

<nav>
    <ul class="pagination pagination-lg ">
    	<% int nowPage= rpage.getPageNo();
    	   int totalPage = rpage.getTotalPage();
    	%>
            <% if(rpage.getPageNo() != 1) {%>
                <li>
                	<a href="#" onclick="toPage(<%=nowPage - 1 %>)" aria-label="Next">
		        		<span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            <% }%>

			<% for(int i=1;i<=totalPage;i++) { %>
					<% if (i == nowPage) { %>
                        <li class="active"><a href="#"><%=i %></a>
                        </li>			
					<% } else { %>
                            <li><a href="#" onclick="toPage(<%=i %>)"><%=i %></a>
                            </li>					
					<% }%>
             <% }%>


            <% if(nowPage < totalPage) {%>
                    <li>
                        <a href="#" onclick="toPage(<%=nowPage + 1%>)" aria-label="Next">
                            <span aria-hidden="false">&raquo;</span>
                        </a>
                    </li>
            <% }%>
    </ul>
</nav>

</div>




<script>
var queryStr = "<%=queryStr%>";
jQuery(document).ready(function(){
	
	jQuery("#query_str").val(queryStr);
	
	jQuery("#full_search_btn").bind("click", function() {
		var queryStr = jQuery("#query_str").val();
		if (queryStr.length > 0) {
			window.location = "/search/page/" + queryStr + "/1";
		}
	});
});

function toPage(pageNum) {
	var newReqUrl = "/search/page/" + queryStr + "/" + pageNum;
	
	window.location.href = newReqUrl;
}

</script>
