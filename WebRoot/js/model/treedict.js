GLOBAL.namespace("dr");

/**
 * 将字典转换成弹出树
 */
dr.treedict = function(options) {
	var defaults = {
			"id":"treedict",
			"dict_id":"",
			"field_id":"",
			"field_name":""
		};
	this._opts = jQuery.extend(defaults,options);
	
	this.initData();
}

/**
 * 初始化数据
 */
dr.treedict.prototype.initData = function() {
	var _self = this;
	if (_self._opts.request_url) {  //给出了获取数据的url
		_self.zNodes = sendAjax(_self._opts.request_url, {}, "get");
		
		if (_self._opts.pid_field || _self._opts.id_field) {
			jQuery.each(_self.zNodes, function(index, item){
				item.pId = item[_self._opts.pid_field];
				item.id = item[_self._opts.id_field];
				item.open = true;
			});
		}
	} else {
		_self.zNodes = sendAjax("/dictentry/getDictItems/" + _self._opts.dict_id, {}, "get");
		
		jQuery.each(_self.zNodes, function(index, item){
			item.pId = item.pcode;
			item.id = item.code;
			item.open = true;
		});		
	}
}

/**
 * 显示到页面
 */
dr.treedict.prototype.showInPage = function() {
	var _self = this;
	jQuery.fn.zTree.init(jQuery("#" + _self._opts.div_id), _self.inPageSetting(), _self.zNodes);
}

/**
 * 显示到页面的设置
 */
dr.treedict.prototype.inPageSetting = function() {
	var _self = this;
	
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		} ,
		callback: {
			onClick : function (event, treeId, treeNode, clickFlag) {
				jQuery("#search_query_" + _self._opts.field_id).val(treeNode.id);
				
				//刷新页面
				_self._opts.refreshFunc.apply();
			}
			
		} 
	}
	
	return setting;
};

/**
 * 显示到弹出框
 */
dr.treedict.prototype.showDialog = function() {
	var _self = this;
	
	var options = {};
	options.treeData = _self.zNodes;
	options.title = _self._opts.title || "树";
	options.parHandler = this;
	options.selectLeaf = _self._opts.selectLeaf || false;
	options.callBack = function(id, name) {
		
		if (jQuery("#" + _self._opts.field_id).length > 0) {
			jQuery("#" + _self._opts.field_id).val(id);	
		}
		
		if (jQuery("#" + _self._opts.field_name).length > 0) {
			jQuery("#" + _self._opts.field_name).val(name);	
		}
	};
	
	var treeDialog = new dr.treeview(options);
	treeDialog.render();
}


