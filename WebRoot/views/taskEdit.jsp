

<jsp:include page="header.jsp" flush="true" />
<%@ page import="com.dream.model.Task" %>

<% 
Task task;
if (null != request.getAttribute("task")) {
	task = (Task)request.getAttribute("task");	
} else {
	task = new Task();
}

%>


<div class="content paper-border" style="margin-bottom:20px">

<table width="100%">
	<tr>
		<td>标题</td>
		<td><input type="text" id="title" size="80" value="<%=task.getTitle()%>" border=1></td>
	</tr>
	<tr>
		<td>描述</td>
		<td><textarea type="text" id="descp" rows="3" cols="80" value="<%=task.getDescp()%>" border=1><%=task.getDescp()%></textarea></textarea></td>
	</tr>
	<tr>
		<td>是否完成</td>
		<td>
			
		</td>
	</tr>	
	<tr>
		<td>开始时间</td>
		<td><input type="text" id="start" size="80" value="<%=task.getStart()%>" border=1></td>
	</tr>
	<tr>
		<td>结束时间</td>
		<td><input type="text" id="endTime" size="80" value="<%=task.getEnd()%>" border=1></td>
	</tr>		
	
	<input type="hidden" id ="id" value="<%=task.getId()%>">
</table>

  





<input type="button" id="submit" onclick="save()" value="保存"/>

</div>

<script>

function save() {
	var param = {};
	param.title = jQuery("#title").val();
	param.id = jQuery("#id").val();
	
	param.descp = jQuery("#descp").val();
	//param.start = jQuery("#start").val();
	//param.endTime = jQuery("#endTime").val();
	
	alert(JsonToStr(param));
	
	sendAjax("/task/save", param);
	
	window.location.href = "/task/list";
}

</script>
