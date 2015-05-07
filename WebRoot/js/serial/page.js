
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
	
	jQuery.each(_self.insBtns, function(index, item){
		var btnStr = "<button type='button' class='btn btn-primary' id='"+item.code+"' value='"+item.code+"'>"+item.name+"</button>";
		var btnObj = jQuery(btnStr).appendTo(btnCon);
		if (item.code == "cstart" || item.code == "btncode0") { //开始
			
			//btnObj.disabled();
			
			//获取按钮定义的command, 后台做,
			btnObj.bind("click", function(){		
				debugger;
				_self.getFieldData();
			});
		
			
		} else if (item.code == "cstop") { //结束
			
			
		} else if (item.code == "cpause") { //暂停
			
		}
		
		
	});
}

dr.pageObj.prototype.getFieldData = function() {
	var _self = this;
	
	var fieldData = {};
	jQuery.each(_self.fieldItemMap, function(index, item){
		
		fieldData[index] = item.getValue();
	});
	
	
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
			
			if (item.itemtype == "COUNTDOWN") {
				fieldObj.setValue(90);

			} else if (item.itemtype == "DYNAMICGRAPH") {
				var dps = []; // dataPoints

				var chart = new CanvasJS.Chart("dfjslkdjgdsjgd", {
//					title :{
//						text: "Live Random Data"
//					},			
					data: [{
						type: "line",
						dataPoints: dps 
					}]
				});

				var xVal = 0;
				var yVal = 100;	
				var updateInterval = 1000;
				var dataLength = 500; // number of dataPoints visible at any point

				var updateChart = function (count) {
					count = count || 1;
					// count is number of times loop runs to generate random dataPoints.
					
					for (var j = 0; j < count; j++) {	
						yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
						dps.push({
							x: xVal,
							y: yVal
						});
						xVal++;
					};
					if (dps.length > dataLength)
					{
						//dps.shift();				
					}
					
					chart.render();		

				};

				// generates first set of dataPoints
				updateChart(dataLength); 

				// update chart after specified time. 
				setInterval(function(){updateChart()}, updateInterval); 
			}
		} else {
			jQuery(fieldStr).appendTo(contentObj);	
		}
	});
}

dr.pageObj.prototype._afterLoad = function() {
	var _self = this;
	
	
	adjustIframe("iframe_" + _self.channelport);
}


