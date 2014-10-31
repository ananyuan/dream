package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.ArticleDao;
import com.dream.model.Article;
import com.dream.service.ArticleService;

public class ArticleServiceImpl implements ArticleService {

	private ArticleDao articleDao;
	
	@Override
	public int insert(Article article) {

		return articleDao.insert(article);
	}

	@Override
	public int update(Article article) {
		return articleDao.update(article);
	}

	@Override
	public int delete(String id) {
		return articleDao.delete(id);
	}

	@Override
	public int countAll() {
		return articleDao.countAll();
	}

	@Override
	public Article findArticle(String id) {
		return articleDao.findArticle(id);
	}

	@Override
	public List<Article> findArticles(Page<?> page) {
		return articleDao.findArticles(page);
	}

	@Override
	public List<Article> findArticlesByChannelId(Page<?> page, int chanId) {
		return articleDao.findArticlesByChannelId(page, chanId);
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}
