package com.dream.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.model.Article;
import com.dream.service.ArticleService;

@Controller
@RequestMapping("/artical")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
    @RequestMapping(value="/{name}", method = RequestMethod.GET)
	public @ResponseBody Article getArticleInJSON(@PathVariable int id, HttpSession session) {
 
    	return articleService.findArticle(id);
	}
	
}
