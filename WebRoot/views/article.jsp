<jsp:include page="header.jsp" flush="true" />
<%@ page import="com.dream.model.Article" %>

<link rel="stylesheet" href="/css/editor/editor.css" />
<script charset="utf-8" src="/js/editor/kindeditor.js"></script>
<script charset="utf-8" src="/js/editor/lang/zh_CN.js"></script>

<script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="/js/plupload/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>
<script type="text/javascript" src="/js/plupload/i18n/zh_CN.js"></script>

<script type="text/javascript" src="/js/upload.js"></script>

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
	<tr>
		<td>选择图片</td>
		<td>
			<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
			<br />
			
			<div id="container">
			    <a id="pickfiles" href="javascript:;">[Select files]</a>
			</div>		
		</td>
	</tr>	
	<input type="hidden" id ="id" value="<%=article.getId()%>">
	<input type="hidden" id ="atime" value="<%=article.getAtime()%>">
	<input type="hidden" id ="imgids" value="<%=article.getImgids()%>">
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
	param.imgids = jQuery("#imgids").val();
	param.atime = jQuery("#atime").val();
	
	param.content = editor.html();
	param.summary = editor.text();
	
	sendAjax("/article/save", param);
	
	// 设置HTML内容
	//editor.html('HTML内容');
	window.location.href = "/";
}


function handleFileInfo(info) {
	var fileId = info.response;
	
	//更新imgids
	var oldImgids = jQuery("#imgids").val();
	if (oldImgids.length > 0) {
		oldImgids += ",";
	}
	jQuery("#imgids").val(oldImgids + fileId);
	
	//图片插入
	var imgHtml = "<img src='/file/"+info.response+"' style='max-width:100%;'/>";
	
	editor.insertHtml(imgHtml);
}

</script>
