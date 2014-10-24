
<jsp:include page="header.jsp" flush="true" /> 


<script>

var param = {};
    param.NAME = "anan";
    param.VALUE = "value";
var result = sendAjax("/task/testRb", param);


alert(JsonToStr(result));


</script>


<%@ page import="java.util.List"%>
<%@ page import="com.dream.model.Task"%>

<% 
List<Task> allTask = (List<Task>)request.getAttribute("allTask");

for (Task task: allTask) {
	out.println(task.getTitle() + " " + task.getStart() + "<br>");
}
%>