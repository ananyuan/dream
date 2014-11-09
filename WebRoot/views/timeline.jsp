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


<script type="text/javascript" src="/js/jquery.masonry.min.js"></script>
<link rel="stylesheet" href="/css/timeline.css">

<div class="content paper-border" style="margin-bottom:20px">
	<div id="container">
		  <div class="timeline_container">
		    <div class="timeline">
		      <div class="plus"></div>
		    </div>
		  </div>
		  
		  <% for (Task task: tasks) {%>
			  <div class="item">
			    <div class="inner">
			    	<span style="display:inline-block">
				    	<b><%=task.getTitle()%></b>
			    	</span>
			    	<span style="display:inline-block">
				    	<p><%=task.getStart()%></p>
			    	</span>
			    	<p><%=task.getDescp()%></p>
			    </div>
			  </div>
		  <%} %>		  
		
		</div>		
</div>


<script>
//injecting arrow points
function arrowPoint(){
	var s = $("#container").find(".item");
	$.each(s,function(i,obj){
		var posLeft = $(obj).css("left");
		if(posLeft == "0px"){
			html = "<span class='rightCorner'></span>";
			$(obj).prepend(html);
		} else {
			html = "<span class='leftCorner'></span>";
			$(obj).prepend(html);
		}
	});
}

jQuery(document).ready(function(){
	$("#container").masonry({
		itemSelector:".item",
	});
	
	arrowPoint();
});

</script>
