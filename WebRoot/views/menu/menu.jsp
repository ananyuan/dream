
<jsp:include page="../header_banner_no.jsp" flush="true" />

<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>菜单</h2>
	</div>

	<div class="btn-bar col-md-12">
		<a href="/menu/edit/_ADD_" class="btn btn-primary btn-sm">添加</a>	
		<a href="javascript:void(0);" onclick="hihi()">dialog</a>
		<a href="javascript:void(0);" id="dialog_postion" onclick="userDialog()">dialog</a>
		
		<hr>
	</div>
	

	<div class="left-div col-md-3">
		<input type="hidden" id="search_query_pid" value="">
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
					<th>是否目录</th>
					<th>URL</th>
					<th>样式</th>
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
		                { "mData": "msort", 'sClass':'right'},
		                { "mData": "mtype", 'sClass':'center'},
		                { "mData": "url", 'sClass':'left'},
		                { "mData": "mclass", 'sClass':'left'},
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/menu/search";
	var defaultSort = "msort,asc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
    
    initLeftTree();
    
});

function initLeftTree() {
	var param = {};
	
	var zNodes = sendAjax("/menu/getMenuList", param, "get");
	
	jQuery.each(zNodes, function(index, item){
		item.pId = item.pid;
		item.open = true;
		//item.iconSkin = "pIcon01";
	});
	
	jQuery.fn.zTree.init($("#leftTreeView"), setting, zNodes);
}

function onClick(event, treeId, treeNode, clickFlag) {
	console.log("[  onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");

	
	jQuery("#search_query_pid").val(treeNode.id);
	
	querySearch();
}		


function deleteItem(id) {
	if(confirm("确定删除该条记录！")) {
		var param = {};
		param.id = id;
		sendAjax("/menu/delete/" + id, param, "get");
		
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
	var search_query_pid = jQuery("#search_query_pid").val();
	if (search_query_pid.length > 0) {
		aoData.push({"name":"search_query_pid", "value":search_query_pid});	
	}
}

jQuery(document).ready(function(){
	resetFrameHei();
});

function hihi() {
	var param = {};
	
	var zNodes = sendAjax("/menu/getMenuList", param, "get");
	
	jQuery.each(zNodes, function(index, item){
		item.pId = item.pid;
		item.open = true;
	});
	
	var options = {};
	options.treeData = zNodes;
	options.title = "菜单";
	options.parHandler = this;
	options.callBack = callBack;
	
	var xx = new dr.treeview(options);
	xx.render();
}

function userDialog() {
	var param = {};
	
	var zNodes = sendAjax("/user/getUserListForTree", param, "get");
	
/* 	jQuery.each(zNodes, function(index, item){
		item.pId = item.pid;
		item.open = true;
	}); */
	
	var options = {};
	options.treeData = zNodes;
	options.title = "菜单";
	options.parHandler = this;
	options.callBack = callBack;
	
	var xx = new dr.treeview(options);
	xx.render();
}


function callBack(id, name) {
	alert(id + "   " + name);
}
</script>