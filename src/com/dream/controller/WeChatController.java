package com.dream.controller;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.Page;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.model.Article;
import com.dream.service.ArticleService;
import com.dream.service.wechat.msg.ArticleItem;
import com.dream.service.wechat.msg.MessageUtil;
import com.dream.service.wechat.msg.NewsMessage;
import com.dream.service.wechat.msg.TextMessage;
import com.dream.utils.CommUtils;
import com.dream.utils.SpringContextUtil;


@Controller
@RequestMapping("/wechat/req")
public class WeChatController {

	private static Log log = LogFactory.getLog(WeChatController.class);
	
	public static final String TOKEN = "this1is2a3testtokenforwechat";

	
	
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String reqGet(HttpServletRequest request) {
    	HashMap<String, Object> params = CommUtils.getParams(request);
    	
		String signature = (String)params.get("signature");
		// 时间戳
		String timestamp = (String)params.get("timestamp");
		// 随机数
		String nonce = (String)params.get("nonce");
		// 随机字符串
		String echostr = (String)params.get("echostr");
    	
		if (checkSignature(signature, timestamp, nonce)) {
			log.error("-----echostr---" + echostr);
			
			return echostr;
		}
    	
    	return echostr;
	}
	
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(method = RequestMethod.POST)
	public void reqPost(HttpServletRequest request, HttpServletResponse response) {
		String respMessage = "";
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			
			Iterator<String> it = requestMap.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				String value =	requestMap.get(key);
				
				
				log.error("-------post---from---wechat------------------key---" + key + "--value--" + value);
			}
			

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				if ("1".equalsIgnoreCase(content)) {
					respMessage = getNewsMsg(fromUserName, toUserName);
				} else if ("2".equalsIgnoreCase(content)) {
					textMessage.setContent("something rtn for 2");
					// 将文本消息对象转换成xml字符串
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} else if ("news".equalsIgnoreCase(content)) {
					respMessage = getNewsMsg(fromUserName, toUserName);
				} else {
					textMessage.setContent("回复1有惊喜哦");
					// 将文本消息对象转换成xml字符串
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventKey = requestMap.get("EventKey");
				String event = requestMap.get("Event");
				
				
				if (event.equalsIgnoreCase("subscribe") || eventKey.equalsIgnoreCase("subscribe")) {
					textMessage.setContent("欢迎关注欣明德！\n回复1有惊喜哦");
					// 将文本消息对象转换成xml字符串
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} else if (eventKey.indexOf("clickmenu1.1") >= 0) {
					respMessage = getNewsMsg(fromUserName, toUserName);
				} else if (eventKey.indexOf("clickmenu") >= 0) {
					textMessage.setContent("您点击了菜单，触发了 " + eventKey);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
			}
		} catch (Exception e) {
			log.error("reqPost ", e);
		}
    	
		response.setCharacterEncoding("UTF-8");
    	String header = "text/xml; charset=utf-8";
    	
    	try {
	    	response.setContentType(header);
	        PrintWriter out = response.getWriter();
	        out.write(respMessage);
	        out.flush();
	        out.close();
    	}catch (Exception e) {
    		log.error("reqPost resposne ", e);
    	}
	}

	private String getNewsMsg(String fromUserName, String toUserName) {
		String respMessage;
		NewsMessage newsMsg = new NewsMessage();
		newsMsg.setToUserName(fromUserName);
		newsMsg.setFromUserName(toUserName);
		newsMsg.setCreateTime(new Date().getTime());
		newsMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMsg.setFuncFlag(0);
		
		newsMsg.setArticleCount(5);
		
		List<ArticleItem> articles = getArticles();
		newsMsg.setArticles(articles);
		
		respMessage = MessageUtil.newsMessageToXml(newsMsg);
		return respMessage;
	}
    
	private List getArticles() {
		ArticleService articleService = SpringContextUtil.getBean("articleService");
		
		Page page = new Page();
		
		List<ArticleItem> rtnArticles = new ArrayList<ArticleItem>();
		List<Article> articles = articleService.findArticlesHasImgs(page);
		for (Article myArticle: articles) {
			ArticleItem item = new ArticleItem();
			item.setDescription(myArticle.getTitle());
			item.setTitle(myArticle.getTitle());
			item.setUrl("http://yuananan.cn/" + myArticle.getLocalurl());
			
			String imgUrl = "";
			if (StringUtils.isNotEmpty(myArticle.getImgids())) {
				if (myArticle.getImgids().indexOf(",") > 0) {
					imgUrl = myArticle.getImgids().substring(0, myArticle.getImgids().indexOf(",")); 
				} else {
					imgUrl = myArticle.getImgids();
				}
				
				imgUrl = "http://yuananan.cn/file/" + imgUrl;
			} else {
				imgUrl = "http://yuananan.cn/file/MMhuwe1vidwt987q1FWHSR";
			}
			item.setPicUrl(imgUrl);
			
			rtnArticles.add(item);
		}
		
		
		return rtnArticles;
	}

	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { TOKEN, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException", e);
		}

		content = null;
		
		// 将sha1加密后的字符串可与signature对比
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
}


