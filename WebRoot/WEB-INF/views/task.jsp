
<jsp:include page="header.jsp" flush="true" />

<!--------------Content--------------->
<section id="content">
	<div class="wrap-content">
		<div class="row block">
			<div id="main-content" class="col-2-3">
				<div class="wrap-col">
					<article>
						<div class="heading">
							<h2><a href="#">什么是JSONP</a></h2>
						</div>
						<div class="content">
							<img src="images/img2.jpg" width="250px" height="100px"/>
							<p>web客户端通过与调用脚本一模一样的方式，来调用跨域服务器上动态生成的js格式文件（一般以JSON为后缀），显而易见，服务器之所以要动态生成JSON文件，目的就在于把客户端需要的数据装入进 [...]</p>
						</div>
						<div class="info">
							<p>2014-10-14 - <a href="#">01 Commnets</a></p>
						</div>
					</article>
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
});

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
