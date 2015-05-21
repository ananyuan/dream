GLOBAL.namespace("dr");

dr.treeview = function(options) {
	var defaults = {
			"id":"dialog-id",
			"treeData":"",
			"width" : 360,
			"height" : 540,
			"resizable" : false,
			"selectLeaf" : true,
			"title" : "标题"
		};
	this._opts = jQuery.extend(defaults,options);		
}

/**
 * 渲染树
 */
dr.treeview.prototype.render = function () {
	var _self = this;
	
	var dialog = jQuery("<div id='" + _self._opts.id + "' title='" + _self._opts.title + "'></div>").appendTo(jQuery("body"));

	var treeDiv = jQuery("<ul id='"+_self._opts.id+"_tree' class='ztree'></ul>").appendTo(dialog);
	
	
	
	_self.posAttr = {my: "center", at : "center", of : window, collision : "fit"};
//	if (_self._opts.position) {
//		_self.posAttr = _self._opts.position;
//	}
	
    $("#" + _self._opts.id).dialog({
        modal: true,
        width : _self._opts.width,
		height : _self._opts.height,
		resizable : _self._opts.resizable,
		position : _self.posAttr,
        buttons: {
          "确定": function() {
        	 //返回数据， 并关闭dialog
        	  var nodes = _self.treeObj.getSelectedNodes();
        	  
        	  if (nodes.length == 0) {
        		  alert("请选择节点");
        		  return false;
        	  }
        	  
        	  if (_self._opts.selectLeaf && nodes[0].isParent) {
        		  alert("请选择叶子节点");
        		  return false;
        	  }
        	  
        	  var backFunc = _self._opts.callBack;
              backFunc.call(_self._opts.parHandler, nodes[0].id, nodes[0].name); 
        	  
              $(this).dialog("close");
          },"取消":function(){
        	 $(this).remove();
  		  }
        }
    });

  	_self.treeObj = jQuery.fn.zTree.init($("#" + _self._opts.id + "_tree"), _self.setting(), _self._opts.treeData);
}

/**
 * 树设置 
 */
dr.treeview.prototype.setting = function () {
	var _self = this;
	
    var setting = {
    		data: {
    			simpleData: {
    				enable: true
    			}
    		},
    		callback: {
    			//onClick: _self.onClick
    		}
    };
    
    return setting;
}

/**
 * 点击事件
 */
dr.treeview.prototype.onClick = function (event, treeId, treeNode, clickFlag) {
	console.log("[  onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
}		


