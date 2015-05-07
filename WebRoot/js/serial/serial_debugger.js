
function showPorts(serial_ports) {
	var comListHtml = "";
	console.log(JSON.stringify(serial_ports));
	$.each(serial_ports, function(key, item){
		comListHtml += "<option value='"+item+"'>"+item+"</option>";
	});

	$(comListHtml).appendTo($("#serialport_select"));
}	


function showReceiveData(receiveData) {
	var oldVal = $("#receive_data").val();

    jQuery("#receive_data").val(oldVal + receiveData);	
}


function openSerialPort() {
	var param_serail = {};
	param_serail.baudrate = $("#baudrate").val();
	param_serail.databits = parseInt($("#databits").val());
	param_serail.stopbits = parseInt($("#stopbits").val());

	var port_num = $("#serialport_select").val();
	
	var param = {};
	param.servObj = "serial"; //将操作父对象的serial对象
	param.actionMethod = "open"; //操作方法
	param.dataObj = {"PORT_NUM" : port_num, "PARAM_OBJ" : param_serail};
	
	
	return sendSerialData(param);
}


function closePort() {
	var port_num = $("#serialport_select").val();
	
	var param = {};
	param.servObj = "serial"; //将操作父对象的serial对象
	param.actionMethod = "close"; //操作方法
	param.dataObj = {"PORT_NUM" : port_num};
	
	sendSerialData(param);
}

function sendDataToSerialPort() {
	var port_num = $("#serialport_select").val();
	var dataStr = $("#send_data").val();
	
	var param = {};
	param.servObj = "serial"; //将操作父对象的serial对象
	param.actionMethod = "writeData"; //操作方法
	param.dataObj = {"PORT_NUM" : port_num, "COMMAND" : dataStr};
	
	sendSerialData(param);
}

jQuery(document).ready(function(){
	$("#open_close_btn").bind("click", function(){
		var btnText = $("#open_close_btn").text();
		if (jQuery.trim(btnText) == "打开串口") {
			var rtnMsg = openSerialPort();
			if (rtnMsg && rtnMsg._RTN_ERR_MSG_) {
				alert(rtnMsg._RTN_ERR_MSG_);
			} else {
				$("#open_close_btn").text("关闭串口");
				
				//串口打开之后就监听
				registerComet();
			}
		} else {
			closePort();
			registerCometCancel();
			$("#open_close_btn").text("打开串口");
		}
	});

	$("#send_data_btn").bind("click", function(){
		sendDataToSerialPort();
	});	
	
	
	setTimeout("getSerialPorts('iframe_settings')", 2000);	

});


function registerComet() {
	var port_num = $("#serialport_select").val();
	$("#comet_flush").attr("src", "/serial/receive/" + port_num);
}

function registerCometCancel() {
	$("#comet_flush").attr("src", "");
}

/**
 * 服务端调用
 * @param msg
 */
function handleRtnMsg(msg){
	var oldVal = jQuery("#receive_data").val();
	jQuery("#receive_data").val(oldVal + msg);
}

