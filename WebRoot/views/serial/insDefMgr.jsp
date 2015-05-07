
<jsp:include page="/views/serial/header_serial.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="/css/table.css"/>

<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>


<body>
<div class="outter-div">

<div class="header-table">
	<h2 class="page-header">
		功能定义
	</h2>

	<a href="/insDefMgr/edit/_ADD_" class="btn btn-primary btn-sm">添加</a>

</div>

<table class="table table-bordered table-hover" id = "myTable">
	<thead>
		<tr>
			<th class="xuhao no-sort">序号</th>
			<th>编码</th>
			<th>名称</th>
			<th>模板</th>
			<th>通道数</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>

</div>
<script>
var	oTable;
$(document).ready(function() {
	var columnsDef = [	//设定各列宽度
		            	{ "mData": "xuhao", 'sClass':'center', "bSortable": false},  
		                { "mData": "code", 'sClass':'left'},   
		                { "mData": "name",'sClass':'left'},
		                { "mData": "model",'sClass':'left'},
		                { "mData": "channum",'sClass':'right'}, 
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/insDefMgr/search";
	var defaultSort = "code,asc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);
});

/**
 * 查询
 */
function querySearch(){
	//刷新Datatable，会自动激发retrieveData
	oTable.fnDraw();	
}

/**
 * 设置查询的参数
 */
function setQueryParam(aoData) {
}


function deleteItem(id) {
	if(confirm("确定删除该条记录！")) {
		var param = {};
		param.id = id;
		sendAjax("/sick/delete/" + id, param, "get");
		
		querySearch();
	}
}

</script>

</body>


