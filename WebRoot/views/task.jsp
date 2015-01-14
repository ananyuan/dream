
<jsp:include page="header.jsp" flush="true" />

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

<a href="/task/edit/_ADD_" class="btn btn-primary btn-sm">添加TASK</a>

<table class="table table-bordered" id = "myTable">
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
			<td><% if (task.getTtype() == 1){out.print("未完成");} else {out.print("已完成");} %></td>
			<td class="center"><%= task.getStart()%></td>
			<td class="center"><%= task.getEndTime()%></td>
			<td class="center">
				<a class="btn btn-primary btn-sm" href="#" onclick="edit(<%= task.getId()%>)">编辑</a>
				<a class="btn btn-danger btn-sm" href="#" onclick="remove(<%= task.getId()%>)">删除</a>
			</td>
		</tr>
	<% } %>	
	</tbody>
</table>

</div>

<script>

function edit(id) {
	window.location.href = "/task/edit/" + id;
}

function remove(id) {
	
}

</script>