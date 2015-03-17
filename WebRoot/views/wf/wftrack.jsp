
<jsp:include page="../header_banner_no.jsp" flush="true" />

<% 
String wfid = "";
if (null != request.getAttribute("wfid")) {
	wfid = (String)request.getAttribute("wfid");	
} 

%>

<div class="outter-div">
<div class="content paper-border inner_div" style="margin-bottom:20px">

<div class="center">
	<h2>流程跟踪</h2>
</div>


<table class="table table-bordered table-hover" id = "myTable">
	<thead>
		<tr>
		    <th>序号</th>
		    <th>环节名称</th>
			<th>送交人</th>
			<th>送交时间</th>
			<th>接收人</th>
			<th>办理人</th>
			<th>办理时间</th>
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
		                { "mData": "nodename", 'sClass':'left'},   
		                { "mData": "fromusername",'sClass':'left'},
		                { "mData": "totime", 'sClass':'center'},   
		                { "mData": "tousername",'sClass':'left'},
		                { "mData": "doneusername", 'sClass':'left'},   
		                { "mData": "donetime",'sClass':'left'}
						];
	var reqUrl = "/wftrack/search";
	var defaultSort = "totime,desc";
	var dataTableParam = getPageParam(columnsDef, reqUrl, defaultSort);
	
    oTable = $('#myTable').dataTable(dataTableParam);	
});

/**
 * 设置查询的参数
 */
function setQueryParam(aoData) {
	aoData.push({"name":"search_query_wfid", "value":"<%=wfid%>"});	
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