
<jsp:include page="/views/serial/header_serial.jsp" flush="true" />
<link rel="stylesheet" href="/css/datepicker3.css" />
<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="/css/table.css"/>

<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>



<%@ page import="com.dream.controller.serial.model.InsBtn"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	InsBtn itemObj;
	if (null != request.getAttribute("itemObj")) {
		itemObj = (InsBtn) request.getAttribute("itemObj");
	} else {
		itemObj = new InsBtn();
	}
	

%>

<style>
body {
	background-color:gray;
	padding : 20px;
}

.outter-div {
	background-color : white;
	padding:20px;
	min-height: 700px;
}
.header-table {
	padding-bottom : 10px;
}

.header-table .page-header {
	text-align : center;
}

.input-group.date {
   padding-left: 15px;
   padding-right: 15px;
}

</style>
<body>
<div class="outter-div">

<div class="header-table">
	<h2 class="page-header">
		按钮设置
	</h2>
</div>


	<form class="form-horizontal" data-toggle="validator" id="formId_btn" style="padding-top:20px">
		<div class="form-group col-sm-6">
			<label for="code" class="col-sm-4 control-label">编码</label> 
			<div class="col-sm-6">
		        <input type="text" class="form-control item-code" id="code" value="<%=itemObj.getCode()%>" required data-error="该项必填">
		        <div class="help-block with-errors"></div>
		    </div>
		</div>
		<div class="form-group col-sm-6">
			<label for="name" class="col-sm-4 control-label">名称</label> 
			<div class="col-sm-6">
		        <input type="text" class="form-control item-code" id="name" value="<%=itemObj.getName()%>" required data-error="该项必填">
		        <div class="help-block with-errors"></div>
		    </div>
		</div>


		<div class="form-group col-sm-6">
			<label for="command" class="col-sm-4 control-label">点击发送命令</label> 
			<div class="col-sm-6">
		        <input type="text" class="form-control item-code" id="command" value="<%=itemObj.getCommand()%>">
		        <div class="help-block with-errors"></div>
		    </div>
		</div>
		<div class="form-group col-sm-6">
			<label for="cssplus" class="col-sm-4 control-label">扩展样式</label> 
			<div class="col-sm-6">
		        <input type="text" class="form-control item-code" id="cssplus" value="<%=itemObj.getCssplus()%>">
		        <div class="help-block with-errors"></div>
		    </div>
		</div>

	
		<div class="form-group col-sm-6">
			<label for="sortnum" class="col-sm-4 control-label">排序</label> 
			<div class="col-sm-6">
		      <input type="number" class="form-control item-code" id="sortnum" value="<%=itemObj.getSortnum()%>" data-error="填写数字">
		      <div class="help-block with-errors"></div>
		    </div>
		</div>
	
	
		<div class="form-group col-sm-12">
			<div style="text-align: center">
				<button type="button" class="btn btn-primary" id="submit" onclick="saveBtn()" value="保存" />
				<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
				保存
				</button>
				<button type="button" class="btn btn-primary" id="back_his" onclick="back()" value="返回" />
				<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
				返回
				</button>			
			</div>
		</div>
		<input type="hidden" class="item-code" id="id" value="<%=itemObj.getId()%>">
		<input type="hidden" class="item-code" id="insid" value="<%=itemObj.getInsid()%>">
	</form>




</div>
</body>

<script>
$(document).ready(function(){
	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd"
	});
	
});

function saveBtn() {
	jQuery("#formId_btn").submit();
	
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

	
 	var rtnResult = sendAjax("/insBtn/save", param);
 	
 	//window.location.href = "/insDefMgr/list";
}


function back() {
	history.go(-1);
}







</script>
