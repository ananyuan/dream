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
			debugger;
			var tabId = e.currentTarget.attributes.getNamedItem("aria-controls").nodeValue;
			
			if (jQuery("#" + tabId).html().trim() == "") {
				jQuery("<iframe src='/insDef/page/<%=insid%>/"+tabId+"' id='iframe_"+tabId+"'></iframe>").appendTo(jQuery("#" + tabId));
			}
		});		
		
	});
	
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
			<li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">串口调试工具<%=chanum%></a></li>
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