var GLOBAL = new Object();
GLOBAL.namespace = function(fullNameSpaceName) {
    var nsArray = fullNameSpaceName.split('.');
    var sEval = "";
    var sNS = ""; 
    var len = nsArray.length;
    for (var i = 0; i < len; i++) {
        if (i != 0) sNS += ".";
        sNS += nsArray[i];
        sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS + " = new Object();";
    }
    if (sEval != "") eval(sEval);
};


var System  = {
    getMode:function() {
    	return "RXTX"; // NODEJS / RXTX 
    }
};

function randomStr() {
    return Math.random().toString(36).substr(2); // remove `0.`
};


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
    queryType = queryType || "POST";
    var jsonStr = JSON.stringify(queryParams);
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
 * 
 * @param ajaxUrl 请求url
 * @param queryParams 请求参数
 * @param queryType get/post 默认为post请求
 * @param async 是否同步
 * @param func 同步执行的回调函数
 * @returns 返回值
 */
function sendAjaxParam(ajaxUrl,queryParams,queryType,async,func) {
    var resultData = new Object();
    var tempasync = false;
    if (async) {
    	tempasync = async;
    }
    queryType = queryType || "post"; 
    jQuery.ajax({
        type:queryType,
        url:encodeURI(ajaxUrl),
        dataType:"json",
        data:queryParams,
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


/**
 * @param pageNum 页码
 * @param reqLocation 请求地址 /article/articles
 * @param reqLocation 返回数据放在页面上的哪 
 */
function goPage(pageNum, reqLocation, locInPage) {
	var page = {};
	page["pageNo"] = pageNum;
	
	var param = {};
	param._PAGE_ = JSON.stringify(page);
	
	var article = sendAjaxParam("/article/articles", param);
	debugger;
	jQuery("#mainArticleDiv").html(article._DATA_);
}

function deleteItem(id) {
	var param = {};
	param.id = id;
	sendAjax("/article/delete/" + id, param, "get");
	
	window.location.href = "/";
}

/**
 * 
 * @param columnArray 表格的所有列
 * @param reqUrl 请求url
 * @param defaultSort 默认排序
 * @returns 初始化datatable的参数
 */
function getPageParam(columnArray, reqUrl, defaultSort) {
	var sortField = 1;
	var sortType = "desc";
	if (defaultSort) {
		var sortArray = defaultSort.split(",");

		jQuery.each(columnArray, function(index, item){
			if (item.mData == sortArray[0]) {
				sortField = index;
			}
		});
		
		sortType = sortArray[1];
	}
	
	var pageParam = {
			"bAutoWidth": false,					//不自动计算列宽度
			"aoColumns": columnArray ,
			"aaSorting": [[sortField,sortType]] ,             //默认的排序字段
			"bProcessing": true,					//加载数据时显示正在加载信息
			"bServerSide": true,					//指定从服务器端获取数据
			"bFilter": false,						//不使用过滤功能
			"bLengthChange": false,					//用户不可改变每页显示数量
			"iDisplayLength": 50,					//每页显示8条数据
			"sAjaxSource": reqUrl,					//获取数据的url
			"fnServerData": retrieveData,			//获取数据的处理函数
			"sPaginationType": "full_numbers",		//翻页界面类型
			"oLanguage": {							//汉化
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "没有检索到数据",
				"sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",
				"sInfoEmtpy": "没有数据",
				"sProcessing": "正在加载数据...",
				"oPaginate": {
					"sFirst": "首页",
					"sPrevious": "前页",
					"sNext": "后页",
					"sLast": "尾页"
				}
			}
		};
	
	return pageParam;
}


//自定义数据获取函数
function retrieveData( sSource, aoData, fnCallback ) {
	if (typeof(setQueryParam) == 'function') { //判断子页面是否存在该函数
		setQueryParam(aoData);	
	}
	
	$.ajax( {
		"type": "POST", 
		"contentType": "application/json",
		"url": sSource, 
		"dataType": "json",
		"data": JSON.stringify(aoData), 
		"headers": {'Content-Type': 'application/json'}, 
		"success": function(resp) {
			fnCallback(resp);
		}
	});
}

/**
 * 重新设置iframe的高度
 */
function resetFrameHei(){
	//设置高度， 延时
	setTimeout(function() {
		adjustIframe("right_main_iframe");
	}, 50);
	

	resetParentHei();
}

function resetParentHei() {
	if (window.frameElement) {
		var iframeId = window.frameElement.id;
		
		if (iframeId) {
			adjustIframe(iframeId);
		}
	}
}

/**
 * 
 * @param frameId frame的ID
 */
function adjustIframe(frameId) {
	
	var iframe = document.getElementById(frameId);
	if (!iframe) {
		iframe = parent.document.getElementById(frameId);
	}
	
	if (!iframe) {
		return false;
	}
	
	var idoc = iframe.contentWindow && iframe.contentWindow.document
			|| iframe.contentDocument;
	var callback = function() {
		var iheight = Math.max(idoc.body.scrollHeight, idoc.documentElement.scrollHeight); //取得其高
		var outterHei = top.document.documentElement.scrollHeight;
		
		if (iheight < outterHei) {
			iheight = outterHei;
		}
		
		if ($(window.parent).height() > iheight) {
			iheight = $(window.parent).height() - 56;
		}
		
		//iheight = iheight - 56;
		//iheight = iheight + 17;
		
		iframe.style.height = iheight + "px";
	}
	if (iframe.attachEvent) {
		iframe.attachEvent("onload", callback);
		callback();
	} else {
		iframe.onload = callback;
	}
}

/**
 * 
 */
function getFiles(dataid, model) {
	if (model) {
		return sendAjax("/file/list/" + model + "/" + dataid, {}, "GET");
	} else {
		return sendAjax("/file/list/" + dataid, {}, "GET");
	}
}

/**
 * 显示文件
 * @param dataid
 * @param model
 */
function showFiles(dataid, model) {
	var fileList = getFiles(dataid, model);
	
	var fileCon = jQuery("#file_list");
	jQuery.each(fileList, function(index, item){
		buildOnFile(fileCon, item);
	});
}

/**
 * 构建一条文件记录
 * @param fileCon
 * @param item
 */
function buildOnFile(fileCon, item) {
	var fileLineStr = "<div class='row file' item_id='"+item.id+"'><div class='col-sm-4 file-name'>"+item.name+"</div>";
	fileLineStr += "<div class='col-sm-3 file-time'>"+item.atime+"</div>";
	fileLineStr += "<div class='col-sm-2 file-size'>"+item.fsize+"</div>";
	fileLineStr += "<div class='col-sm-2 file-oper'></div>";
	
	fileLineStr += "</div>";
	
	var newFileOper = jQuery(fileLineStr).appendTo(fileCon).find(".file-oper");
	
	var fileOperDel = jQuery("<a href='#'>删除</a>").appendTo(newFileOper);
	var fileOperDown = jQuery("<a href='#'>下载</a>").appendTo(newFileOper);
	//var fileOperInsert = jQuery("<a href='#'>插入正文</a>").appendTo(newFileOper);
	
	fileOperDel.bind("click", function(){
		alert("delete " + item.id + "  " + item.name);
	});	
	
	fileOperDown.bind("click", function(){
		window.open("/file/" + item.id);
	});	

}

/**
 * 刚上传的文件，显示到页面的文件列表上去
 * @param file
 * @param info
 */
function addNewFile(file, info) {
	var fileCon = jQuery("#file_list");
	var item = {};
	item.id = info.response;
	item.name = file.name;
	item.fsize = file.size;
	item.atime = "刚刚";
	buildOnFile(fileCon, item);
}

Date.prototype.add = function(milliseconds){
	var m = this.getTime() + milliseconds;
	return new Date(m);
};
Date.prototype.addSeconds = function(second){
	return this.add(second * 1000);
};
Date.prototype.addMinutes = function(minute){
	return this.addSeconds(minute*60);
};
Date.prototype.addHours = function(hour){
	return this.addMinutes(60*hour);
};
Date.prototype.addDays = function(day){
	return this.addHours(day * 24);
};
