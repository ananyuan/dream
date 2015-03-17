
<jsp:include page="../header_banner_no.jsp" flush="true" />


<div class="outter-div">
<div class="content paper-border inner_div" style="margin-bottom:20px">

<div class="center">
	<h2>流程定义</h2>
</div>

<a href="/wfdef/edit/_ADD_" class="btn btn-primary btn-sm">添加</a>



<table class="table table-bordered table-hover" id = "myTable">
	<thead>
		<tr>
		    <th>序号</th>
			<th>编号</th>
			<th>名称</th>
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
		                { "mData": "name",'sClass':'left', 'sWidth': '360px'},
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/wfdef/search";
	var defaultSort = "code,desc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
});

function deleteItem(id) {
	if(confirm("确定删除该条记录！")) {
		var param = {};
		param.id = id;
		sendAjax("/wfdef/delete/" + id, param, "get");
		
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

jQuery(document).ready(function(){
	resetFrameHei();
});


</script>