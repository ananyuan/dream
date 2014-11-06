
<jsp:include page="/views/header.jsp" flush="true" />

<!--------------Content--------------->
			<div class="content paper-border">
				<div id="mainArticleDiv" class="content-left">				
				</div>
				<div class="content-right">
					<div class="box">
						<div class="heading">
							<h2>未完成</h2>
						</div>
						<div id="tasksTodoDiv" class="content">
						</div>
					</div>
					<div class="box">
						<div class="heading">
							<h2>已完成</h2>
						</div>
						<div id="tasksFinishDiv" class="content">
						</div>
					</div>					
					<div class="box">
						<div class="heading">
							<h2>类别</h2>
						</div>
						<div id="channelsDiv" class="content">
						</div>
					</div>
				</div>
			</div>

<script>

var param = {"title":"asdfg1"};
	param.id = 12;
    param.title = "anan";
    param.descp = "value";
//    param.ttype = 23;
//    param.start = "a123451s";
//    param.endTime = "v987652v";

jQuery(document).ready(function(){
	showTaskTodo();
	showTaskFinish();
	showChannel();
	showArticles();
});

function showArticles() {
	var article = sendAjax("/article/articles", {}, "get");

	jQuery(article._DATA_).appendTo(jQuery("#mainArticleDiv"));	
}


function showTaskTodo() {
	var tasksTodo = sendAjax("/task/taskTodo", {}, "get");

	var taskTodoStr = "";
	jQuery.each(tasksTodo , function (key, item) {
		taskTodoStr += "<p>" + item.title + "</p>";
	});

	jQuery(taskTodoStr).appendTo(jQuery("#tasksTodoDiv"));
}

function showTaskFinish() {
	var taskFinish = sendAjax("/task/taskFinish", {}, "get");

	var taskFinishStr = "";
	jQuery.each(taskFinish , function (key, item) {
		taskFinishStr += "<p>" + item.title + "</p>";
	});

	jQuery(taskFinishStr).appendTo(jQuery("#tasksFinishDiv"));

}

function showChannel() {
	var channels = sendAjax("/channel/channels", {}, "get");

	var channelStr = "";
	jQuery.each(channels , function (key, item) {
		channelStr += "<p>"+item.name+"</p>";
	});
	
	jQuery(channelStr).appendTo(jQuery("#channelsDiv"));

}

</script>
