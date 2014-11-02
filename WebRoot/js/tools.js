
/**
 * 
 * @param ajaxUrl 请求url
 * @param queryParams 请求参数
 * @param queryType get/post 默认为post请求
 * @param async 是否同步
 * @param func 同步执行的回调函数
 * @returns 返回值
 */
function sendAjax(ajaxUrl,queryParams,queryType,async,func) {
    var resultData = new Object();
    var tempasync = false;
    if (async) {
    	tempasync = async;
    }
    queryType = queryType || "post"; 
    var jsonStr = JSON.stringify({title:'测试用户1'});
    jsonStr = JSON.stringify(queryParams);
    jQuery.ajax({
        type:queryType,
        url:encodeURI(ajaxUrl),
        dataType:"json",
        contentType: "application/json; charset=utf-8",
        mimeType: 'application/json',
        data:jsonStr,
        cache:false,
        async:tempasync,
        timeout:60000,
        success:function(data) {
            resultData = {};
            resultData = data;
            if (typeof data === "string") {//判断返回数据类型
                resultData = jQuery.parseJSON(data);
            }
            
            if(func) {
            	func.call(this, resultData);
            }
        },
        error:function(err) {
            resultData = {};
            resultData.exception = err;
            resultData.msg = err.responseText || "error";
            
            throw new Error(resultData.msg);
        }
    });
    
    return resultData;
};


/**
 * 将json对象转为字符串,只能返回一层的转换，更深层的转换请使用jQuery.toJSON(jsonObj)方法
 * @param o  参数：json对象
 * @return 返回值：字符串
 */
function JsonToStr(o) {
	/**
	 * 对字符串进行特殊字符编码
	 * @param str 参数：源字符串
	 * @return String 返回值：编码后的字符串
	 */
	function encode(str) {
		var sb = [];
		sb.push("\"");
		var c = "";
		for ( var i = 0; i < str.length; i++) {
			c = str.charAt(i);
			if (c == '\\') {
				sb.push("\\\\");
			} else if (c == '\n') {
				sb.push("\\n");
			} else if (c == '\r') {
				sb.push("\\r");
			} else if (c == '\t') {
				sb.push("\\t");
			} else if (c == '\'') {
				sb.push("\\\'");
			} else if (c == '\"') {
				sb.push("\\\"");
			} else if (c == '%') {
				sb.push("%25");
			} else if (c == '+') {
				sb.push(encodeURIComponent("+"));
			} else {
				sb.push(c);
			}
		}
		sb.push("\"");
		return sb.join("");
	};
	//
	var arr = [];
	var fmt = function(s) {
		if (/^(number)$/.test(typeof s)) {
			return s;
		} else {
			return /^(string)$/.test(typeof s) ? "" + encode(s) + "" : s;
		}

	};
	for ( var i in o) {
		arr.push("\"" + i + "\":" + fmt(o[i]));
	}
	return "{" + arr.join(",") + "}";
};

/**
 * 字符串转换为json对象,简单的一层级的调用，未增加转义等，如使用更复杂的调用，使用jQuery.parseJSON(jsonStr)
 * @param strData 参数：json格式字符串
 * @return 返回值：json对象
 */
function StrToJson(strData) {
	try {
		return (new Function("return " + strData))();
	} catch (e) {
		return {};
	}
};
