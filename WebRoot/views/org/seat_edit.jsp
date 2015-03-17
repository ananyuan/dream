
<jsp:include page="../header_banner_no.jsp" flush="true" />

<script type="text/javascript" src="/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="/js/highcharts/highmaps.js"></script>
<script type="text/javascript" src="/js/highcharts/map.js"></script>

<!-- 
编辑页面， 建立座位和人员的关联
 -->


<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>座位设置</h2>
	</div>

	<div id="container" class="col-sm-8" style="height:480px;"></div>
	
	<div id="info" class="col-sm-4" style="height:400px;">
		<div id="seat_detail" style="display:none">

			<form class="form-horizontal" data-toggle="validator" id="formId">
				<div class="form-group col-sm-12">	    
					<label for="seatid" class="col-sm-4 control-label">id</label> 
					<div class="col-sm-8 input-group">
				      <input type="text" class="form-control item-code" id="seatid" value="" readonly>
				    </div>
				</div>	
				<div class="form-group col-sm-12">	    
					<label for="name" class="col-sm-4 control-label">名称</label> 
					<div class="col-sm-8 input-group">
				      <input type="text" class="form-control item-code" id="name" value="">
				    </div>
				</div>
				<div class="form-group col-sm-12">	    
					<label for="sdesc" class="col-sm-4 control-label">说明</label> 
					<div class="col-sm-8 input-group">
				      <input type="text" class="form-control item-code" id="sdesc" value="">
				    </div>
				</div>				
				<div class="form-group col-sm-12">	    
					<label for="username" class="col-sm-4 control-label">人员</label> 
					<div class="col-sm-8 input-group input-username">
				      <input type="text" class="form-control item-code" id="username" value="">
				        <span class="input-group-addon"><i class="glyphicon glyphicon-zoom-in"></i></span>
				        <span class="input-group-addon"><i class="glyphicon glyphicon-remove"></i></span>
				        <input type="hidden" class="item-code" id="userid" value="">
				    </div>
				</div>		
			
				<div class="form-group col-sm-12">
					<div style="text-align: center">
						<button type="button" class="btn btn-primary" id="save_seat" value="保存" />
						<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
						保存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>



</div>

<script>
$(function () {
	var mapchart;
	
    

	
    Highcharts.wrap(Highcharts.Point.prototype, 'select', function (proceed) {
        proceed.apply(this, Array.prototype.slice.call(arguments, 1));

        
        var classValue = jQuery("path[fill=gray]").attr("class");
        
        if (classValue) {
            var seatid = classValue.trim().replace("highcharts-name-", "");
            
            var seatInfo = sendAjax("/seat/getSeat/" + seatid, {}, "get");
            
            $("#seat_detail").show();
            $("#seatid").val(seatid);
            if (seatInfo.user) {
                $("#username").val(seatInfo.user.username);
                $("#userid").val(seatInfo.user.id);            	
            } else {
            	$("#username").val("");
                $("#userid").val("");   
            }
            
            $("#name").val(seatInfo.name);
            $("#sdesc").val(seatInfo.sdesc);
        } else {
        	$("#seat_detail").hide();
            $("#seatid").val("");
            $("#username").val("");
            $("#userid").val("");   
            $("#name").val();
            $("#sdesc").val();
        }

        
        /**	
        var points = mapchart.getSelectedPoints();
        */
    });    		
    
	mapchart = $('#container').highcharts('Map', {
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
            				"path": "M66,-984,68,-902,155,-902,154,-986Z"
            			},
            			{
            				"name": "path4717",
            				"path": "M68,-882,68,-797,154,-797,154,-881z"
            			},
            			{
            				"name": "path4719",
            				"path": "M68,-776,68,-690,154,-690,154,-776z"
            			},
            			{
            				"name": "path4721",
            				"path": "M68,-669,68,-583,154,-583,154,-669z"
            			},
            			{
            				"name": "path4723",
            				"path": "M195,-988,196,-903,281,-902,281,-984z"
            			},
            			{
            				"name": "path4725",
            				"path": "M196,-881,196,-797,280,-798,281,-881z"
            			},
            			{
            				"name": "path4729",
            				"path": "M690,-954,690,-646,1000,-644,1000,-954z"
            			},
            			{
            				"name": "path4731",
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

	
	function saveSeat() {
		
		var param = {};
		param.seatid = jQuery("#seatid").val();
		param.userid = jQuery("#userid").val();
		param.sdesc = jQuery("#sdesc").val();
		param.name = jQuery("#name").val();
		
		var rtnResult = sendAjax("/seat/save", param);
		
		if (rtnResult) {
			alert("保存成功");
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
        $("#userid").val(id);   
	}

	jQuery(".input-username").find(".glyphicon-zoom-in").bind("click", function(){
		userDialog();
	});
	
	jQuery("#save_seat").bind("click", function(){
		saveSeat();
	});
});


jQuery(document).ready(function(){
	//重新设置高度
	resetFrameHei();
	
	jQuery(".highcharts-legend").hide();  //隐藏下面的 分类， series
	jQuery("text[text-anchor=end]").hide(); //隐藏highcharts.com
});






</script>