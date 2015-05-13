
GLOBAL.namespace("dr");

dr.pageObj = function(options) {
	var defaults = {
			"id":options.inscode + options.channelport + "-channel",
			"title" : "通道"
		};
	this._opts = jQuery.extend(defaults, options);		
	
	this.inscode = this._opts.inscode;
	this.channelport = this._opts.channelport;
	
	this.mainCon = jQuery("#page_board");
	if (this._opts.insFields && this._opts.insBtns) {
		this.insFields = this._opts.insFields;
		
		this.insBtns = this._opts.insBtns;
	} else {
		var param = {'inscode':this.inscode};
		var defdata = sendAjaxParam("/insDef/defdata", param);
		
		this.insFields = defdata._DATA_.insFields;
		
		this.insBtns = defdata._DATA_.insBtns;
	}
}


dr.pageObj.prototype.show = function () {
	var _self = this;
	
	//布局， 
	_self._bldLayout();
	
	//加载完成之后的  设置高度， 等
	_self._afterLoad();
}

dr.pageObj.prototype._bldLayout = function() {
	var _self = this;
	
	_self._showBtns();
	
	_self._showFields();
}


dr.pageObj.prototype._showBtns = function() {
	var _self = this;
	
	var btnCon = jQuery("<div class='btn-con'></div>").appendTo(_self.mainCon);
	
	this.btnObjMap = {};
	jQuery.each(_self.insBtns, function(index, item){
		var cssplus = "<span class='"+item.cssplus+"' aria-hidden='true'></span>";
		
		var btnStr = "<button type='button' class='btn btn-primary' id='"+item.code+"' value='"+item.code+"'>"+ cssplus + item.name+"</button>";
		var btnObj = jQuery(btnStr).appendTo(btnCon);
		_self.btnObjMap[item.code] = btnObj;
		
		if (item.code == "cspause" || item.code == "csstop") {
			btnObj.addClass("disabled");
		}
		
		btnObj.bind("click", function() {  //注册点击事件
			if (item.code == "csstop") {
				if (!confirm("确定停止？")) {
					return;
				}
			}
			
			var reluserid = _self.getRelUserId();
			
			if (item.code == "csstart" && !reluserid) { //判断绑定人员了没，如果没有，则提示
				if (!confirm("未关联人员， 是否继续？\n继续点 确定 \n否则点 取消 进行绑定人员操作")) {
					return;
				}
			}			
			
			var paramdata = _self.getFieldData();
			paramdata.channelport = _self.channelport;
			paramdata.inscode = _self.inscode;
			paramdata.act_code = item.code;
			paramdata.userid = reluserid;
			
			var actRes = sendAjaxParam("/insDef/act", paramdata);
			
			var oldpici = _self.pici;
			
			_self.pici = actRes.pici;
			
			
			if (item.code == "csstart") { //开始
				_self.btnObjMap["csstart"].addClass("disabled");
				_self.btnObjMap["csstop"].removeClass("disabled");
				_self.btnObjMap["cspause"].removeClass("disabled");
				
				//所有的动态图开始工作 ， 清除之前的数据
				jQuery.each(_self.fieldItemMap, function(index, item){
					if(item.fieldDef.itemtype.toUpperCase() == "DYNAMICGRAPH") {
						if (oldpici != undefined && oldpici.length == 0) {
							item.setValue("clearon"); 
						} else {
							item.setValue("on");
						}
					} else if (item.fieldDef.itemtype.toUpperCase() == "COUNTDOWN") {
						if (oldpici != undefined && oldpici.length == 0) {
							item.setValue("clearon"); 
						} else {
							item.setValue("START");
						}
					}
				});
				
			} else if (item.code == "csstop") { //结束
				_self.btnObjMap["csstart"].removeClass("disabled");
				_self.btnObjMap["csstop"].addClass("disabled");
				_self.btnObjMap["cspause"].addClass("disabled");
				//所有的动态图 清零
				jQuery.each(_self.fieldItemMap, function(index, item){
					if(item.fieldDef.itemtype.toUpperCase() == "DYNAMICGRAPH") {
						item.setValue("off");
					} else if (item.fieldDef.itemtype.toUpperCase() == "COUNTDOWN") {
						item.setValue("STOP");
					}
				});	
			} else if (item.code == "cspause") { //暂停
				_self.btnObjMap["csstart"].removeClass("disabled");
				_self.btnObjMap["csstop"].removeClass("disabled");
				_self.btnObjMap["cspause"].addClass("disabled");
				
				//所有的动态图停止工作
				jQuery.each(_self.fieldItemMap, function(index, item){
					if(item.fieldDef.itemtype.toUpperCase() == "DYNAMICGRAPH") {
						item.setValue("off");
					} else if (item.fieldDef.itemtype.toUpperCase() == "COUNTDOWN") {
						item.setValue("STOP");
					}
				});				
			} 
		});
	});
}

dr.pageObj.prototype.getRelUserId = function() {
	var _self = this;
	
	var reluserid = jQuery(parent.document).find("#right_reluser").find("span").attr("reluserid"); 
	
	return reluserid;
}




dr.pageObj.prototype.getFieldData = function() {
	var _self = this;
	
	var fieldData = {};
	jQuery.each(_self.fieldItemMap, function(index, item){
		
		fieldData[index] = item.getValue();
	});
	if (_self.pici) {
		fieldData["pici"] = _self.pici;
	}
	
	return fieldData;
}


dr.pageObj.prototype._showFields = function() {
	var _self = this;
	
	var contentObj = jQuery("<div style='width:100%'></div>").appendTo(_self.mainCon);
	
	this.fieldItemMap = {};
	jQuery.each(_self.insFields, function(index, item){
		var fieldStr = "";
		
		var fieldObj;
		if (item.itemtype == "INPUT") {
			fieldObj = new __input(item);
		} else if (item.itemtype == "SLIDER") {
			fieldObj = new __slider(item);
		} else if (item.itemtype == "COUNTDOWN") {
			fieldObj = new __countdown(item);
		} else if (item.itemtype == "SELECT") {
			fieldObj = new __select(item);
		} else if (item.itemtype == "DESCP") {
			fieldObj = new __descp(item);
		} else if (item.itemtype == "ONOFF") {
			fieldObj = new __onoff(item);
		} else if (item.itemtype == "DYNAMICGRAPH") {
			fieldObj = new __dynamicGraph(item);
		} 
		
		if (fieldObj) {
			_self.fieldItemMap[item.code] = fieldObj;
			
			fieldObj.getFieldObj().appendTo(contentObj);
			
			fieldObj.afterInit();
		} else {
			jQuery(fieldStr).appendTo(contentObj);	
		}
	});
}

dr.pageObj.prototype._afterLoad = function() {
	var _self = this;
	
	
	adjustIframe("iframe_" + _self.channelport);
}


