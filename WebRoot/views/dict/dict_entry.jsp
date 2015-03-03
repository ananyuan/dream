
<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" href="/css/relate.css" />

<% 
String dictId = "";
if (null != request.getAttribute("dictId")) {
	dictId = (String)request.getAttribute("dictId");	
} 

%>

<div class="outter-div">
<div class="content paper-border inner_div" style="margin-bottom:20px">

<a href="/dictentry/edit/<%=dictId%>/_ADD_" class="btn btn-primary btn-sm">添加</a>

<table class="table table-bordered table-hover" id = "myTable">
	<thead>
		<tr>
		    <th>序号</th>
			<th>编码</th>
			<th>父编码</th>
			<th>名称</th>
			<th>排序</th>
			<th>字典</th>
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
		                { "mData": "code", 'sClass':'left'},
		                { "mData": "pcode", 'sClass':'left'},   
		                { "mData": "name",'sClass':'left'},
		                { "mData": "esort",'sClass':'right'},
		                { "mData": "dictid",'sClass':'left'},
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/dictentry/search";
	var defaultSort = "esort,asc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
});

/**
 * 设置查询的参数
 */
function setQueryParam(aoData) {
	aoData.push({"name":"search_query_dictid", "value":"<%=dictId%>"});	
}

function deleteItem(id) {
	if(confirm("确定删除该条记录！")) {
		var param = {};
		param.id = id;
		sendAjax("/dictentry/delete/" + id, param, "get");
		
		querySearch();
	}
}

/**
 * 查询
 */
function querySearch(){
	//刷新Datatable，会自动激发retrieveData
	oTable.fnDraw();	
}

</script>