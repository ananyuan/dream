package com.dream.search;

import java.util.ArrayList;
import java.util.List;
import com.dream.model.Article;
import com.dream.utils.DateUtils;
import com.dream.utils.ThreadTask;

public class IndexArticleTask extends ThreadTask {

	private Article article;
	
	private int indexType = 1;
	
	public static final int INDEX_TYPE_ADD = 1;
	
	public static final int INDEX_TYPE_DEL = 2;
	
	public IndexArticleTask(Article article, int indexType){
		this.article = article;
		
		this.indexType = indexType;
	}
	
	
	@Override
	public void execute() {
		
		if (indexType == INDEX_TYPE_ADD) { //添加索引
			BasicField basicData = new BasicField();
			basicData.setContent(article.getContent());
			basicData.setId(article.getId());
			basicData.setCreate_time(DateUtils.getDateFromString(article.getAtime()));
			basicData.setTitle(article.getTitle());
			basicData.setSummery(article.getSummary());
			basicData.setUrl(article.getLocalurl());
			
			List<String> keyWords = new ArrayList<String>();
			keyWords.add(article.getChanname());
			
			basicData.setKeywords(keyWords);
			
			SolrMgr.addIndex(basicData, null);
		} else if (indexType == INDEX_TYPE_DEL) { //删除索引
			SolrMgr.delIndex(article.getId());
		}
	}
}
