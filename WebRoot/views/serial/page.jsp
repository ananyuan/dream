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
	String channelport = "";
	
	if (null != request.getAttribute("insDef")) {
		insDef = (InsDef) request.getAttribute("insDef");
	} else {
		insDef = new InsDef();
	}
	if (null != request.getAttribute("channelport")) {
		channelport = (String) request.getAttribute("channelport");
	}
	
	String insid = insDef.getId();
	int chanum = insDef.getChannum();
%>


<script>
	jQuery(document).ready(function(){
		var opts = {"inscode":"<%=insid%>", "channelport":"<%=channelport%>"};
		var pageObj = new dr.pageObj(opts);
		pageObj.show();
	});
	
</script>




<body style="padding-top:0px; overflow-x:hidden;">

	<div id="page_board" style="width:100%;margin:0px;">

	</div>


</body>