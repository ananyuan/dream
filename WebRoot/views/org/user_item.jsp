

<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>



<%@ page import="com.dream.model.org.User" %>

<% 
User user;
if (null != request.getAttribute("itemObj")) {
	user = (User)request.getAttribute("itemObj");
} else {
	user = new User();
}

%>
<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		用户
	</h2>
</div>


<form class="form-horizontal" data-toggle="validator" id="formId">
	<div class="form-group col-sm-6">
		<label for="name" class="col-sm-4 control-label">姓名</label> 
		<div class="col-sm-7">
	        <input type="text" class="form-control item-code" id="username" value="<%=user.getUsername()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>

	<div class="form-group col-sm-6">
		<label for="loginname" class="col-sm-4 control-label">登录名</label> 
		<div class="col-sm-7">
	        <input type="text" class="form-control item-code" id="loginname" value="<%=user.getLoginname()%>" required data-error="该项必填">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>


	<div class="form-group col-sm-6">
		<label for="deptname" class="col-sm-4 control-label">部门</label> 
		<div class="col-sm-7">
			<div class="input-group field-pname">
		        <input type="text" class="form-control item-code" id="deptname" value="<%=user.getDeptname()%>">
		        <span class="input-group-addon"><i class="glyphicon glyphicon-zoom-in"></i></span>
		        <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
		        <input type="hidden" class="item-code" id="deptcode" value="<%=user.getDeptcode()%>">
	        </div>
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="usort" class="col-sm-4 control-label">排序</label> 
		<div class="col-sm-7">
	        <input type="number" class="form-control item-code" id="usort" value="<%=user.getUsort()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="sex" class="col-sm-4 control-label">性别</label> 
		<div class="col-sm-7">
	        <input type="number" class="form-control item-code" id="sex" value="<%=user.getSex()%>">
	        <div class="help-block with-errors"></div>
	    </div>
	</div>
	<div class="form-group col-sm-6">
		<label for="email" class="col-sm-4 control-label">邮箱</label> 
		<div class="col-sm-7">
	      <input type="text" class="form-control item-code" id="email" value="<%=user.getEmail()%>">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>	
	<div class="form-group col-sm-6">
		<label for="password" class="col-sm-4 control-label">密码</label> 
		<div class="col-sm-7">
	      <input type="text" class="form-control item-code" id="password" value="<%=user.getPassword()%>" required data-error="该项必填">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>	
			
	<input type="hidden" class="item-code" id ="id" value="<%=user.getId()%>">


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
var code = "<%=user.getId()%>";
	
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
	
 	var rtnResult = sendAjax("/user/save", param);
 	
 	window.location.href = "/user/list";
}

function back() {
	window.location.href = "/user/list";
}


function initDeptSelect() {
	jQuery(".field-pname").find(".glyphicon-zoom-in").bind("click", function(){
		var param = {};
		
		var zNodes = sendAjax("/dept/getDeptListForTree", param, "get");
		
/* 		jQuery.each(zNodes, function(index, item){
			item.pId = item.pcode;
			item.id = item.code;
			item.open = true;
		}); */
		
		var options = {};
		options.treeData = zNodes;
		options.title = "部门";
		options.parHandler = this;
		options.selectLeaf = false;
		options.callBack = callBackUser;
		
		var xx = new dr.treeview(options);
		xx.render();
	});
	
	jQuery(".field-pname").find(".glyphicon-remove").bind("click", function(){
		jQuery("#deptname").val("");
		jQuery("#deptcode").val("");
		
	});	
}

function callBackUser(id, name) {
	jQuery("#deptname").val(name);
	jQuery("#deptcode").val(id);	
}

jQuery(document).ready(function(){
	resetFrameHei();

	initDeptSelect();
});

</script>
