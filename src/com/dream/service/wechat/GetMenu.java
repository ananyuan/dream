package com.dream.service.wechat;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dream.service.wechat.menu.Button;
import com.dream.service.wechat.menu.Menu;
import com.dream.utils.WxUtils;

/**
 * 初始化公共号的菜单
 * @author anan
 *
 */
public class GetMenu {
	
	private static Logger log = LoggerFactory.getLogger(GetMenu.class);
	
	private GetMenu() {
	} 
	
	public static void createMenu() {
		Menu menu = getMenu();
		
		String token = WxUtils.getToken();
		
		// 拼装创建菜单的url
		String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN", token);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = WeixinUtil.createMenu(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
	}

	private static Menu getMenu() {
		
		Button sb2 = new Button("菜单1.1", "click", "clickmenu1.1", null, null);
		Button btn1 = new Button("菜单一", "click", null, null, new Button[] {sb2 });

		Button sb3 = new Button("菜单2.1", "click", "clickmenu2.1", null, null);
		Button sb4 = new Button("菜单2.2", "click", "clickmenu2.2", null, null);
		
		Button btn2 = new Button("菜单二", "click", null, null, new Button[] { sb3, sb4 });

		Button sb6 = new Button("主页", "view", null, "http://yuananan.cn", null);
		Button sb7 = new Button("微信js sdk", "view", null, "http://yuananan.cn/views/wxtest.jsp", null);
		
		Button btn3 = new Button("跳转菜单", "click", null, null, new Button[] {sb6 , sb7 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn3 });

		return menu;
	}
}
