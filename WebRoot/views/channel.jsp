
<jsp:include page="header.jsp" flush="true" />


<link id="cssfile" rel="stylesheet" href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.min.css">

<script type="text/javascript" src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>




<div class="content paper-border" style="margin-bottom:20px">

<table class="table" id = "myTable">
	<thead>
		<tr>
			<th>标题</th>
			<th>说明</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	
	
		<tr class="odd gradeX">
			<td>标题</td>
			<td>说明</td>
			<td class="center">开始时间</td>
			<td class="center">结束时间</td>
			<td class="center">编辑</td>
		</tr>
		
	</tbody>
</table>

</div>

<script>
$(document).ready(function(){
    $('#myTable').DataTable({
    	"bPaginate": false, //翻页功能
    	"bLengthChange": false, //改变每页显示数据数量
    	"bFilter": false, //过滤功能
    	"bSort": false, //排序功能
    	"bInfo": false,//页脚信息
    });
});
</script>