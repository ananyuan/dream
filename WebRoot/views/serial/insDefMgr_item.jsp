
<jsp:include page="/views/serial/header_serial.jsp" flush="true" />
<link rel="stylesheet" href="/css/datepicker3.css" />
<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="/css/table.css"/>

<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>



<%@ page import="com.dream.controller.serial.model.InsDef"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	InsDef itemObj;
	if (null != request.getAttribute("itemObj")) {
		itemObj = (InsDef) request.getAttribute("itemObj");
	} else {
		itemObj = new InsDef();
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

	<div role="tabpanel">
		<ul class="nav nav-tabs" role="tablist" id="myTab">

			<li role="presentation" class="active"><a href="#main_card" aria-controls="main_card" role="tab" data-toggle="tab">定义信息</a></li>


			<li role="presentation"><a href="#ins_field" aria-controls="ins_field" role="tab" data-toggle="tab">字段</a></li>
			<li role="presentation"><a href="#ins_btn" aria-controls="ins_btn" role="tab" data-toggle="tab">操作</a></li>
		</ul>

		<div class="tab-content" id="myTab_content">

			<div role="tabpanel" class="tab-pane active" id="main_card">
					<form class="form-horizontal" data-toggle="validator" id="formId" style="padding-top:20px">
						<div class="form-group col-sm-6">
							<label for="id" class="col-sm-4 control-label">编码</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="id" value="<%=itemObj.getId()%>" required data-error="该项必填">
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
							<label for="channum" class="col-sm-4 control-label">通道数</label> 
							<div class="col-sm-6">
						      <input type="number" class="form-control item-code" id="channum" value="<%=itemObj.getChannum()%>" data-error="填写数字">
						      <div class="help-block with-errors"></div>
						    </div>
						</div>

						<div class="form-group col-sm-6">
							<label for="validateres" class="col-sm-4 control-label">验证返回结果</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="validateres" value="<%=itemObj.getValidateres()%>" required data-error="该项必填">
						        <div class="help-block with-errors"></div>
						    </div>
						</div>
					
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

			<div role="tabpanel" class="tab-pane" id="ins_field">
					<a href="/insField/edit/<%=itemObj.getId()%>/_ADD_" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加</a>
					<table class="table table-bordered table-hover" id = "table_field">
						<thead>
							<tr>
								<th class="xuhao no-sort">序号</th>
								<th>编码</th>
								<th>名称</th>
								<th>类型</th>
								<th>排序</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
			</div>
			<div role="tabpanel" class="tab-pane" id="ins_btn">
					<a href="/insBtn/edit/<%=itemObj.getId()%>/_ADD_" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加</a>
					<table class="table table-bordered table-hover" id = "table_btn">
						<thead>
							<tr>
								<th class="xuhao no-sort">序号</th>
								<th>编码</th>
								<th>名称</th>
								<th>排序</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
			</div>
		</div>
	</div>




</div>
</body>

<script>
var insid = "<%=itemObj.getId()%>";

$(document).ready(function(){
	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd"
	});
	
	showTableField();
	showTableBtn();
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

	
 	var rtnResult = sendAjax("/insDefMgr/save", param);
 	
 	//window.location.href = "/insDefMgr/list";
}


function back() {
	window.location.href = "/insDefMgr/list";
}





var	oTable;
var btnTable;
function showTableField() {	
	var columnsDef = [	//设定各列宽度
		            	{ "mData": "xuhao", 'sClass':'center', "bSortable": false},  
		                { "mData": "code", 'sClass':'left'},   
		                { "mData": "name",'sClass':'left'},
		                { "mData": "itemtype",'sClass':'left'},
		                { "mData": "sortnum",'sClass':'right'}, 
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/insField/search";
	var defaultSort = "sortnum,asc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#table_field').dataTable(dataTableParam);
    
}

function showTableBtn() {	
	var columnsDef = [	//设定各列宽度
		            	{ "mData": "xuhao", 'sClass':'center', "bSortable": false},  
		                { "mData": "code", 'sClass':'left'},   
		                { "mData": "name",'sClass':'left'},
		                { "mData": "sortnum",'sClass':'right'}, 
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/insBtn/search";
	var defaultSort = "sortnum,asc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
	btnTable = $('#table_btn').dataTable(dataTableParam);
    
}

/**
 * 查询
 */
function querySearch(){
	//刷新Datatable，会自动激发retrieveData
	try {
		oTable.fnDraw();	
	} catch (e) {
		
	}

	try {
		btnTable.fnDraw();	
	} catch (e) {
		
	}
}

/**
 * 设置查询的参数
 */
function setQueryParam(aoData) {
	aoData.push({"name":"search_query_insid", "value":insid});	
}


</script>
