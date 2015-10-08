<!DOCTYPE html>
<html lang="en">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>wx js test</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="/css/dream.css"/>
	
	<script type="text/javascript" src="/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/tools.js"></script>		
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>	
</head>
<%@ page import="java.util.Map"%>
<%@ page import="com.dream.utils.WxUtils"%>

<%
Map<String, String> signMap = WxUtils.getSign("http://yuananan.cn/views/wxtest.jsp");
%>

<body style="background-color: #EEE;">
    <div class="lbox_close wxapi_form">
      <h3 id="menu-basic">基础接口</h3>
      <span class="desc">判断当前客户端是否支持指定JS接口</span>
      <button class="btn btn_primary" id="checkJsApi">checkJsApi</button>
    </div>
</body>

<script>

jQuery(document).ready(function(){
	
});
debugger;
//var params = {};
//params.urlStr = "yuananan.cn/views/wxtest.jsp";
//var wxJsConfig = sendAjax("/wechat/js", params);

wx.config({
    debug: true,
    appId: 'wxf7811be1cb11d8c5',
    timestamp: '<%=signMap.get("timestamp")%>',
    nonceStr: '<%=signMap.get("nonceStr")%>',
    signature: '<%=signMap.get("signature")%>',
    jsApiList: [
      'checkJsApi',
      'onMenuShareTimeline',
      'onMenuShareAppMessage',
      'onMenuShareQQ',
      'onMenuShareWeibo',
      'onMenuShareQZone',
      'hideMenuItems',
      'showMenuItems',
      'hideAllNonBaseMenuItem',
      'showAllNonBaseMenuItem',
      'translateVoice',
      'startRecord',
      'stopRecord',
      'onVoiceRecordEnd',
      'playVoice',
      'onVoicePlayEnd',
      'pauseVoice',
      'stopVoice',
      'uploadVoice',
      'downloadVoice',
      'chooseImage',
      'previewImage',
      'uploadImage',
      'downloadImage',
      'getNetworkType',
      'openLocation',
      'getLocation',
      'hideOptionMenu',
      'showOptionMenu',
      'closeWindow',
      'scanQRCode',
      'chooseWXPay',
      'openProductSpecificView',
      'addCard',
      'chooseCard',
      'openCard'
    ]
});

wx.ready(function () {
	  // 1 判断当前版本是否支持指定 JS 接口，支持批量判断
	  document.querySelector('#checkJsApi').onclick = function () {
	    wx.checkJsApi({
	      jsApiList: [
	        'getNetworkType',
	        'previewImage'
	      ],
	      success: function (res) {
	        alert(JSON.stringify(res));
	      }
	    });
	  };
	  
	  try {
		  location.href = "wxf7811be1cb11d8c5://openwebview/?ret=0";
	  }catch (e) {
		  alert(e);
	  }
	  
});

</script>

</html>

