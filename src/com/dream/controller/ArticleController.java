package com.dream.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
