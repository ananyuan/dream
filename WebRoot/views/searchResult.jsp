<!DOCTYPE html>
<html lang="en">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>袁安安的三分田-搜索结果</title>

    <!-- Bootstrap Core CSS -->
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="/css/zTreeStyle/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="/css/dream.css"/>
	
	<script type="text/javascript" src="/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    
	<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="/js/jquery.ztree.all-3.5.min.js"></script>
    
    
    <script type="text/javascript" src="/js/tools.js"></script>		
</head>


<style>
#b_results {
	display: inline-block;
	color: #333;
}
#b_results>li:first-child {
  padding-top: 7px;
}
#b_results>.b_algo {
  padding: 7px 0px 5px 0px;
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
	padding-left: 0px;
}

#r_content {
}

#r_content nav{
 padding-left:0px;
}

#search_div .padding20 {
    padding-left:0px;
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



<body style="background-color: #EEE;">
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" style="text-shadow: none; font-size: 24px;width: 240px;" href="/">袁安安的三分田</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                 <ul class="nav navbar-nav navbar-right">
                    <li> 
                        <a href="/views/about.jsp">关于</a>
                    </li>
                    <li>
                        <a href="/task/timeline">时间轴</a>
                    </li>
                    <li>
                        <a href="/user/login">登录</a>
                    </li>
                </ul>               
            </div>   
        </div>
    </nav>



<div class="container well" style="min-height:500px">

<div id="search_div" class="container">
    <div class="input-group padding20">
        <input type="text" class="form-control" id="query_str" placeholder="输入查询内容">
        <span class="input-group-btn">
            <button class="btn btn-default" type="button" id="full_search_btn">
                <span class="glyphicon glyphicon-search"></span>
        	</button>
        </span>
    </div>	
</div>
<div id="r_content" class="">
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

</div>

</body>

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
</html>