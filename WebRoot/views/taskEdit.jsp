

<jsp:include page="header.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>

<%@ page import="com.dream.model.Task" %>

<% 
Task task;
if (null != request.getAttribute("task")) {
	task = (Task)request.getAttribute("task");	
} else {
	task = new Task();
}

%>
<form class="form-horizontal">
  <div class="form-group">
    <label for="title" class="col-sm-2 control-label">标题</label>
    <div class="col-sm-8">
      <input type="text" class="form-control" id="title" value="<%=task.getTitle()%>">
    </div>
  </div>
  <div class="form-group">
    <label for="descp" class="col-sm-2 control-label">描述</label>
    <div class="col-sm-8">
      <textarea class="form-control" rows="3" id="descp" value="<%=task.getDescp()%>"><%=task.getDescp()%></textarea>
    </div>
  </div>
  <div class="form-group">
  	<label for="ttype" class="col-sm-2 control-label">描述</label>
    <div class="col-sm-8">
		<label class="radio-inline">
		  <input type="radio" name="ttype" id="inlineRadio1" <% if (task.getTtype() == 1){out.print("checked");} %> value="1"> 未完成
		</label>
		<label class="radio-inline">
		  <input type="radio" name="ttype" id="inlineRadio2" <% if (task.getTtype() == 2){out.print("checked");} %> value="2"> 已完成
		</label>
    </div>
  </div>
  <div class="form-group">
    <label for="start" class="col-sm-2 control-label">开始时间</label>
    <div class="col-sm-3 input-group date">
      <input type="text" class="form-control" id="start" value="<%=task.getStart()%>">
      <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
    </div>
  </div>
  <div class="form-group">
    <label for="endTime" class="col-sm-2 control-label">结束时间</label>
    <div class="col-sm-3 input-group date">
      <input type="text" class="form-control" id="endTime" value="<%=task.getEndTime()%>">
      <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
    </div>
  </div>  
  <input type="hidden" id ="id" value="<%=task.getId()%>">

  <div class="form-group">
  	<div style="text-align:center">
  	<input type="button" class="btn btn-primary" id="submit" onclick="save()" value="保存"/>
  	</div>
  </div>
  
  
</form>

<script>


$('.input-group.date').datepicker({
    format: "yyyy-mm-dd"
});


function save() {
	var param = {};
	param.title = jQuery("#title").val();
	param.id = jQuery("#id").val();
	
	param.descp = jQuery("#descp").val();
	param.ttype = $("input[name='ttype'][checked]").val();
	param.start = jQuery("#start").val();
	param.endTime = jQuery("#endTime").val();
	
	sendAjax("/task/save", param);
	
	window.location.href = "/task/list";
}

</script>
