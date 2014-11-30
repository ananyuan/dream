package com.dream.mail;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.mail.Flags;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dream.utils.DateUtils;

public class ParseMessage {
	
	private Log log = LogFactory.getLog(ParseMessage.class);
	
	/**
	 * 邮件的Message对象
	 */
	private Message message;
	
	/**
	 * mail Header 中的信息
	 */
	private MailBean mailBean = new MailBean();	
	
	/**
	 * 邮件的正文信息
	 */
	private List<MailContent> contentList = new ArrayList<MailContent>();

	
	
	/** 带有编码信息的头信息 */
	private final String[] CHARTSET_HEADER = new String[] { "Subject", "From",
			"To", "Cc", "Delivered-To" };
	
	/**
	 * 
	 * @param message 一条邮件 Message对象
	 * @param receiverMail 当前收件人的邮件地址
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public ParseMessage(Message message, String receiverMail) throws IOException, MessagingException {
		this.message = message;
		
		mailBean.setReceiver(receiverMail);
		
		parseMsg();
	}

	/**
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void parseMsg() throws IOException, MessagingException {
		String messageId = "";
		try {
			messageId = getMessageId(message);
			if (messageId.length() == 0) {
				return;
			}

		} catch (MessagingException e) {
			log.error("获取 messageId出错 ------------" + e.getMessage());
		}

		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~ 开始multi "  );
		String headCharset = getHeadCharset(); // 头信息中存在的编码

		Object content = message.getContent();
		
		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~ 开始parseBaseHeader " );
		parseBaseHeader(); //往mailBean中添加基础信息
		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~ 结束parseBaseHeader "  );
		
		parseMultiParts(content, headCharset); //解析邮件中的正文及附件信息
		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~ 结束multi "  );
		
		postParse(); //根据解析的结果，再次填充某些值，如通过fileList 往mailBean 中塞入 FILE_FLAG 是否有文件的标识
	}
	
	private void postParse() {
//		if (fileList.size() > 0) {
//			mailBean.setHasFile(true);
//		}
	}

	/**
	 * 
	 * @return 发送时间
	 */
	public static String getSendTime(Message message) {
		try {
			Date sentDate = message.getSentDate(); // 发送时间
			
			return DateUtils.getDatetime(sentDate);
		} catch (MessagingException e) {
			
		} 
		
		return DateUtils.getDatetime();
	}

	/**
	 * 处理邮件的基本信息(Header) ,不包含正文部分 也不包含附件部分
	 * @throws MessagingException 
	 */
	private void parseBaseHeader() throws MessagingException {
		String from = getAddressHeader(message.getHeader("From")).trim();
		

		String subject = message.getSubject(); // 标题
		Date receiveDate = message.getReceivedDate(); // 接收时间

		
		mailBean.setSubject(subject);
		mailBean.setFrom(from);
		mailBean.setSendTime(getSendTime(message));
		mailBean.setTo(message.getHeader("To").toString());
		if (null != receiveDate) {
			mailBean.setReceiveTime(DateUtils.getDatetime(receiveDate));
		}
		mailBean.setBcc(getAddressHeader(message.getHeader("BCC")));
		mailBean.setCc(getAddressHeader(message.getHeader("CC")));

		
		String messageId = getMessageId(message);
		mailBean.setMessageId(messageId);

		//是否已读
		mailBean.setOpen(true);
		if (!message.isSet(Flags.Flag.SEEN) && !message.isSet(Flags.Flag.ANSWERED)) {
			mailBean.setOpen(false);
		}
		mailBean.setHasFile(false);
	}



	/**
	 * 解析邮件中的正文及附件信息
	 * @param content
	 *            message中的content对象
	 * @param dataId
	 *            message本地化的主键
	 * @param headCharset
	 *            头信息中的编码信息
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void parseMultiParts(Object content, String headCharset) throws IOException, MessagingException {
		if (content instanceof Multipart) {
			Multipart multi = ((Multipart) content);
			int parts = multi.getCount();
			String contentType = multi.getContentType();

			ContentType multiConType = new ContentType(contentType);

			if (null != multiConType.getParameter("charset")) { // 如果自己的部分上带有编码信息，则不用传进来的
				headCharset = multiConType.getParameter("charset");
			}

			for (int j = 0; j < parts; ++j) {
				MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
				ContentType partConType = new ContentType(part.getContentType());

				if (null != partConType.getParameter("charset")) { // 如果自己的部分上带有编码信息，则不用传进来的
					headCharset = partConType.getParameter("charset");
				}

				if (part.getContent() instanceof Multipart) { // 有嵌套，递归
					parseMultiParts(part.getContent(), headCharset);
				} else {
					// 判断 part 类型 ， 决定是否是附件
					String fileName = partConType.getParameter("name");
					if (null == fileName) { // 正文
						// 处理多个正文 , multiConType 下面都是一样的，一次就处理了
						if (multiConType.getBaseType().equalsIgnoreCase("multipart/alternative")) {
							handleContent(headCharset, multi, parts);
							break;
						} else { // 如果没有 multipart/alternative ， 漏掉了正文，// 就到这个地方去处理仅有的这一个正文
							savePrimaryContent(headCharset, part);
							
						}
					} else { // 附件
						if (null != part) {
							//saveFujian(part, j);
						}
					}
				}
			}
		} else { // 直接显示成正文的邮件
			ContentType contenttype = new ContentType(message.getContentType());
			String mimeType = contenttype.getBaseType();

			//构造一个正文的对象 
			MailContent mailContent = new MailContent();
			
			mailContent.setContent((String)content);
			mailContent.setType(mimeType.toUpperCase());
			mailContent.setSort(0);
			contentList.add(mailContent);
			
		}
	}
	


	/**
	 * 处理正文
	 * 
	 * @param dataId
	 * @param headCharset
	 * @param multi
	 * @param partNum
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void handleContent(String headCharset, Multipart multi, int partNum) throws MessagingException,
			IOException {
		if (partNum <= 0) {
			return;
		}

		String charset = headCharset;

		MimeBodyPart part0 = (MimeBodyPart) multi.getBodyPart(0);

		charset = savePrimaryContent(charset, part0); //往  contentList 中添加第一个 正文

		if (partNum > 1) { //不止一种正文格式， 则要处理到关联表里去
			for (int j = 1; j < partNum; j++) {
				MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);

				persisContent(part, charset, j);
			}
		}
	}
	

	
	
	/**
	 * 
	 * @param dataId
	 * @param msg
	 * @param charset
	 * @param bodyPart
	 * @return
	 * @throws ParseException
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String savePrimaryContent(String charset, MimeBodyPart bodyPart) throws ParseException,
			MessagingException, IOException {
		
		ContentType contentType = new ContentType(bodyPart.getContentType());

		if (null != contentType.getParameter("charset")) { // 如果自己的部分上带有编码信息，则不用传进来的
			charset = contentType.getParameter("charset");
		}
		String mimeType = contentType.getBaseType();

		
		MailContent mailContent = new MailContent();
		mailContent.setSort(0);
		mailContent.setType(mimeType.toUpperCase());
		
		if (bodyPart.isMimeType("text/html")) {
			mailContent.setContent((String) bodyPart.getContent());
		} else if (bodyPart.isMimeType("text/plain")) {
			String contentStr = getContentStr(charset, bodyPart.getInputStream());

			mailContent.setContent(contentStr);
		}
		
		contentList.add(mailContent);
		
		return charset;
	}
	

	
	/**
	 * 
	 * @param orignalStr
	 *            "=?utf-8?B?5p2oIOeRnA==?=" <yangyu@staff.zotn.com>,
	 *            "=?utf-8?B?5p2o55Gc?=" <yangyu@ruaho.com>
	 * @return 汉字 <code@ruaho.com>,
	 */
	private String getAddressHeader(String[] orignalStrs) {
		if (null == orignalStrs) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		try {
			for (String orignalStr : orignalStrs) {

				orignalStr = orignalStr.replaceAll("\\r", "")
						.replaceAll("\\n", "").replaceAll("\\t", "");

				System.out.println("orignalStr = " + orignalStr);
				for (String orign : orignalStr.split(",")) {
					// orign 有可能被双引号括起来了, 去掉双引号之后再解析
					System.out.println("orignal = " + orign);

					orign = orign.replaceAll("\"", "");

					if (existEncodedWord(orign)) {
						String[] oneUser = orign.trim().split(" ");

						sb.append(MimeUtility.decodeWord(oneUser[0]))
								.append(" ").append(oneUser[1]).append(",");

					} else {
						sb.append(orign).append(",");
					}
				}
			}

			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	/**
	 * 
	 * @param orign
	 *            原始串
	 * @return 是否存在编码
	 */
	private boolean existEncodedWord(String orign) {

		if (orign.indexOf("=?") >= 0) {
			return true;
		}

		return false;
	}
	
	
	/**
	 * 
	 * @param part
	 * @param dataId
	 * @param charset
	 * @param sort
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void persisContent(MimeBodyPart part, String charset, int sort) throws MessagingException, IOException {
		ContentType contentType = new ContentType(part.getContentType());

		if (null != contentType.getParameter("charset")) { // 如果自己的部分上带有编码信息，则不用传进来的
			charset = contentType.getParameter("charset");
		}

		String contentStr = "";
		if (part.isMimeType("text/html")) {
			contentStr = (String) part.getContent();
		} else if (part.isMimeType("text/plain")) {
			contentStr = getContentStr(charset, part.getInputStream());
		}

		
		
		MailContent mailContent = new MailContent();
		mailContent.setSort(sort);
		mailContent.setType(contentType.getBaseType().toUpperCase());
		mailContent.setContent(contentStr);
		
		contentList.add(mailContent);
	}
	
	/**
	 * 
	 * @param charset
	 *            编码集
	 * @param partInputStream
	 *            MimeBodyPart的InputStream
	 * @return 正文内容
	 * @throws IOException
	 */
	private String getContentStr(String charset, InputStream partInputStream)
			throws IOException {
		String contentStr = "";

		InputStream in = null;
		try {
			// 正文内容
			in = new BufferedInputStream(partInputStream);

			contentStr = IOUtils.toString(in, charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}

		return contentStr;
	}
	
	
	/**
	 * 从Header中获取 Message-ID
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	public static String getMessageId(Message msg) throws MessagingException {
		
		return ((MimeMessage) msg).getMessageID();
	}
	
	/**
	 * 
	 * @return 头部存在的编码信息  TODO 这块取头部编码的，优化
	 * @throws MessagingException
	 */
	private String getHeadCharset() throws MessagingException {
		String headCharset = "";

		@SuppressWarnings("unchecked")
		Enumeration<Header> enums = message.getMatchingHeaders(CHARTSET_HEADER);
		while (enums.hasMoreElements()) {
			Header header = enums.nextElement();

			String headerValue = header.getValue();

			if (headerValue.startsWith("=?")) {
				String newHeadStr = headerValue.replaceFirst("=\\?", "");

				headCharset = newHeadStr.substring(0, newHeadStr.indexOf("?"));

				break;
			}
		}
		return headCharset;
	}


	/**
	 * 
	 * @return 邮件的主体Bean
	 */
	public MailBean getMailBean() {
		return mailBean;
	}

	/**
	 * 
	 * @return 邮件的正文的列表
	 */
	public List<MailContent> getContentList() {
		return contentList;
	}
	

}
