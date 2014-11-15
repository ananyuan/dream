
<jsp:include page="header.jsp" flush="true" />


<link id="cssfile" rel="stylesheet" href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.min.css">

<script type="text/javascript" src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>

<%@ page import="com.dream.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<% 
List<Task> tasks;
if (null != request.getAttribute("allTask")) {
	tasks = (List<Task>)request.getAttribute("allTask");	
} else {
	tasks = new ArrayList<Task>();
}

%>


<div class="content paper-border" style="margin-bottom:20px">

<table class="table" id = "myTable">
	<thead>
		<tr>
			<th>标题</th>
			<th>说明</th>
			<th>状态</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	
	<% for (Task task: tasks) {%>
		<tr class="odd gradeX">
			<td><%= task.getTitle()%></td>
			<td><%= task.getDescp()%></td>
			<td><%= task.getType()%></td>
			<td class="center"><%= task.getStart()%></td>
			<td class="center"><%= task.getEnd()%></td>
			<td class="center">
				<a href="#" onclick="edit(<%= task.getId()%>)">编辑</a>
				<a href="#" onclick="remove(<%= task.getId()%>)">删除</a>
			</td>
		</tr>
	<% } %>	
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

function edit(id) {
	window.location.href = "/task/edit/" + id;
}

function remove(id) {
	
}

</script>