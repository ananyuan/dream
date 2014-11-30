package com.dream.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class ReceiveMailImap {

	private Log log = LogFactory.getLog(ReceiveMailImap.class);

	private String userMail;

	private String userPassword;

	private String mailHost;
	
	/** 邮件服务器 */
	private MailServer mailServ = new MailServer();

	public ReceiveMailImap(MailUser userMail) throws Exception {
		this.userMail = userMail.getMail();
		this.userPassword = userMail.getPassword();
		
		this.mailHost = mailServ.getHost();
	}
	
	/**
	 * 
	 * @param folder
	 * @param store
	 * @return
	 * @throws MessagingException
	 */
	private MailBox getMailBox(Folder folder, Store store, String messageId) throws MessagingException {
		MailProps mailProps = new MailProps(mailServ);
		Properties props = mailProps.getProps();

		Session session = Session.getDefaultInstance(props, null);
		if (mailProps.isServTypeImap()) {
			store = session.getStore("imap");	
		} else {
			store = session.getStore("pop3");
		}
		
		store.connect(mailHost, userMail, userPassword);

		folder = store.getFolder("Inbox");
		
		if (mailProps.isServTypeImap()) {
			folder.open(Folder.READ_WRITE);
		} else {
			folder.open(Folder.READ_ONLY);
		}
		
		Message[] messages;
		
		if (mailProps.isServTypeImap()) { //IMAP
			if (messageId.length() > 0) { //指定了messageId , 指定收件人和messageID获取
				SearchTerm messageIDTerm = new MessageIDTerm(messageId);
				
				messages = folder.search(messageIDTerm);
			} else {
                boolean getAllMail = false;
				
                if (getAllMail) {
                	messages = folder.getMessages();
                } else { //如果之前有收过邮件， 则从那个时间开始
					int count = folder.getMessageCount();
					
					if (count > 4) {
						messages = folder.getMessages(count - 4, count); 	
					} else {
						messages = folder.getMessages();
					}
				}
			}
		} else { //POP3 , 
			messages = folder.getMessages();
		}
		
		MailBox mailBox = new MailBox();
		
		mailBox.setFolder(folder);
		mailBox.setStore(store);
		mailBox.setMessages(messages);
		
		return mailBox;
	}
	
	/**
	 * 
	 * @param messageId Message-ID
	 * @param seen 是否已读
	 * @throws Exception 异常
	 */
	public void setMailSeen(String messageId, boolean seen) throws Exception {
		chgMailStatus(messageId, Flags.Flag.SEEN, seen);
	}

	/**
	 * 
	 * @param messageId Message-ID
	 * @param seen 是否已读
	 * @throws Exception 异常
	 */
	public void deleteMail(String messageId) throws Exception {
		chgMailStatus(messageId, Flags.Flag.DELETED, true);
	}
	
	/**
	 * 设置单条邮件的状态值
	 * @param messageId 邮件的Message-ID
	 * @param flag Flags.Flag.DELETED/ Flags.Flag.SEEN
	 * @param status 状态值
	 * @throws Exception
	 */
	private void chgMailStatus(String messageId, Flag flag, boolean status) throws Exception {
		Folder folder = null;
		Store store = null;
		try {
			MailBox mailBox = getMailBox(folder, store, messageId);
			folder = mailBox.getFolder();
			store = mailBox.getStore();
			Message[] messages = mailBox.getMessages();
			
			log.debug("chgMailStatus , messageId = " + messageId);

			for (int i = 0; i < messages.length; ++i) {
				System.out.println("MESSAGE #" + (i + 1) + ":");
				Message msg = messages[i];

				msg.setFlag(flag, status); 
			}
		} catch (Exception e) {
			log.error("Exception:  ", e);
			throw e;
		} finally {
			try {
				if (folder != null) {
					folder.close(true);  //
				}

				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				//log.error("MessagingException:  ", e);
			}
		}		
	}
	
	
	
	/**
	 * 收取邮件
	 * @throws Exception 
	 */
	public HashMap<String, String> receiveMail() throws Exception {
		
		HashMap<String, String> fileMsgs = new HashMap<String, String>();
		
		Folder folder = null;
		Store store = null;
		try {
			MailBox mailBox = getMailBox(folder, store, "");
			folder = mailBox.getFolder();
			store = mailBox.getStore();
			Message[] messages = mailBox.getMessages();
			
			
			log.debug("No of Messages : " + folder.getMessageCount());
			log.debug("No of Unread Messages : " + folder.getUnreadMessageCount());

			for (int i = 0; i < messages.length; ++i) {
				System.out.println("MESSAGE #" + (i + 1) + ":");
				Message msg = messages[i];
				
				try {
				   parseMsg(msg, fileMsgs);
				} catch (Exception e) {
					log.error("解析message出错 " , e);
				}
			}
		} catch (Exception e) {
			log.error("Exception:  ", e);
			throw e;
		} finally {
			try {
				if (folder != null) {
					folder.close(true);  //
				}

				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				
				//log.error("MessagingException:  ", e);
			}
		}
		
		
		return fileMsgs;
	}

	/**
	 * 解析邮件内容
	 * @param msg
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void parseMsg(Message msg, HashMap<String, String> fileMsgs) throws IOException, MessagingException {
		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~ 解析开始 " );
		ParseMessage parseMessage = new ParseMessage(msg, userMail);
		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~ 解析结束 " );
		
		MailBean mailBean = parseMessage.getMailBean();
		
		List<MailContent> contentList = parseMessage.getContentList();
		
		if (!StringUtils.isEmpty(mailBean.getFrom())) {
		    if (contentList.size() > 0) { //设置mailBean的Content
		    	String plainContent = plainContent(contentList);
		    }
		}
	}

	/**
	 * 
	 * @param contentList 正文的列表
	 * @return 正文列表中的 文字内容
	 */
	private String plainContent(List<MailContent> contentList) {
		String content = contentList.get(0).getContent();
		for (MailContent conBean: contentList) {
			//如果存在TEXT/HTML类型的 ， 则提取里面的内容， 否则直接用
			if (conBean.getType().equalsIgnoreCase("text/html")) {
				Document doc = Jsoup.parse(conBean.getContent());

				content = doc.body().text(); 
				
				break;
			}
		}
		
		return content;
	}
	
	class MailBox {
		Store store; 
		Folder folder;
		Message[] messages;
		public Store getStore() {
			return store;
		}
		public void setStore(Store store) {
			this.store = store;
		}
		public Folder getFolder() {
			return folder;
		}
		public void setFolder(Folder folder) {
			this.folder = folder;
		}
		public Message[] getMessages() {
			return messages;
		}
		public void setMessages(Message[] messages) {
			this.messages = messages;
		}
	}

}
