<link rel="stylesheet" href="/css/editor/editor.css" />
<script charset="utf-8" src="/js/editor/kindeditor.js"></script>
<script charset="utf-8" src="/js/editor/lang/zh_CN.js"></script>

<jsp:include page="header.jsp" flush="true" />

<!--------------Content--------------->
标题  <input type="text" id="title" value="" border=1>


<textarea id="editor_id" name="content" style="width:100%;height:300px;">
&lt;strong&gt;HTML内容&lt;/strong&gt;
</textarea>


<input type="button" id="submit" onclick="save()" value="保存"/>

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
	
	alert(editor.html());
	param.content = editor.html();
	param.summary = editor.text();
	
	sendAjax("/article/save", param);
	
	// 设置HTML内容
	//editor.html('HTML内容');	
}

</script>
