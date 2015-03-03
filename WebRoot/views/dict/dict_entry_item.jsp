

<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" href="/css/relate.css" />
<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>

<%@ page import="com.dream.model.DictEntry" %>

<% 
DictEntry entry;
if (null != request.getAttribute("itemObj")) {
	entry = (DictEntry)request.getAttribute("itemObj");	
} else {
	entry = new DictEntry();
}

%>
<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		字典项
	</h2>
</div>


<form class="form-horizontal" data-toggle="validator" id="formId">
	<div class="form-group col-sm-6">
		<label for="code" class="col-sm-4 control-label">编码</label> 
		<div class="col-sm-6">
	        <input type="text" class="form-control item-code" id="code" value="<%=entry.getCode()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="pcode" class="col-sm-4 control-label">父编码</label> 
		<div class="col-sm-6">
	      <input type="text" class="form-control item-code" id="pcode" value="<%=entry.getPcode()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>

	<div class="form-group col-sm-6">
		<label for="name" class="col-sm-4 control-label">名称</label> 
		<div class="col-sm-6">
	        <input type="text" class="form-control item-code" id="name" value="<%=entry.getName()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="esort" class="col-sm-4 control-label">排序</label> 
		<div class="col-sm-6">
	      <input type="number" class="form-control item-code" id="esort" value="<%=entry.getEsort()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="dlevel" class="col-sm-4 control-label">层级</label> 
		<div class="col-sm-6">
	      <input type="number" class="form-control item-code" id="dlevel" readonly value="<%=entry.getDlevel()%>">
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
	
	<input type="hidden" class="item-code" id="id" value="<%=entry.getId()%>">
	<input type="hidden" class="item-code" id="dictid" value="<%=entry.getDictid()%>">
</form>

</div>

<script>

var dictId = "<%=entry.getDictid()%>";

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

	
 	var rtnResult = sendAjax("/dictentry/save", param);
 	
 	window.location.href = "/dictentry/relate/" + dictId;
}

function back() {
	history.go(-1);
}

jQuery(document).ready(function(){
	resetFrameHei();
});

</script>
