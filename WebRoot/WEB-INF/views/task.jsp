
<jsp:include page="header.jsp" flush="true" />

<!--------------Content--------------->
<section id="content">
	<div class="wrap-content">
		<div class="row block">
			<div id="main-content" class="col-2-3">
				<div id="mainArticleDiv" class="wrap-col">
				</div>
			</div>
			<div id="sidebar" class="col-1-3">
				<div class="wrap-col">
					<div class="box">
						<div class="heading"><h2>未完成</h2></div>
						<div id="tasksTodoDiv" class="content">
						</div>
					</div>
					<div class="box">
						<div class="heading"><h2>已完成</h2></div>
						<div id="tasksFinishDiv" class="content">
							<p>申请服务器</p>
						</div>
					</div>					
					<div class="box">
						<div class="heading"><h2>Categories</h2></div>
						<div id="channelsDiv" class="content">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


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

	jQuery(article.content).appendTo(jQuery("#mainArticleDiv"));	
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

	var channelStr = "<ul>";
	jQuery.each(channels , function (key, item) {
		channelStr += "<li><a href='#'>"+item.name+"</a></li>";
	});
	channelStr += "</ul>";
	
	jQuery(channelStr).appendTo(jQuery("#channelsDiv"));

}

</script>
