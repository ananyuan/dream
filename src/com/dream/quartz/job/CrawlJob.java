package com.dream.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dream.service.CrawlService;

public class CrawlJob extends QuartzJobBean {
	 private CrawlService crawlService = null;  
	    public CrawlService getCrawlService() {  
	        return crawlService;  
	    }  
	    public void setCrawlService(CrawlService crawlService) {  
	        this.crawlService = crawlService;  
	    }  
	    @Override  
	    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {  
	        this.crawlService.crawl();  
	          
	    }  
}
