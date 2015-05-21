GLOBAL.namespace("dr");

dr.dialogselect = function(options) {
	var defaults = {
			"id":"dialog-id",
			"dialogData":"",
			"width" : 800,
			"height" : 540,
			"resizable" : false,
			"selectLeaf" : true,
			"title" : "标题"
		};
	this._opts = jQuery.extend(defaults,options);		
	this.columns = this._opts.columns;
	this.requrl = this._opts.requrl;
	this.defaultSort = this._opts.defaultSort;
}

/**
 * 渲染 弹出框
 */
dr.dialogselect.prototype.render = function () {
	var _self = this;
	
	if ($("#" + _self._opts.id)) {
		$("#" + _self._opts.id).remove();
	}
	
	var dialog = jQuery("<div id='" + _self._opts.id + "' title='" + _self._opts.title + "'></div>").appendTo(jQuery("body"));

	var treeDiv = jQuery("<div id='"+_self._opts.id+"_dialogselect' class='dialogselect'></div>").appendTo(dialog);
	
	_self.posAttr = {my: "center", at : "center", of : window, collision : "fit"};

	
    $("#" + _self._opts.id).dialog({
        modal: true,
        width : _self._opts.width,
		height : _self._opts.height,
		resizable : _self._opts.resizable,
		position : _self.posAttr,
        buttons: {
          "确定": function() {
        	  //返回数据， 并关闭dialog
        	  var selectTrObj = oTable.find(".selected");
        	  var rtnObj = {};
        	  rtnObj["id"] = selectTrObj.attr("id");
        	  
        	  jQuery.each(_self.columns, function(index, item){
        	      rtnObj[item.code] = selectTrObj.find("td:eq("+(index+1)+")", this).text();
              });
        	  
        	  var backFunc = _self._opts.callBack;
              backFunc.call(_self._opts.parHandler, rtnObj); 
        	  
              $(this).dialog("close");
          },"取消":function(){
        	 $(this).remove();
  		  }
        }
    });

  	jQuery(_self.getTableStrut()).appendTo(jQuery("#"+_self._opts.id+"_dialogselect"));
    
    
	var columnsDef = new Array();
	columnsDef.push({ "mData": "xuhao", 'sClass':'center', "bSortable": false});
	
	jQuery.each(_self.columns, function(index, item){
		columnsDef.push({"mData": item.code, 'sClass':'left'});
	});
	
	var dataTableParam = getPageParam(columnsDef, _self.requrl, _self.defaultSort);
    var oTable = $('#dialog_table').dataTable(dataTableParam);
    oTable.fnDraw();	
    
    
    $("#"+_self._opts.id+"_dialogselect tbody").delegate("tr", "click", function() {
		if ($(this).hasClass('selected') ) {
			$(this).removeClass('selected');
		} else {
			oTable.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
    });
}

/**
 * 构造table
 */
dr.dialogselect.prototype.getTableStrut = function () {
	var _self = this;
	
	var tableStr = "<table class='table table-bordered table-hover' id = 'dialog_table'>";
	tableStr +="	<thead>";
	tableStr +="		<tr>";
	tableStr +="			<th class='xuhao no-sort'>序号</th>";
	
	jQuery.each(_self.columns, function(index, item){
		tableStr +="<th>"+item.name+"</th>";	
	});
	
	tableStr +="		</tr>";
	tableStr +="	</thead>";
	tableStr +="	<tbody>";
	tableStr +="	</tbody>";
	tableStr +="</table>";
	
	return tableStr;
	
}	


