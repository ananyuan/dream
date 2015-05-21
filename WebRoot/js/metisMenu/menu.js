GLOBAL.namespace("dr");


dr.menu = function(options) {
	var defaults = {
			"id":"",
			"menuData":""
		};
	this._opts = jQuery.extend(defaults,options);		
}

/**
 * 
 */
dr.menu.prototype.render = function () {
	var _self = this;
	var param = {};
	
	var menuData = sendAjax("/menu/getAllMenu", param, "get");
	
	var leftMenu = jQuery("#side-menu");
	jQuery.each(menuData, function(index, item){
		if (item.mtype == "FOLDER") {
			
			var folderLi = jQuery("<li></li>").appendTo(leftMenu);
			
			var foldera = jQuery("<a href='#'><i class='"+item.mclass+"'></i> "+item.name+"<span class='fa arrow'></span></a>").appendTo(folderLi);
			
			var subUl = jQuery("<ul class='nav nav-second-level'></ul>").appendTo(folderLi);
			
			if (item.childs) {
				jQuery.each(item.childs, function(index, subItem) {
					if (subItem.mtype == "FOLDER") { //还有第三级别
						var folderSecondLi = jQuery("<li></li>").appendTo(subUl);
						
						var foldera = jQuery("<a href='#'> "+subItem.name+"<span class='fa arrow'></span></a>").appendTo(folderSecondLi);
						
						var thirdUl = jQuery("<ul class='nav nav-third-level'></ul>").appendTo(folderSecondLi);
												
						
						if (subItem.childs) {
							jQuery.each(subItem.childs, function(index, thirdItem) {
								var thirdMenu = jQuery("<li><a href='#'>"+thirdItem.name+"</a></li>").appendTo(thirdUl);
							
								thirdMenu.bind("click", function(){
									_self.clickEvent(thirdItem);
								});
							});
						}
					} else {
						var subMenu = jQuery("<li><a href='#'>"+subItem.name+"</a></li>").appendTo(subUl);
						
						subMenu.bind("click", function(){
							_self.clickEvent(subItem);
						});
					}
				});	
			}
		} else {
			var folderLi = jQuery("<li><a href='"+item.url+"'><i class='"+item.mclass+"'></i> "+item.name+"</a></li>");
			
			folderLi.appendTo(leftMenu);
		}
	});
	
	jQuery('#side-menu').metisMenu();
}

/**
 * 菜单点击事件
 */
dr.menu.prototype.clickEvent = function (menuItem) {
	var frameId = "right_main_iframe";
	var iframe = document.getElementById(frameId);
	if (!iframe) {
		iframe = parent.document.getElementById(frameId);
	}	
	iframe.style.height = "0px";
	
	jQuery("#" + frameId).attr("src", menuItem.url);
	
	resetFrameHei();
}

