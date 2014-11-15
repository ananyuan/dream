<link rel="stylesheet" href="/css/editor/editor.css" />
<script charset="utf-8" src="/js/editor/kindeditor.js"></script>
<script charset="utf-8" src="/js/editor/lang/zh_CN.js"></script>

<jsp:include page="header.jsp" flush="true" />
<%@ page import="com.dream.model.Article" %>

<% 
Article article;
if (null != request.getAttribute("article")) {
	article = (Article)request.getAttribute("article");	
} else {
	article = new Article();
}

%>


<div class="content paper-border" style="margin-bottom:20px">

<table width="100%">
	<tr>
		<td>标题</td>
		<td><input type="text" id="title" size="80" value="<%=article.getTitle()%>" border=1></td>
	</tr>
	<tr>
		<td colspan="2">
			<textarea id="editor_id" name="content" style="width:100%;height:300px;">
			<%=article.getContent()%>
			</textarea>		
		</td>
	</tr>
	<input type="hidden" id ="id" value="<%=article.getId()%>">
</table>

  





<input type="button" id="submit" onclick="save()" value="保存"/>

</div>

<script>
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="content"]', {
		allowFileManager : true
	});
});

function save() {
	var param = {};
	param.title = jQuery("#title").val();
	param.id = jQuery("#id").val();
	
	param.content = editor.html();
	param.summary = editor.text();
	
	sendAjax("/article/save", param);
	
	// 设置HTML内容
	//editor.html('HTML内容');
	window.location.href = "/";
}

</script>