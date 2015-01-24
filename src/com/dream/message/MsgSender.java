package com.dream.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class MsgSender {

	private static Log log = LogFactory.getLog(MsgSender.class);

	private final static int ACCESS_ID = 2100080357;

	private final static String SECRET_KEY = "a145f10203a009db96e2c2cf9e410466";

	private final static String appId = "fQreIhB0qrAXGfsZtCtyQ";
	private final static String appkey = "QVvV70f9hD9K5fvQ89qo88";
	private final static String master = "1asVIdrmAf9qGzXUIsYFxA";
	private final static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	public static void pushOneUser(String title, String content) {
		try {
			IGtPush push = new IGtPush(host, appkey, master);
			push.connect();

			NotificationTemplate template = notificationTemplateDemo(title, content);
			SingleMessage message = new SingleMessage();
			message.setOffline(true);
			// 离线有效时间，单位为毫秒，可选
			message.setOfflineExpireTime(24 * 3600 * 1000);
			message.setData(template);
			// message.setPushNetWorkType(1);
			// //判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
			Target target = new Target();

			target.setAppId(appId);
			target.setClientId("1adf64f2fabcefa1fba5f544430bdc23");
			// 用户别名推送，cid和用户别名只能2者选其一
			// String alias = "个";
			// target.setAlias(alias);
			IPushResult ret = push.pushMessageToSingle(message, target);
			System.out.println(ret.getResponse().toString());
		} catch (Exception e) {
			log.error("pushOneUser", e);
		}
	}

	public static NotificationTemplate notificationTemplateDemo(String title, String content) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(content);
		// 配置通知栏图标
		template.setLogo("ic_launcher.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入您要透传的内容");
		return template;
	}

	public static void main(String[] args) {
		// MsgSender.demoPushAllDevice("新消息", "设备的账户或别名由终端SDK在调用推送注册接口时设置");

		//MsgSender.pushOneUser("yuananan", "新消息1", "设备的账户或别名由终端SDK在调用推送注册接口时设置");
	}

}
