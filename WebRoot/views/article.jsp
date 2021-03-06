<jsp:include page="header_banner_no.jsp" flush="true" />
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

<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		发布文章
	</h2>
</div>

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
			<div class="input-group field-channame">
		        <input type="text" class="form-control item-code" id="channame" value="<%=article.getChanname()%>">
		        <span class="input-group-addon"><i class="glyphicon glyphicon-zoom-in"></i></span>
		        <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
		        <input type="hidden" class="item-code" id="chanId" value="<%=article.getChanId()%>">
	        </div>
	        <div class="help-block with-errors"></div>		
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
		<div class="col-sm-9" style="padding-top: 7px;">
			<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>

			<div id="container">
				<a id="pickfiles" href="javascript:;">[Select files]</a>
			</div>
			
			<div id="file_list" class="file-list">
				
			</div>
		</div>
	</div>

	<div class="form-group">
		<div style="text-align: center" class="col-sm-12">
			<button type="button" class="btn btn-primary" id="submit" onclick="save()" value="保存" />
			<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
			保存
			</button>			
			
		</div>
	</div>
	<input type="hidden" id="id" value="<%=article.getId()%>"> 
	<input type="hidden" id="atime" value="<%=article.getAtime()%>"> 
	<input type="hidden" id="imgids" value="<%=article.getImgids()%>">
</form>

</div>

<script>
	var id = "<%=article.getId()%>";
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			allowFileManager : true
		});
	});

	function save(refresh) {
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
		if (!refresh) {
			window.location.href = "/article/list";	
		}
	}

	function handleFileInfo(file, info) {
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
		
		save("noRefresh");
	}
	
	function getUploadParam() {
		return {"model":"", "data_id":id};
	}


	jQuery(document).ready(function(){
		jQuery(".field-channame").find(".glyphicon-zoom-in").bind("click", function(){
			var options = {};
			options.dict_id = "D_CHANNEL";
			options.field_id = "chanId";
			options.field_name = "channame";
			
			var treeDictLeft = new dr.treedict(options);
			treeDictLeft.showDialog();
		});		
		
		if (id != "_ADD_") {
			showFiles(id);	
		}
		
		resetFrameHei();
	});	
	
</script>
