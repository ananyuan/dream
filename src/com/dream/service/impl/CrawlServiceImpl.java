package com.dream.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.base.DateUtils;
import com.dream.service.CrawlService;

public class CrawlServiceImpl implements CrawlService {

	private static Log log = LogFactory.getLog(CrawlServiceImpl.class);
	
	@Override
	public void crawl() {
		log.debug("in crawl --------------------------------" + DateUtils.getDatetime());
	}

}
