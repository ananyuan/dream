<jsp:include page="header.jsp" flush="true" />
<%@ page import="com.dream.model.Article"%>
<%@ page import="com.dream.model.DictEntry"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<link rel="stylesheet" href="/css/editor/editor.css" />
<script charset="utf-8" src="/js/editor/kindeditor.js"></script>
<script charset="utf-8" src="/js/editor/lang/zh_CN.js"></script>

<script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="/js/plupload/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>
<script type="text/javascript" src="/js/plupload/i18n/zh_CN.js"></script>

<script type="text/javascript" src="/js/upload.js"></script>

<%
	Article article;
	List<DictEntry> chanList = new ArrayList();
	if (null != request.getAttribute("article")) {
		article = (Article) request.getAttribute("article");
	} else {
		article = new Article();
	}
	if (null != request.getAttribute("chanList")) {
		chanList = (List) request.getAttribute("chanList");
	}
%>
<form class="form-horizontal">
	<div class="form-group">
		<label for="title" class="col-sm-2 control-label">标题</label> 
		<div class="col-sm-9">
	      <input type="text" class="form-control" id="title" value="<%=article.getTitle()%>">
	    </div>
	</div>
	<div class="form-group">
		<label for="chanId" class="col-sm-2 control-label">栏目</label> 
		<div class="col-sm-3">
	        <select class="form-control" id="chanId">
	        	<% for (DictEntry channel: chanList) {
	        		String option = "<option value='"+channel.getCode()+"' ";
	        		if (channel.getCode().equals(String.valueOf(article.getChanId()))) {
	        			option += " selected=\"selected\" ";
	        		}
	        		option +=  ">"+channel.getName()+"</option>";
	        		
	        		System.out.println("---------------------" + option);
	        		
	        		out.print(option);
	        	} %>
	        </select>
	    </div>
		<label for="sortnum" class="col-sm-2 control-label">排序</label> 
		<div class="col-sm-3">
	      <input type="text" class="form-control" id="sortnum" value="<%=article.getSortnum()%>">
	    </div>
	</div>	
	

	<div class="form-group">
		<label for="editor_id" class="col-sm-2 control-label">正文</label>
		<div class="col-sm-9">
		<textarea id="editor_id" name="content" class="form-control" style="height: 300px;"><%=article.getContent()%></textarea>
		</div>
	</div>
	
	
	<div class="form-group">
		<label for="container" class="col-sm-2 control-label">选择图片</label>
		<div class="col-sm-9">
			<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>

			<div id="container">
				<a id="pickfiles" href="javascript:;">[Select files]</a>
			</div>
		</div>
	</div>

	<div class="form-group col-sm-12">
		<div style="text-align: center">
			<input type="button" class="btn btn-primary" id="submit" onclick="save()" value="保存" />
		</div>
	</div>
	<input type="hidden" id="id" value="<%=article.getId()%>"> 
	<input type="hidden" id="atime" value="<%=article.getAtime()%>"> 
	<input type="hidden" id="imgids" value="<%=article.getImgids()%>">
</form>



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
		param.chanId = jQuery("#chanId").val();

		param.sortnum = jQuery("#sortnum").val();
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
		var imgHtml = "<img src='/file/" + info.response + "' style='max-width:100%;'/>";

		editor.insertHtml(imgHtml);
	}
</script>
