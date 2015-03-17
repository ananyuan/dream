
<jsp:include page="../header_banner_no.jsp" flush="true" />

<script type="text/javascript" src="/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="/js/highcharts/highmaps.js"></script>
<script type="text/javascript" src="/js/highcharts/map.js"></script>

<!-- 
查看页面  鼠标晃上去显示人员的信息(头像/部门/姓名/电话)
 -->

<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>座位查看</h2>
	</div>

	<div id="container" class="col-sm-8" style="height:480px;"></div>
	
	<div id="info" class="col-sm-4" style="height:400px;">
		<div id="seat_detail">
				<div class="form-group col-sm-12">
					<div class="col-sm-8 input-group input-username">
				      <input type="text" class="form-control item-code" id="username" value="">
				        <span class="input-group-addon"><i class="glyphicon glyphicon-zoom-in"></i></span>
				        <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
				        <input type="hidden" class="item-code" id="query_user_id" value="">
				    </div>
				</div>					
				<div class="form-group col-sm-12">
					<div style="text-align: center">
						<button type="button" class="btn btn-primary" id="query_seat" value="查询" />
						<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
						查询
						</button>
					</div>
				</div>			
		</div>
	</div>
</div>



</div>

<script>
$(function () {
	var mapchart = $('#container').highcharts('Map', {
        title : {
            text : '座位布局'
        },		
        tooltip: {
        	useHTML: true,
        	formatter: function() {
        		var seatid = this.key;
        		
        		var seatInfo = sendAjax("/seat/getSeat/" + seatid, {}, "get");
        		
        		if (seatInfo.user) {
        			return '<b>'+ seatInfo.user.username + " <br> " + seatInfo.user.deptname +'</b>';	
        		} else {
        			return "<b>暂未坐人</b>";
        		}
        	}
        },
        series: [
             	{
            		"type": "map",
            		"allowPointSelect": true,
                    "cursor": 'pointer',
                    "states": {
                        select: {
                            color: 'gray',
                            borderColor: 'black',
                            dashStyle: 'shortdot'
                        }
                    },
            		"data": [
            			{
            				"name": "path4715",
            				"id": "path4715",
            				"path": "M66,-984,68,-902,155,-902,154,-986Z"
            			},
            			{
            				"name": "path4717",
            				"id": "path4717",
            				"path": "M68,-882,68,-797,154,-797,154,-881z"
            			},
            			{
            				"name": "path4719",
            				"id": "path4719",
            				"path": "M68,-776,68,-690,154,-690,154,-776z"
            			},
            			{
            				"name": "path4721",
            				"id": "path4721",
            				"path": "M68,-669,68,-583,154,-583,154,-669z"
            			},
            			{
            				"name": "path4723",
            				"id": "path4723",
            				"path": "M195,-988,196,-903,281,-902,281,-984z"
            			},
            			{
            				"name": "path4725",
            				"id": "path4725",
            				"path": "M196,-881,196,-797,280,-798,281,-881z"
            			},
            			{
            				"name": "path4729",
            				"id": "path4729",
            				"path": "M690,-954,690,-646,1000,-644,1000,-954z"
            			},
            			{
            				"name": "path4731",
            				"id": "path4731",
            				"path": "M690,-632,695,-441,996,-444,994,-636z"
            			}
            		]
            	},
            	{
            		"type": "mapline",
            		"data": [
            			{
            				"name": "path4727",
            				"path": "M0,-997,0,-500,678,-498,680,-425L1000,-423,998,-958,690,-956,686,-1000Z"
            			}
            		]
            	}
            ]
    }).highcharts();

	
	function findUserSeat() {
		var userid = jQuery("#query_user_id").val();
		var username = jQuery("#username").val();
		
		if (userid.length == 0) {
			alert("选择要查询的人员");
			return;
		}
		
		//取消之前的选择
		var selecteds = mapchart.getSelectedPoints();
		jQuery.each(selecteds, function(key, item){
			item.select();
		});
		
		var seatInfo = sendAjax("/seat/findUserSeat/" + userid, {}, "get");
		
		if (seatInfo && seatInfo.length > 0) {
			jQuery.each(seatInfo, function(key, item){
				mapchart.get(item.seatid).select();		
			});
		} else {
			alert("没有发现 " + username + "的座位");
		}
	}

	function userDialog() {
		var param = {};
		
		var zNodes = sendAjax("/user/getUserListForTree", param, "get");
		
		
		var options = {};
		options.treeData = zNodes;
		options.title = "人员选择";
		options.parHandler = this;
		options.callBack = callBack;
		
		var xx = new dr.treeview(options);
		xx.render();
	}
	
	function callBack(id, name) {
    	$("#username").val(name);
        $("#query_user_id").val(id);   
	}

	jQuery(".input-username").find(".glyphicon-zoom-in").bind("click", function(){
		userDialog();
	});
	
	jQuery("#query_seat").bind("click", function(){
		findUserSeat();
	});	
});


jQuery(document).ready(function(){
	//重新设置高度
	resetFrameHei();
	
	jQuery(".highcharts-legend").hide();  //隐藏下面的 分类， series
	jQuery("text[text-anchor=end]").hide(); //隐藏highcharts.com
});






</script>