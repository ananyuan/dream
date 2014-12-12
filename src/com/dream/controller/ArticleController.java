package com.dream.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Context;
import com.dream.base.Page;
import com.dream.model.ActLog;
import com.dream.model.Article;
import com.dream.service.ActLogService;
import com.dream.service.ArticleService;
import com.dream.service.FileService;
import com.dream.utils.CommUtils;
import com.dream.utils.DateUtils;
import com.dream.utils.FileMgr;
import com.dream.utils.FreeMarkerUtils;
import com.dream.utils.SpringContextUtil;
import com.dream.utils.UuidUtils;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
    	if (null == session.getAttribute("USER")) {
    		mav.setViewName("login");
    	} else {
            mav.setViewName("article");
            
            if (id.equals("_ADD_")) {
            	Article article = new Article();
            	article.setId(UuidUtils.base58Uuid());
            	mav.addObject("article", article);
            } else {
            	Article	article = articleService.findArticle(id);
                
                mav.addObject("article",article);
            }
    	}
    	
        return mav;
    }
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable String id, HttpSession session){
        
        //delete the files related
        FileMgr.deleteByDataId(id);
        
        //delete the generated html file 
        String localurl = CommUtils.getArticleLocal(id);
        File htmlFile = new File(Context.getSYSPATH() + localurl.substring(1));
        if (htmlFile.exists()) {
        	htmlFile.delete();
        }
        
        //delete main record
        articleService.delete(id);
        
        //添加一条删除的记录， 供客户端获取之后，删除客户端的记录
        ActLogService actLogService = SpringContextUtil.getBean("actLogService");
        
        ActLog actLog = new ActLog();
        actLog.setActType(Constant.ACT_TYPE_DELETE);
        actLog.setAtime(DateUtils.getDatetime());
        actLog.setDataId(id);
        actLog.setModelType("article");
        
        actLogService.insert(actLog);
    	
        return "";
    }
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Article save(@RequestBody Article article) {
    	boolean addFlag = false;
    	if (StringUtils.isEmpty(article.getAtime())) { //添加时间为空， 则为添加
    		
    		article.setAtime(DateUtils.getDatetime());
    		
    		addFlag = true;
    	}
    	
    	if (!StringUtils.isEmpty(article.getSummary())) {
    		String summary = article.getSummary();
    		if (summary.length() > 300) {
    			article.setSummary(summary.substring(0, 300));
    		}
    		
    	}
    	
    	
    	article.setChanId(11);
    	String localurl = CommUtils.getArticleLocal(article.getId()); 
    	
    	article.setLocalurl(localurl);
    	
    	article.setSummary(CommUtils.getText(article.getSummary()));
    	
    	if (addFlag) {
    		articleService.insert(article);	
    	} else {
    		articleService.update(article);
    	}
    	
    	//根据模板生成文件
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("article", article);
    	
    	String fileDir = Context.getSYSPATH() + "ftl" + File.separator;
    	
    	String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articleToHtml.ftl", dataMap); 
    	
    	String filePath = Context.getSYSPATH() + localurl.substring(1);
    	File file = new File(filePath);
    	
    	try {
			FileUtils.writeStringToFile(file, articlesHtml, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
		return article;
	}

	@RequestMapping(value="/articles", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArticles(HttpServletRequest request, HttpSession session) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	HashMap<String, Object> params = CommUtils.getParams(request);
    	Page page;
    	if (null == params.get("_PAGE_")) {
    		page = new Page();  //获取第一页的数据
    	} else {
    		page = CommUtils.getPage(String.valueOf(params.get("_PAGE_")));
    	}
    	request.getParameterNames();
    	List<Article> articles = articleService.findArticles(page);
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("articles", articles);
    	dataMap.put("_PAGE_", page);
    	
    	if (null != session.getAttribute("USER")) {
    		rtnMap.put("_SESSION_", session.getAttribute("USER"));
    		
    		dataMap.put("canEdit", true);
    	} 
    	
    	
    	String fileDir = Context.getSYSPATH() + "ftl" + File.separator;
    	
    	String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articles.ftl", dataMap); //TODO
 

    	rtnMap.put("_DATA_", articlesHtml);
    	
		return rtnMap;
	}
    
	
    
    @RequestMapping(value="/id/{id}", method = RequestMethod.GET)
	public @ResponseBody Article getArticleInJSON(@PathVariable String id, HttpSession session) {
 
    	return articleService.findArticle(id);
	}
    
    
    @RequestMapping(value="/list/{lasttime}", method = RequestMethod.GET)
	public @ResponseBody List<Article> getArticlesInJSON(@PathVariable String lasttime) {
    	return articleService.findArticles(lasttime);
	}
}
