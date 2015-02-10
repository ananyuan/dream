

<jsp:include page="../header.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>

<%@ page import="com.dream.model.Dict" %>

<% 
Dict dict;
if (null != request.getAttribute("itemObj")) {
	dict = (Dict)request.getAttribute("itemObj");	
} else {
	dict = new Dict();
}

%>
<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		字典
	</h2>
</div>


<form class="form-horizontal" data-toggle="validator" id="formId">
	<div class="form-group col-sm-6">
		<label for="code" class="col-sm-4 control-label">编码</label> 
		<div class="col-sm-6">
	        <input type="text" class="form-control item-code" id="code" value="<%=dict.getCode()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="name" class="col-sm-4 control-label">名称</label> 
		<div class="col-sm-6">
	      <input type="text" class="form-control item-code" id="name" value="<%=dict.getName()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>


	<div class="form-group col-sm-12">
		<div style="text-align: center">
			<button type="button" class="btn btn-primary" id="submit" onclick="save()" value="保存" />
			<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
			保存
			</button>
			<button type="button" class="btn btn-primary" id="back_his" onclick="back()" value="返回" />
			<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
			返回
			</button>			
		</div>
	</div>
</form>


<iframe id="relate_iframe" src="">


</iframe>

</div>




<script>
var dictId = "<%=dict.getCode()%>";

$(document).ready(function() {
	if (dictId.length > 0) {
		//关联列表
		jQuery("#relate_iframe").attr("src", "/dictentry/relate/" + dictId);
		
		resetFrameHei();
	} else {
		jQuery("#relate_iframe").hide();
	}
});
	
function save() {
	jQuery("#formId").submit();
	
	var xx = jQuery(".has-error").length;
	if (xx > 0) {
		alert("检查输入项");
		
		return;
	}
	
	var param = {};
	jQuery(".item-code").each(function(){
		var itemObj = $(this);
		var fieldName = itemObj.attr("id");
		var fieldValue = itemObj.val();
		
		param[fieldName] = fieldValue;
	});

	
 	var rtnResult = sendAjax("/dict/save", param);
 	
 	window.location.href = "/dict/list";
}

function back() {
	window.location.href = "/dict/list";
}

</script>
