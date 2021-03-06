<jsp:include page="/views/header.jsp" flush="true" />



    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <!-- Blog Entries Column -->
            <div class="col-md-8">

                <h1 class="page-header">
                    You got a dream, you got to protect it. 
                    <small></small>
                </h1>
				<div id="mainArticleDiv">
					
				</div>				

            </div>

            <!-- Blog Sidebar Widgets Column -->
            <div class="col-md-4">

                <!-- Blog Search Well -->
                <div class="well">
                    <h4>Blog Search</h4>
                    <div class="input-group">
                        <input type="text" class="form-control" id="query_str" placeholder="输入查询内容">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="full_search_btn">
                                <span class="glyphicon glyphicon-search"></span>
                        	</button>
                        </span>
                    </div>
                </div>

                <div class="well">
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="android_download_url">
                            	<li><h3><a href='http://pan.baidu.com/s/1jGIfvIu' target="_blank">ANDROID客户端下载</a></h3></li>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <div class="well">
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="channelsDiv">
                            </ul>
                        </div>
                    </div>
                </div>
                
                <!-- Blog Categories Well -->
                <div class="well">
                    <h4><a href="#" onclick="toTaskList()">未完成</a></h4>
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="tasksTodoDiv">
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="well">
                    <h4><a href="#" onclick="toTaskList()">已完成</a></h4>
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="list-unstyled" id="tasksFinishDiv">
                            </ul>
                        </div>
                    </div>
                </div>				
				
            </div>
        </div>

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright © yuananan.cn 2015 鄂ICP备15004825号</p>
                </div>
            </div>
        </footer>

    </div>


<script>


jQuery(document).ready(function(){
	showTaskTodo();
	showTaskFinish();
	showArticles();
	showChannel();
	
	jQuery("#full_search_btn").bind("click", function() {
		var queryStr = jQuery("#query_str").val();
		if (queryStr.length > 0) {
			window.location = "/search/page/" + queryStr + "/1";
		}
	});
});

function showArticles() {
	var article = sendAjax("/article/articles", {}, "post");

	jQuery(article._DATA_).appendTo(jQuery("#mainArticleDiv"));	
}


function showTaskTodo() {
	var tasksTodo = sendAjax("/task/taskTodo", {}, "get");

	var taskTodoStr = "";
	jQuery.each(tasksTodo , function (key, item) {
		taskTodoStr += "<li>" + item.title + "</li>";
	});

	jQuery(taskTodoStr).appendTo(jQuery("#tasksTodoDiv"));
}

function showTaskFinish() {
	var taskFinish = sendAjax("/task/taskFinish", {}, "get");

	var taskFinishStr = "";
	jQuery.each(taskFinish , function (key, item) {
		taskFinishStr += "<li>" + item.title + "</li>";
	});

	jQuery(taskFinishStr).appendTo(jQuery("#tasksFinishDiv"));

}

function showChannel() {
	var channels = sendAjax("/channel/channels", {}, "get");

	
	jQuery.each(channels , function (key, item) {
		var channelStr = "<li><h3><a href='#' id='"+item.code+"'>"+item.name+"</a></h3></li>";
		
		var channelObj = jQuery(channelStr).appendTo(jQuery("#channelsDiv"));
		
		channelObj.bind("click", function() {
			window.location.href = "/article/list";
		});
	});
	
	
	
	
	
}

function toTaskList() {
	window.location = "/task/list";	
}


</script>

</body>


</html>