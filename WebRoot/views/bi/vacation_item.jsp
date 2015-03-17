

<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>
<%
String id = "";
String niid = "0";
if (null != request.getAttribute("id")) {
	id = (String)request.getAttribute("id");	
} 

if (null != request.getAttribute("niid")) {
	niid = (String)request.getAttribute("niid").toString();	
} 
%>


<div class="outter-div">
<div class="button_bar">
	<button type="button" class="btn btn-primary" id="submit" onclick="save()" value="保存">
	<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
	保存
	</button>
	<button type="button" class="btn btn-primary" id="back_his" onclick="back()" value="返回">
	<span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span>
	返回
	</button>
</div>


<div class="header-table">
	<h2 class="page-header">
		请假单
	</h2>
</div>


<form class="form-horizontal" data-toggle="validator" id="formId">

	<div class="form-group col-sm-12">
		<label for="title" class="col-sm-2 control-label">标题</label> 
		<div class="col-sm-8">
	      <input type="text" class="form-control item-code" id="title" value="">
	      <div class="help-block with-errors"></div>
	    </div>
	</div>
	
	<div class="form-group col-sm-6">
		<label for="username" class="col-sm-4 control-label">请假人</label> 
		<div class="col-sm-6 ">
		    <div class="input-group input-username">
		    <input type="text" class="form-control item-code" id="username" value="">
	        <span class="input-group-addon"><i class="glyphicon glyphicon-zoom-in"></i></span>
	        <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
	        <input type="hidden" class="item-code" id="usercode" value="">
	      	<div class="help-block with-errors"></div>
	      	</div>
	    </div>
	</div>	
	
	<div class="form-group col-sm-6">
		<label for="vtime" class="col-sm-4 control-label">时间</label> 
		<div class="col-sm-6 input-group date">
	      <input type="text" class="form-control item-code" id="vtime" value="">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	      <div class="help-block with-errors"></div>
	    </div>
	</div>	

	<div class="form-group col-sm-12">
		<label for="memo" class="col-sm-2 control-label">说明</label>
		<div class="col-sm-8 area-left-padding">
		<textarea id="memo" name="memo" class="form-control item-code" style="height: 200px;"></textarea>
		</div>
	</div>	

	<input type="hidden" class="item-code" id="id" value="">
	<input type="hidden" class="item-code" id="wfid" value="">
</form>


<iframe id="relate_iframe" src="">


</iframe>

</div>




<script>
var id="<%=id%>";
var niid="<%=niid%>";



$(document).ready(function() {
	resetFrameHei();
	if (id != "_ADD_") {
		var param = {};
		var rtnResult = sendAjax("/vacation/editJson/" + id + "/" + niid, param, "get");
		
		initPageItems(rtnResult);
		debugger;
		addWfTrackBtn(rtnResult.itemObj.wfid);
	}

	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd"
	});	
	
	jQuery(".input-username").find(".glyphicon-zoom-in").bind("click", function(){
		var param = {};
		
		var zNodes = sendAjax("/user/getUserListForTree", param, "get");
		
		var options = {};
		options.treeData = zNodes;
		options.title = "选择送交人员";
		options.parHandler = this;
		options.callBack = function(touser, name) {
			jQuery("#usercode").val(touser);
			jQuery("#username").val(name);
		};
		
		var xx = new dr.treeview(options);
		xx.render();
	});
});

function addWfTrackBtn(wfid) {
	var btnString = "<button type='button' id='wftrack' class='btn btn-primary right-margin-4' value='流程跟踪'><span class='glyphicon glyphicon-road' aria-hidden='true'></span>&nbsp;&nbsp;流程跟踪</button>";
	
	var btnObj = jQuery(btnString).appendTo(jQuery(".button_bar"));
	btnObj.bind("click", function(){
		window.open("/wftrack/list/" + wfid);
	});	
}

function initPageItems(dataObj) {
	//填上默认值
	jQuery.each(dataObj.itemObj, function(key, value){
		jQuery("#" + key).val(value);
	});
	
	//构造按钮
	jQuery.each(dataObj.nextSteps, function(key, item){
		var btnString = "<button type='button' id='"+item.nodeCode+"' class='btn btn-primary right-margin-4' value='"+item.nodeName+"'><span class='glyphicon glyphicon-random' aria-hidden='true'></span>&nbsp;&nbsp;"+item.nodeName+"</button>";
		
		var btnObj = jQuery(btnString).appendTo(jQuery(".button_bar"));
		btnObj.bind("click", function(){
			var param = {};
			
			var zNodes = sendAjax("/user/getUserListForTree", param, "get");
			
			var options = {};
			options.treeData = zNodes;
			options.title = "选择送交人员";
			options.parHandler = this;
			options.callBack = function(touser, name) {
				var sendParam = {};
				sendParam.touser = touser;
				sendParam.nextNode = item.nodeCode;
				sendParam.niid = niid;
				sendParam.dataid = id;
				sendParam.title = jQuery("#title").val();
				
				var rtnObj = sendAjax("/wf/toNext", sendParam);
				
				alert("送交成功");
				
				window.top.location.href = "/index/dashboard";
			};
			
			var xx = new dr.treeview(options);
			xx.render();
		});
	});	
	
	
}

	
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

	
 	var rtnResult = sendAjax("/vacation/save", param);
 	
}

function back() {
	//window.location.href = "/dashboard.jsp";
	
	window.history.go(-1);
}

</script>
