package com.dream.service;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Article;

/**
 * 文章类
 * @author anan
 *
 */
public interface ArticleService {
	public int insert(Article article);

	public int update(Article article);

	public int delete(String id);

	public int countAll();

	public Article findArticle(String id);
	
	public List<Article> findArticles(Page<?> page);
	
	public List<Article> findArticlesByChannelId(Page<?> page, int channelId);
}
