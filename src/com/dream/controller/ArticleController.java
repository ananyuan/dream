package com.dream.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dream.model.Article;
import com.dream.service.ArticleService;
import com.dream.utils.FreeMarkerUtils;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
    @RequestMapping(value="edit")
    public ModelAndView edit(Article article){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("article");
        
        if (StringUtils.isEmpty(article.getId())) {
        	article = new Article();
        } else {
        	int id = article.getId();
        	
        	article = articleService.findArticle(id);
        }
        
        mav.addObject("article",article);
        
        return mav;
    }
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Article save(@RequestBody Article article) {
    	article.setChanId(11);
    	article.setAtime("2014-10-29");
    	
    	String localurl = "html" + Constant.PATH_SEPARATOR + "article"
			 			+ Constant.PATH_SEPARATOR + article.getId() + ".html";
    	
    	article.setLocalurl(localurl);
    	
    	articleService.insert(article);
    	
    	//根据模板生成文件
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("article", article);
    	
    	String fileDir = Context.getSYSPATH() + "ftl" + Constant.PATH_SEPARATOR;
    	
    	String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articleToHtml.ftl", dataMap); 
    	
    	String filePath = Context.getSYSPATH() + localurl;
    	
    	File file = new File(filePath);
    	
    	try {
			FileUtils.writeStringToFile(file, articlesHtml, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
		return article;
	}
    
	
    
    @RequestMapping(value="/{name}", method = RequestMethod.GET)
	public @ResponseBody Article getArticleInJSON(@PathVariable int id, HttpSession session) {
 
    	return articleService.findArticle(id);
	}
    
    @RequestMapping(value="/articles", method = RequestMethod.GET)
	public @ResponseBody Article getChannels() {
    	Page<?> page = new Page();  //获取第一页的数据
    	List<Article> articles = articleService.findArticles(page);
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("articles", articles);
    	
    	String fileDir = Context.getSYSPATH() + "ftl" + Constant.PATH_SEPARATOR;
    	
    	String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articles.ftl", dataMap); //TODO
 
    	Article article = new Article();
    	
    	article.setContent(articlesHtml);
    	
		return article;
	}
    
    
    
}
