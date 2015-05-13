package com.dream.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.mail.MailUser;
import com.dream.mail.ReceiveMailImap;
import com.dream.service.CrawlService;
import com.dream.utils.DateUtils;

public class CrawlServiceImpl implements CrawlService {

	private static Log log = LogFactory.getLog(CrawlServiceImpl.class);
	
	@Override
	public void crawl() {
		/**
		log.debug("in crawl --------------------------------" + DateUtils.getDatetime());
		
		MailUser userMail = new MailUser();
		userMail.setMail("");
		userMail.setPassword("");
		
		
		try {
			ReceiveMailImap receive = new ReceiveMailImap(userMail);
			
			receive.receiveMail();
		} catch (Exception e) {
			log.error("receive error", e);
		}
		
		*/
	}

	
	
	public static void main(String[] args) {
		
		CrawlServiceImpl xx = new CrawlServiceImpl(); 
		xx.crawl();
	}
}
