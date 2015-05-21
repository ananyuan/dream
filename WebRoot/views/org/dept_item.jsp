

<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>



<%@ page import="com.dream.model.org.Dept" %>

<% 
Dept dept;
if (null != request.getAttribute("itemObj")) {
	dept = (Dept)request.getAttribute("itemObj");
} else {
	dept = new Dept();
}

%>
<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		部门
	</h2>
</div>


<form class="form-horizontal" data-toggle="validator" id="formId">
	<div class="form-group col-sm-6">
		<label for="name" class="col-sm-4 control-label">名称</label> 
		<div class="col-sm-7">
	        <input type="text" class="form-control item-code" id="name" value="<%=dept.getName()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>


	<div class="form-group col-sm-6">
		<label for="pname" class="col-sm-4 control-label">父ID</label> 
		<div class="col-sm-7">
			<div class="input-group field-pname">
		        <input type="text" class="form-control item-code" id="pname" value="<%=dept.getPname()%>">
		        <span class="input-group-addon"><i class="glyphicon glyphicon-zoom-in"></i></span>
		        <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
		        <input type="hidden" class="item-code" id="pcode" value="<%=dept.getPcode()%>">
	        </div>
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="dsort" class="col-sm-4 control-label">排序</label> 
		<div class="col-sm-7">
	        <input type="number" class="form-control item-code" id="dsort" value="<%=dept.getDsort()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="dlevel" class="col-sm-4 control-label">层级</label> 
		<div class="col-sm-7">
	        <input type="number" readonly class="form-control item-code" id="dlevel" value="<%=dept.getDlevel()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="dpath" class="col-sm-4 control-label">路径</label> 
		<div class="col-sm-7">
	      <input type="text" readonly class="form-control item-code" id="dpath" value="<%=dept.getDpath()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>	
			
	<input type="hidden" class="item-code" id ="code" value="<%=dept.getCode()%>">


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
var code = "<%=dept.getCode()%>";
	
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
	
 	var rtnResult = sendAjax("/dept/save", param);
 	
 	window.location.href = "/dept/list";
}

function back() {
	window.location.href = "/dept/list";
}


function initDeptSelect() {
	jQuery(".field-pname").find(".glyphicon-zoom-in").bind("click", function(){
		var options = {};
		options.request_url = "/dept/getDeptListForTree";
		options.field_name = "pname"; 
		options.field_id = "pcode";
		
		var treeDictLeft = new dr.treedict(options);
		treeDictLeft.showDialog();
		
/* 		var param = {};
		
		var zNodes = sendAjax("/dept/getDeptListForTree", param, "get");
		
		
		var options = {};
		options.treeData = zNodes;
		options.title = "部门";
		options.parHandler = this;
		options.selectLeaf = false;
		options.callBack = callBack;
		
		var xx = new dr.treeview(options);
		xx.render(); */
	});
	
	jQuery(".field-pname").find(".glyphicon-remove").bind("click", function(){
		jQuery("#pname").val();
		jQuery("#pcode").val();
		
	});	
}

function callBack(id, name) {
	jQuery("#pname").val(name);
	jQuery("#pcode").val(id);	
}

jQuery(document).ready(function(){
	resetFrameHei();
	
	
	initDeptSelect();
});

</script>
