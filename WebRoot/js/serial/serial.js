/**
 * 对串口发送数据
 * @param params
 */
function sendSerialData(params) {
	if (System.getMode() == "RXTX") {
		var rtnData = sendAjax("/serial/sendData", params);
		
		return rtnData;
	} else if (System.getMode() == "NODEJS"){
		messageTopLocalClient(params);
	}	
}


function messageTopLocalClient(params) {
	window.top.postMessage(params, '*');
}

function testPostMsg(){
	getSerialPorts();
	
	var param = {};
	param.servObj = "serial"; //将操作父对象的serial对象
	param.actionMethod = "open"; //操作方法
	param.dataObj = {"PORT_NUM" : "COM4", "COMMAND" : "abc"};
	
	messageTopLocalClient(param);
}

jQuery(document).ready(function(){
	window.addEventListener('message', function(event) {
	    console.log(event.data);
	
	    var msgData = event.data;
	    
	    if (msgData) {
	        console.log(JSON.stringify(msgData));
	        
	        if (msgData.servObj == "serial") {
	            if (msgData.actionMethod == "getPorts") {  //获取串口列表
	                console.log(msgData.dataObj);  //获取到了结果，
	                
	                var fromPage = msgData.fromPage;
	                
	                document.getElementById(fromPage).contentWindow.showPorts(msgData.dataObj);
	            } else if (msgData.actionMethod == "receiveData") {
	            	console.log(msgData.data);
	            	
	            	var dataObj = JSON.parse(msgData.dataObj);
	            	var fromPage = "iframe_settings"
	            	if (dataObj.fromPage) {
	            		fromPage = dataObj.fromPage;
	            	}
	            	document.getElementById(fromPage).contentWindow.showReceiveData(dataObj.data);
	            }
	        }
	    }
	});
});

function getSerialPorts(fromPage){
	if (System.getMode() == "RXTX") {
		var param = {};
		var rtnData = sendAjax("/serial/listPort", param, "get");
		
		showPorts(rtnData.data.split(","));
	} else if (System.getMode() == "NODEJS"){
		var param = {};
		param.servObj = "serial"; //将操作父对象的serial对象
		param.actionMethod = "getPorts"; //操作方法
		param.fromPage = fromPage; //操作方法
		
		messageTopLocalClient(param);		
	}
}

