
<jsp:include page="header.jsp" flush="true" />

<%@ page import="com.dream.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>


<% 
List<HashMap<String, Object>> dataList;
if (null != request.getAttribute("_DATA_")) {
	dataList = (List<HashMap<String, Object>>)request.getAttribute("_DATA_");	
} else {
	dataList = new ArrayList<HashMap<String, Object>>();
}

%>


<div class="content paper-border" style="margin-bottom:20px">

<table class="table table-bordered" id = "myTable">
	<thead>
		<tr>
			<th>content</th>
			<th>id</th>
			<th>title</th>
			<th>create_time</th>
			<th>local_url</th>
		</tr>
	</thead>
	<tbody>
	
	<% for (HashMap<String, Object> dataMap: dataList) {%>
		<tr class="odd gradeX">
			<% 
	        Iterator<String> it = dataMap.keySet().iterator();
	        while (it.hasNext()) {
	            String key = (String) it.next();
	            
	            String colVal = dataMap.get(key).toString();
	            if (key.equals("title")) {
	            	colVal = "<a href='"+dataMap.get("url")+"'>" + colVal + "</a>";
	            }
	            
	            String outStr = "<td>" + colVal + "</td>";
	            
	            out.print(outStr);
	        }
			%>
			
		</tr>
	<% } %>	
	</tbody>
</table>

</div>

<script>


</script>