
<jsp:include page="header.jsp" flush="true" />

<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>

<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>

<style>
body {
	background-color:gray;
	padding : 70px 20px 20px 20px;
}

.outter-div {
	background-color : white;
	padding:20px;
	min-height: 500px;
}

</style>

<div class="outter-div">
<div class="content paper-border inner_div" style="margin-bottom:20px">

<a href="/task/edit/_ADD_" class="btn btn-primary btn-sm">添加TASK</a>

<table class="table table-bordered table-hover" id = "myTable">
	<thead>
		<tr>
		    <th>序号</th>
			<th>标题</th>
			<th>说明</th>
			<th>状态</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>

</div>

</div>

<script>

var	oTable;
$(document).ready(function() {
	var columnsDef = [							//设定各列宽度
		            	{ "mData": "xuhao", 'sClass':'center', "bSortable": false, 'sWidth': '30px'},  
		                { "mData": "title", 'sClass':'left'},   
		                { "mData": "descp",'sClass':'left', 'sWidth': '360px'},
		                { "mData": "ttype",'sClass':'center'}, 
		                { "mData": "start",'sClass':'center'}, 
		                { "mData": "endTime",'sClass':'center'}, 
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/task/search";
	var defaultSort = "start,desc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
});

</script>