
<jsp:include page="../header_banner_no.jsp" flush="true" />


<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>文章</h2>
	</div>

	<div class="btn-bar col-md-12 div-hide">
		<a href="/article/edit/_ADD_" class="btn btn-primary btn-sm">添加</a>
	</div>

	<div class="left-div col-md-2">
		<input type="hidden" id="search_query_chanid" value="">
		<ul id="leftTreeView" class="ztree"></ul>
		
	</div>
	
	<div class="right-div col-md-10">
		<div class="content paper-border inner_div" style="margin-bottom:20px">
		
		
		
		<table class="table table-bordered table-hover" id = "myTable">
			<thead>
				<tr>
				    <th>序号</th>
					<th>标题</th>
					<th>时间</th>
					<th>栏目</th>
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


var	oTable;
$(document).ready(function() {
	var columnsDef = [							//设定各列宽度
		            	{ "mData": "xuhao", 'sClass':'center', "bSortable": false, 'sWidth': '30px'},  
		                { "mData": "title", 'sClass':'left'},
		                { "mData": "atime", 'sClass':'center'},
		                { "mData": "chanId", 'sClass':'left'},
		                { "mData": "caozuo",'sClass':'center', "bSortable": false}
						];
	var reqUrl = "/article/search";
	var defaultSort = "atime,desc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
    
    initLeftTree();
    
    resetFrameHei();
    
    var loginFlag = jQuery(window.top.document).find(".user-logout").length;  //TODO
    if (loginFlag > 0) {
    	jQuery(".div-hide").show();
    }
});


/**
 * 设置查询的参数
 */
function setQueryParam(aoData) {
	var search_query_chanid = jQuery("#search_query_chanid").val();
	if (search_query_chanid.length > 0) {
		aoData.push({"name":"search_query_chanid", "value":search_query_chanid});	
	}
}


function initLeftTree() {
	var options = {};
	options.dict_id = "D_CHANNEL";
	options.div_id = "leftTreeView"; 
	options.field_id = "chanid";
	options.refreshFunc = querySearch;
	
	var treeDictLeft = new dr.treedict(options);
	treeDictLeft.showInPage();
}


function deleteItem(id) {
	if(confirm("确定删除该条记录！")) {
		var param = {};
		param.id = id;
		sendAjax("/article/delete/" + id, param, "get");
		
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