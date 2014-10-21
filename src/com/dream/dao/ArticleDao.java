package com.dream.dao;

import java.util.List;

import com.dream.model.Article;

public interface ArticleDao {

	public int insert(Article article);

	public int update(Article article);

	public int delete(String id);

	public List<Article> selectAll();
	
	public List<Article> findArticles(int chanId);

	public int countAll();

	public Article findArticle(int id);
	
}
