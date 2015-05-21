

<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>

<%@ page import="com.dream.model.Menu" %>

<% 
Menu menu;
if (null != request.getAttribute("itemObj")) {
	menu = (Menu)request.getAttribute("itemObj");	
} else {
	menu = new Menu();
}

%>
<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		菜单
	</h2>
</div>


<form class="form-horizontal" data-toggle="validator" id="formId">
	<div class="form-group col-sm-6">
		<label for="name" class="col-sm-4 control-label">名称</label> 
		<div class="col-sm-6">
	        <input type="text" class="form-control item-code" id="name" value="<%=menu.getName()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="name" class="col-sm-4 control-label">类型</label> 
		<div class="col-sm-6">
	      <div class="form-control">
			<label class="radio-inline">
			  <input type="radio" name="mtype" id="inlineRadio1" <% if (menu.getMtype().equals("FOLDER")){out.print("checked");} %> value="FOLDER"> 目录
			</label>
			<label class="radio-inline">
			  <input type="radio" name="mtype" id="inlineRadio2" <% if (menu.getMtype().equals("LEAF")){out.print("checked");} %> value="LEAF"> 叶子
			</label>
		  </div>
		  <div class="help-block with-errors"></div>	
	    </div>
	</div>

	<div class="form-group col-sm-6">
		<label for="pid" class="col-sm-4 control-label">父ID</label> 
		<div class="col-sm-6">
	        <input type="number" class="form-control item-code" id="pid" value="<%=menu.getPid()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="url" class="col-sm-4 control-label">URL</label> 
		<div class="col-sm-6">
	        <input type="text" class="form-control item-code" id="url" value="<%=menu.getUrl()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="mclass" class="col-sm-4 control-label">样式</label> 
		<div class="col-sm-6">
	        <input type="text" class="form-control item-code" id="mclass" value="<%=menu.getMclass()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="msort" class="col-sm-4 control-label">排序</label> 
		<div class="col-sm-6">
	      <input type="number" class="form-control item-code" id="msort" value="<%=menu.getMsort()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="mlevel" class="col-sm-4 control-label">层级</label> 
		<div class="col-sm-6">
	        <input type="number" readonly class="form-control item-code" id="mlevel" value="<%=menu.getMlevel()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="mpath" class="col-sm-4 control-label">路径</label> 
		<div class="col-sm-6">
	      <input type="text" readonly class="form-control item-code" id="mpath" value="<%=menu.getMpath()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>	
			
	<input type="hidden" class="item-code" id ="id" value="<%=menu.getId()%>">


	<div class="form-group col-sm-12">
		<div style="text-align: center">
			<button type="button" class="btn btn-primary" id="submit" onclick="save()" value="保存" />
			<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
			保存
			</button>
			<button type="button" class="btn btn-primary" id="back_his" onclick="back()" value="返回" />
			<span class="glyphicon glyphicon-backward" aria-hidden="true"></span>
			返回
			</button>			
		</div>
	</div>
</form>

</div>




<script>
var menuId = "<%=menu.getId()%>";
	
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

	param.mtype = $("input[name='mtype'][checked]").val();
	
 	var rtnResult = sendAjax("/menu/save", param);
 	
 	window.location.href = "/menu/list";
}

function back() {
	window.location.href = "/menu/list";
}

jQuery(document).ready(function(){
	resetFrameHei();
});

</script>
