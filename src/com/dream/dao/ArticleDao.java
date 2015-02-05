package com.dream.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dream.base.Page;
import com.dream.model.Article;

public interface ArticleDao {

	public int insert(Article article);

	public int update(Article article);

	public int delete(String id);

	public int countAll();

	public Article findArticle(String id);
	
	public List<Article> findNewArticles(String atime);
	
	public List<Article> findArticles(Page<?> page);

	public List<Article> findArticlesByChannelId(@Param("page") Page<?> page, int channelId);
}
