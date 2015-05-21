//(function(window, $){
	
var	__extend = function (B, A) {
	var F = function(){};
	F.prototype = A.prototype;  
	B.prototype = new F();  
	B.prototype.constructor = B;
	return B;
}	

var __parent = function(fieldDef) {
}

__parent.prototype._init = function(fieldDef) {
	this.fieldDef = fieldDef;
	//外层div
	var fieldItemArr = new Array();
	fieldItemArr.push("<div class='form-group col-sm-12'>");
	fieldItemArr.push("</div>");	
	this._jQueryItemObj = jQuery(fieldItemArr.join(""));
	
	//左边label
	var labelStr = jQuery("<div class='label_div col-sm-2 control-label'>"+fieldDef.name+"</div>");
	jQuery(labelStr).appendTo(this._jQueryItemObj);
	
	//右边可编辑内容
	var rightDivStr = "<div class='edit_div col-sm-8'></div>";
	jQuery(rightDivStr).appendTo(this._jQueryItemObj);
	
	this.render(fieldDef);
}

__parent.prototype.render = function(fieldDef) {
};

__parent.prototype.afterInit = function(fieldDef) {
};

__parent.prototype.disable = function(fieldDef) {
};

__parent.prototype.enable = function(fieldDef) {
};

__parent.prototype.getFieldObj = function() {
	return this._jQueryItemObj;
}

__parent.prototype.getEditObj = function() {
	return this.editObj;
}

__parent.prototype.show = function() {
	this._jQueryItemObj.show();
}

__parent.prototype.hide = function() {
	this._jQueryItemObj.hide();
}


var __input = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__input, __parent);


__input.prototype.render = function(fieldDef) {
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	
	var editStr = jQuery("<input type=text class='form-control' value='"+fieldDef.defualtval+"' id = '"+fieldDef.id+"'>");
	
	this.editObj = jQuery(editStr).appendTo(rightDivObj);
}

__input.prototype.getValue = function() {
	return this.editObj.val();	
}

__input.prototype.setValue = function(value) {
	var oldValue = this.getValue();
	this.editObj.val(value);
	
	if (oldValue != value) {
		this.onchange();
	}
}

__input.prototype.onchange = function() {
	//sendcommand(); TODO
}


__slider = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__slider, __parent);

__slider.prototype.render = function(fieldDef) {
	var _self = this;
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	
	var editCon = jQuery("<div class='col-sm-10'></div>").appendTo(rightDivObj);;
	
	var editStr = jQuery("<div id='"+fieldDef.id+"' style='width:100%'></div>");
	this.editObj = jQuery(editStr).appendTo(editCon);
	
	var extconfigObj;
	if (fieldDef.extconfig) {
		extconfigObj = StrToJson(fieldDef.extconfig);
	}
	
	
	this.stepnum = parseInt(extconfigObj.STEP) || 5;
	this.minnum = parseInt(extconfigObj.MIN) || 30;
	this.maxnum = parseInt(extconfigObj.MAX) || 90;
	this.defaultVal = parseInt(fieldDef.defaultval) || 50;
	
	this.sliderObj = this.editObj.labeledslider({
	      min: this.minnum,
	      max: this.maxnum,
	      range: false,
	      step : this.stepnum, 
	      value: this.defaultVal
	});
	
	var addBtnObj = jQuery("<div class='btn btn-primary col-sm-1'><span class='glyphicon glyphicon-plus' aria-hidden='true'></span></div>").insertAfter(editCon);
	var plusBtnObj = jQuery("<div class='btn btn-primary col-sm-1'><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></div>").insertBefore(editCon);
	
	addBtnObj.bind("click", function(){
		if (_self.getValue() < _self.maxnum) {
			_self.setValue(_self.getValue() + _self.stepnum);
		}
	});
	
	plusBtnObj.bind("click", function(){
		if (_self.getValue() > _self.minnum) {
			_self.setValue(_self.getValue() - _self.stepnum);	
		}
	});	
	
}

__slider.prototype.getValue = function() {
	return this.sliderObj.labeledslider("value");
}

__slider.prototype.setValue = function(value) {
	this.sliderObj.labeledslider("value", value);
}







__countdown = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__countdown, __parent);

__countdown.prototype.render = function(fieldDef) {
	var _self = this;
	this.fieldDef = fieldDef;
	
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	var CDArray = new Array();
	 CDArray.push("<div id='div_time' class='col-sm-6' style='height:75px;display:inline-block;text-align: center;'>");
	 CDArray.push("					<div class='countdown countdown-container container'>");
	 CDArray.push("					    <div class='clock row'>");
	 CDArray.push("					        <div class='clock-item clock-days countdown-time-value col-sm-1 col-md-1'>");
	 CDArray.push("					            <div class='wrap'>");
	 CDArray.push("					                <div class='inner'>");
	 CDArray.push("					                    <div id='canvas-days' class='clock-canvas'></div>");
	 CDArray.push("					");
	 CDArray.push("					                    <div class='text'>");
	 CDArray.push("					                        <p class='val'>0</p>");
	 CDArray.push("					                        <p class='type-days type-time'>天</p>");
	 CDArray.push("					                    </div><!-- /.text -->");
	 CDArray.push("					                </div><!-- /.inner -->");
	 CDArray.push("					            </div><!-- /.wrap -->");
	 CDArray.push("					        </div><!-- /.clock-item -->");
	 CDArray.push("					");
	 CDArray.push("					        <div class='clock-item clock-hours countdown-time-value col-sm-1 col-md-1'>");
	 CDArray.push("					            <div class='wrap'>");
	 CDArray.push("					                <div class='inner'>");
	 CDArray.push("					                    <div id='canvas-hours' class='clock-canvas'></div>");
	 CDArray.push("					");
	 CDArray.push("					                    <div class='text'>");
	 CDArray.push("					                        <p class='val'>0</p>");
	 CDArray.push("					                        <p class='type-hours type-time'>小时</p>");
	 CDArray.push("					                    </div><!-- /.text -->");
	 CDArray.push("					                </div><!-- /.inner -->");
	 CDArray.push("					            </div><!-- /.wrap -->");
	 CDArray.push("					        </div><!-- /.clock-item -->");
	 CDArray.push("					");
	 CDArray.push("					        <div class='clock-item clock-minutes countdown-time-value col-sm-1 col-md-1'>");
	 CDArray.push("					            <div class='wrap'>");
	 CDArray.push("					                <div class='inner'>");
	 CDArray.push("					                    <div id='canvas-minutes' class='clock-canvas'></div>");
	 CDArray.push("					");
	 CDArray.push("					                    <div class='text'>");
	 CDArray.push("					                        <p class='val'>0</p>");
	 CDArray.push("					                        <p class='type-minutes type-time'>分钟</p>");
	 CDArray.push("					                    </div>");
	 CDArray.push("					                </div>");
	 CDArray.push("					            </div>");
	 CDArray.push("					        </div>");
	 CDArray.push("					");
	 CDArray.push("					        <div class='clock-item clock-seconds countdown-time-value col-sm-1 col-md-1'>");
	 CDArray.push("					            <div class='wrap'>");
	 CDArray.push("					                <div class='inner'>");
	 CDArray.push("					                    <div id='canvas-seconds' class='clock-canvas'></div>");
	 CDArray.push("					");
	 CDArray.push("					                    <div class='text'>");
	 CDArray.push("					                        <p class='val'>0</p>");
	 CDArray.push("					                        <p class='type-seconds type-time'>秒</p>");
	 CDArray.push("					                    </div>");
	 CDArray.push("					                </div>");
	 CDArray.push("					            </div>");
	 CDArray.push("					        </div>");
	 CDArray.push("					    </div>");
	 CDArray.push("					</div>");
	 CDArray.push("		      	</div>");
	
	
	var editStr = jQuery(CDArray.join(""));
	
	
	this.editObj = jQuery(editStr).appendTo(rightDivObj);
	
	
	_self.setValBtnObj = jQuery("<button class='btn'>设置时间</button>").appendTo(rightDivObj);;
	_self.setValBtnObj.bind("click", function(){
		_self._setVal();
	});
	
	
	_self.durationval = fieldDef.defaultval;
}

__countdown.prototype._setVal = function() {
	var _self = this;
	
	jQuery("#setting_countdown").remove();
	
	var dialog = jQuery("<div id='setting_countdown' title='时间设置'></div>").appendTo(jQuery("body"));
	
	var inputDiv = jQuery("<div id='setting_countdown_con'><input id='setting_countdown_value' type='text' value=''>分钟</div>").appendTo(dialog);
	
	var posAttr = {my: "center", at : "center", of : window, collision : "fit"};
	
	$("#setting_countdown").dialog({
	    modal: true,
	    width : 600,
		height : 400,
		resizable : true,
		position : posAttr,
	    buttons: {
	      "确定": function() {
	    	  
	    	  var timeval = $(this).find("#setting_countdown_value").val();
	    	  _self.durationval = timeval;
	    	  
	    	  _self.setValue("RESET" + timeval);
	    	  
	          $(this).dialog("close");
	          //$(this).remove();
	      },"取消":function(){
	    	 $(this).remove();
		  }
	    }
	});	

}

__countdown.prototype.afterInit = function() {
	var _self = this;
    var currentTime = new Date();
	var futureTime = (new Date()).addMinutes(_self.fieldDef.defaultval);
	_self.editObj.find('.countdown').final_countdown({
			'start': currentTime.getTime(),
			'end': futureTime.getTime(),
			'now': currentTime.getTime()
	}, function() {
		alert("完成");
	});
}

__countdown.prototype.disable = function() {
	var _self = this;
	_self.setValBtnObj.addClass("disabled");
}

__countdown.prototype.enable = function() {
	var _self = this;
	_self.setValBtnObj.removeClass("disabled");
}

__countdown.prototype.getValue = function() {
	var _self = this;
	return _self.durationval;
}

__countdown.prototype.setValue = function(value) {
	var _self = this;
	
	if (value == "STOP") {
		_self.countDownObj.stopCounter();
		
		return;
	}
	
	
    var currentTime = new Date();
    
    var startFlag = "TRUE";
    var setMinVal = _self.durationval;
    if (value.indexOf("RESET") == 0) {
    	setMinVal = value.replace("RESET", "");
    	startFlag = "false";
    	_self.countDownObj = undefined;
    }
    
	var futureTime = (new Date()).addMinutes(setMinVal);
	
	if (_self.countDownObj) { //如果已经构造了，说明之前已经执行过，但是停止了， 时间就得算没走完的时间
		var remainSecond = _self.editObj.find(".clock-seconds .val").text();
		var remainMinute = _self.editObj.find(".clock-minutes .val").text();
		var remainHour = _self.editObj.find(".clock-hours .val").text();
		if (_self.editObj.find(".clock-hours").is(":hidden")) {
			remainHour = 0;
		}
		
		
		futureTime = (new Date()).addHours(remainHour).addMinutes(remainMinute).addSeconds(remainSecond);
	}
	
	if (value == "clearon") {
		futureTime = (new Date()).addMinutes(_self.durationval);
		_self.countDownObj = undefined;
	}
	
	_self.countDownObj = _self.editObj.find('.countdown').final_countdown({
			'startFlag' : startFlag,
			'start': currentTime.getTime(),
			'end': futureTime.getTime(),
			'now': currentTime.getTime()
	}, function() {
		alert("over");
	});
	
    if (value.indexOf("RESET") == 0) {
    	_self.countDownObj = undefined;
    }
}


__select = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__select, __parent);

__select.prototype.render = function(fieldDef) {
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	
	var $select = $('<select class="form-control form-con-select"></select>');
	//获取字典项的list
	var param = {};
	var dictItems = sendAjax("/dict/getDict/" + fieldDef.reldict, param, "get").childs; 
	jQuery.each(dictItems, function(index, option){
		var checked = "";
		if (option["code"] == fieldDef.defaultval) {
			checked = "selected";
		}
		
		var $option = $('<option '+checked+' value="' + option["code"] + '">' + option["name"] + '</option>');
		$select.append($option);
	});
	
	
	var editStr = jQuery("<div id='"+fieldDef.id+"' ></div>");
	
	
	
	this.editObj = $select.appendTo(jQuery(editStr)).appendTo(rightDivObj);
}

__select.prototype.getValue = function() {
	var val = this.editObj.val();
	if (val) {
		return val;
	}
	return "";
}

__select.prototype.setValue = function(value) {
	this.editObj.find("option[selected]").removeAttr("selected");
	this.editObj.find("option[value='" + value + "']").attr("selected", true);
}




__descp = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__descp, __parent);

__descp.prototype.render = function(fieldDef) {
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	this.fieldDef = fieldDef;
	
	var editStr = jQuery("<div id='"+fieldDef.id+"' >"+fieldDef.defaultval+"</div>");
	
	this.editObj = jQuery(editStr).appendTo(rightDivObj);
}

__descp.prototype.getValue = function() {
	return this.fieldDef.defaultval;
}

__descp.prototype.setValue = function(value) {
	this.fieldDef.defaultval = value;
}




__onoff = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__onoff, __parent);

__onoff.prototype.render = function(fieldDef) {
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	this.fieldDef = fieldDef;
	
	var editStr = jQuery("<div><input id='"+fieldDef.id+"' type='checkbox' data-off-color='warning'></div>");
	
	this.editObj = jQuery(editStr).appendTo(rightDivObj);
	
	this.editObj.find("[type='checkbox']").bootstrapSwitch({
        onSwitchChange: function(event, state) {
            event.preventDefault();
            return console.log(state, event.isDefaultPrevented());
        }
	});
}

__onoff.prototype.getValue = function() {
	return this.editObj.find("[type='checkbox']").bootstrapSwitch("state")
}

__onoff.prototype.setValue = function(value) { //true/false
	this.editObj.find("[type='checkbox']").bootstrapSwitch("state", value);
}








__dynamicGraph = function(fieldDef) {
	this._init(fieldDef);
}
__extend(__dynamicGraph, __parent);

__dynamicGraph.prototype.render = function(fieldDef) {
	var rightDivObj = this._jQueryItemObj.find(".edit_div");
	this.fieldDef = fieldDef;
	
	var editStr = jQuery("<div id='"+fieldDef.id+"' class='graph-dynamic'></div>");
	
	this.editObj = jQuery(editStr).appendTo(rightDivObj);
}

__dynamicGraph.prototype.afterInit = function() {
	var _self = this;
	
	this.dps = [];
	this.xVal = 0;
	this.chart = new CanvasJS.Chart(this.fieldDef.id, {
		data: [{
			type: "line",
			dataPoints: _self.dps 
		}]
	});
	this.chart.render();	
}

__dynamicGraph.prototype.getValue = function() {
	return "";
}

__dynamicGraph.prototype.setValue = function(value) { 
	var _self = this;

	if (value == "off") {
		window.clearInterval(_self.interval)
		
		return;
	} else if(value == "clearon") {
		_self.dps = [];
		_self.xVal = 0;
		_self.chart = undefined;
		_self.afterInit();
	}
	
	var yVal = 100;	
	var updateInterval = 1000;
	var dataLength = 500; // number of dataPoints visible at any point
	
	var dynamicData = {"inscode": "", "tytype":"tytype"};

	var updateChart = function() {
		var resData = sendAjaxParam("/insDef/dynamicdata", dynamicData);
		
		//yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
		
		yVal = resData._DATA_;
		_self.dps.push({
			x: _self.xVal,
			y: yVal
		});
		_self.xVal++;
		
		if (_self.dps.length > dataLength) {
			//dps.shift();
		}
		
		_self.chart.render();
	};

	this.interval = setInterval(function(){updateChart()}, updateInterval); 	
}



//})(window, jQuery);