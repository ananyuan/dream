
<jsp:include page="../header_banner_no.jsp" flush="true" />

<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>部门</h2>
	</div>

	<div class="btn-bar col-md-12">
		<a href="/dept/edit/_ADD_" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加</a>	
		<hr>
	</div>
	

	<div class="left-div col-md-3">
		<input type="hidden" id="search_query_pcode" value="">
		<ul id="leftTreeView" class="ztree"></ul>
		
	</div>
	
	<div class="right-div col-md-9">
		<div class="content paper-border inner_div" style="margin-bottom:20px">
		
		
		
		<table class="table table-bordered table-hover" id = "myTable">
			<thead>
				<tr>
				    <th>序号</th>
					<th>名称</th>
					<th>排序</th>
					<th>层级</th>
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

<script>
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick
		}
};






var	oTable;
$(document).ready(function() {
	var columnsDef = [							//设定各列宽度
		            	{ "mData": "xuhao", 'sClass':'center', "bSortable": false, 'sWidth': '30px'},  
		                { "mData": "name", 'sClass':'left'},
		                { "mData": "dsort", 'sClass':'right'},
		                { "mData": "dlevel", 'sClass':'right'},
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/dept/search";
	var defaultSort = "dsort,asc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
    
    initLeftTree();
    
});

function initLeftTree() {
	var param = {};
	
	var zNodes = sendAjax("/dept/getDeptListForTree", param, "get");
	
/* 	jQuery.each(zNodes, function(index, item){
		item.pId = item.pcode;
		item.id = item.code;
		item.open = true;
	}); */
	
	jQuery.fn.zTree.init($("#leftTreeView"), setting, zNodes);
}

function onClick(event, treeId, treeNode, clickFlag) {
	console.log("[  onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");

	
	jQuery("#search_query_pcode").val(treeNode.id);
	
	querySearch();
}		


function deleteItem(id) {
	if(confirm("确定删除该条记录！")) {
		var param = {};
		param.id = id;
		sendAjax("/dept/delete/" + id, param, "get");
		
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

/**
 * 设置查询的参数
 */
function setQueryParam(aoData) {
	var search_query_pcode = jQuery("#search_query_pcode").val();
	if (search_query_pcode.length > 0) {
		aoData.push({"name":"search_query_pcode", "value":search_query_pcode});	
	}
}

jQuery(document).ready(function(){
	resetFrameHei();
});

</script>