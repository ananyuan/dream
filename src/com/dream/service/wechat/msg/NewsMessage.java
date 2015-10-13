package com.dream.service.wechat.msg;

import java.util.List;

public class NewsMessage extends BaseMessage {
	private int ArticleCount;
	private List<ArticleItem> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<ArticleItem> getArticles() {
		return Articles;
	}

	public void setArticles(List<ArticleItem> articles) {
		Articles = articles;
	}
}



