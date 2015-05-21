<jsp:include page="/views/serial/header_serial.jsp" flush="true" />

<style>
	.nav-tabs > li > a {
		border: 1px solid rgba(18, 237, 63, 1);
	}
	.nav-tabs > .active > a {
		border: 1px solid #F21212;
	}
	#myTab { 
		margin-bottom: 0px;
	}
	.frame-tab {
		margin:0px;
		width:100%;
		height:100%;
		border:0px;
		min-height:600px;
	}
</style>

<%@ page import="com.dream.controller.serial.model.InsDef"%>

<%
	InsDef insDef;
	if (null != request.getAttribute("insDef")) {
		insDef = (InsDef) request.getAttribute("insDef");
	} else {
		insDef = new InsDef();
	}
	String insid = insDef.getId();
	int chanum = insDef.getChannum();
%>


<script>
	jQuery(document).ready(function(){
		
		if (jQuery("#channel1").html().trim() == "") {
			jQuery("<iframe src='/insDef/page/<%=insid%>/channel1' id='iframe_channel1' style='width:100%'></iframe>").appendTo(jQuery("#channel1"));
		}
		
		$('#myTab a').click(function (e) {
			e.preventDefault();
			$(this).tab('show');
			var tabId = e.currentTarget.attributes.getNamedItem("aria-controls").nodeValue;
			
			if (jQuery("#" + tabId).html().trim() == "") {
				jQuery("<iframe src='/insDef/page/<%=insid%>/"+tabId+"' id='iframe_"+tabId+"'></iframe>").appendTo(jQuery("#" + tabId));
			}
		});		
		
		var matchPortParam = {"inscode":"<%=insid%>"};
		var matchPort = sendAjaxParam("/insDef/matchport", matchPortParam);
		
		if (matchPort.serial_port_num == "__ERROR__") {
			var errorObj = jQuery("<div class='error glyphicon glyphicon-remove-circle'>ERROR未监测到硬件设备</div>").appendTo(jQuery("#right_signlamp"));
			errorObj.bind("click", function(){
				matchPort = sendAjaxParam("/insDef/matchport", matchPortParam);
				if (matchPort.serial_port_num == "__ERROR__") {
					alert("未监测到硬件设备，请重启再试");
					jQuery("#right_signlamp").html("<div><div class='ok glyphicon glyphicon-ok-circle'></div>COM4</div>");
					
					jQuery("#right_signlamp").bind("click", function(){
						showSetting();
					});
				} else {
					var okObj = jQuery("#right_signlamp").html("<div class='ok glyphicon glyphicon-ok-circle'>"+matchPort.serial_port_num+"</div>");
				}
			});
		} else {
			jQuery("#right_signlamp").html("<div class='ok glyphicon glyphicon-ok-circle'>"+matchPort.serial_port_num+"</div>");
		}
		
		jQuery("#right_reluser").bind("click", function(){
			selectRelUser();
		});
		
	});

function selectRelUser() {
	var dialogparam = {};
	dialogparam.requrl = "/sick/search";
	dialogparam.defaultSort = "intime,desc";
	dialogparam.columns = [
	                       {'code':'name', 'name':'姓名'},
	                       {'code':'age', 'name':'年龄'},
	                       {'code':'sex', 'name':'性别'},
	                       {'code':'jiguan', 'name':'籍贯'},
	                       {'code':'innum', 'name':'病历号'}
	                       ];
	dialogparam.callBack = function(rtnObj) {
		showRelUserDetail(rtnObj);
	}
	
	var userselect = new dr.dialogselect(dialogparam);
	userselect.render();
}	
	
	
function showRelUserDetail(userObj) {
	var reluserCon = jQuery("#right_reluser").find(".reluser");
	
	var userDetail = "<span reluserid='"+userObj.id+"'>姓名：" + userObj.name + "&nbsp;&nbsp;&nbsp;编号：" + userObj.innum + "</span>";
	
	reluserCon.html(userDetail);
}	

function showSetting() {
	var dialog = jQuery("<div id='setting_serial' title='设置'></div>").appendTo(jQuery("body"));

	var treeDiv = jQuery("<div id='setting_serial_con'></div>").appendTo(dialog);
	
	var posAttr = {my: "center", at : "center", of : window, collision : "fit"};
	
    $("#setting_serial").dialog({
        modal: true,
        width : 600,
		height : 400,
		resizable : true,
		position : posAttr,
        buttons: {
          "确定": function() {
        	  
              $(this).dialog("close");
          },"取消":function(){
        	 $(this).remove();
  		  }
        }
    });	
}



</script>




<body style="padding-top:0px">

	<div role="tabpanel">
		<ul class="nav nav-tabs" role="tablist" id="myTab">
			<%
				String liclass = "active";
				for (int i = 1; i <= chanum; i++) {
					if (i != 1) {
						liclass = "inactive";
					}
			%>

			<li role="presentation" class="<%=liclass%>"><a href="#channel<%=i%>" aria-controls="channel<%=i%>" role="tab" data-toggle="tab">通道<%=i%></a></li>

			<%
				}
			%>

			<li role="presentation"><a href="#sick_record" aria-controls="sick_record" role="tab" data-toggle="tab">病历</a></li>
			<li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">串口调试工具</a></li>
			
			<li class="signlamp" id="right_signlamp"></li>
			<li class="reluser" id="right_reluser"><div class="glyphicon glyphicon-user iconuser"></div><div
					class="reluser inlineblock">选择人员</div></li>
		</ul>

		<div class="tab-content" id="myTab_content">
			<%
				String firstPane = "active";
				for (int i = 1; i <= chanum; i++) {
					if (i != 1) {
						firstPane = "inactive";
					}
			%>

			<div role="tabpanel" class="tab-pane <%= firstPane%>" id="channel<%=i%>"></div>

			<%
				}
			%>

			<div role="tabpanel" class="tab-pane" id="sick_record">
				<iframe src="/sick/list" id="iframe_sick_record" style="margin: 0px; width: 100%; height: 100%; border: 0px"></iframe>
			</div>
			<div role="tabpanel" class="tab-pane" id="settings">
				<iframe src="/views/serial/serial_debugger.html" id="iframe_settings" style="margin: 0px; width: 100%; height: 100%; border: 0px"></iframe>
			</div>
		</div>
	</div>
</body>