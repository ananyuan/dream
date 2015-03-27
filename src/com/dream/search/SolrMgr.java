package com.dream.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.utils.ConfigUtils;

public class SolrMgr {
	private static Log log = LogFactory.getLog(SolrMgr.class);
	
	private static SolrServer solr;
	static {
		String indexUrl = ConfigUtils.getConf(Constant.INDEX_URL);
		
		solr = new HttpSolrServer(indexUrl);
	}

	/**
	 * 
	 * @param basicData
	 *            基础字段
	 * @param dynamicMap
	 *            动态字段
	 */
	public static void addIndex(BasicField basicData,
			HashMap<String, String> dynamicMap) {
		SolrInputDocument doc = new SolrInputDocument();

		if (null == doc.getFieldValue("id")
				|| 0 == doc.getFieldValue("id").toString().length()) {
			doc.addField("id", basicData.getId());
		}

		String title = basicData.getTitle();
		if (null != title && 0 < title.length()) {
			doc.addField("title", title);
		}
		
		String summery = basicData.getSummery();
		if (null != summery && 0 < summery.length()) {
			doc.addField("summery", summery);
		}

		String content = basicData.getContent();
		if (null != content && 0 < content.length()) {
			doc.addField("content", content);
		}
		
		if (null != basicData.getUrl()) {
			doc.addField("url", basicData.getUrl());
		}

		if (null != basicData.getCreate_time()) {
			doc.addField("create_time", basicData.getCreate_time());
		}

		if (null != basicData.getLast_modified()) {
			doc.addField("last_modified", basicData.getLast_modified());
		}

		List<String> keywords = basicData.getKeywords();
		if (null != keywords) {
			for (String keyword : keywords) {
				doc.addField("keywords", keyword);
			}
		}

		// 动态field
		if (null != dynamicMap) {
			Set<String> fieldKeys = dynamicMap.keySet();
			for (String field : fieldKeys) {
				doc.addField(field + BasicField.DYNAMIC_PREFIX_STR, dynamicMap.get(field));
			}
		}

		try {
			solr.add(doc);

			solr.commit(false, false);
		} catch (SolrServerException e) {
			log.error("SolrServerException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}

	/**
	 * 
	 * @param id
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static void delIndex(String id) {
		try {
			solr.deleteById(id);
			log.debug("delete index...");
			solr.commit();

		} catch (SolrServerException e) {
			log.error("SolrServerException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}

	/**
	 * 
	 * @param queryStr 查询的关键字
	 * @param page 分页信息
	 * @return 查询的结果
	 */
	public static List<HashMap<String, Object>> query(String queryStr, Page<?> page) {
        SolrQuery query = new SolrQuery();
        query.setQuery(queryStr);
        query.setParam("sort", "");
        query.setHighlight(true); 
        query.setFields("summery,id,title,url,create_time");
        int pageSize = page.getPageSize();
        query.setStart((page.getPageNo() - 1) * pageSize);
        query.setRows(pageSize);
        
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        
        try {
	        QueryResponse response = solr.query(query);
	        
	        long count = response.getResults().getNumFound();
	        long pages = count / pageSize;
	        if (0 < count % pageSize) {
	            pages++;
	        }
	        page.setTotalPage((int)pages);
	        page.setTotalRecord((int)count);
	
	        SolrDocumentList solrDocumentList = response.getResults();
	        Iterator<SolrDocument> iter = solrDocumentList.iterator();
	        
	        
	        while (iter.hasNext()) {
	            SolrDocument resultDoc = iter.next();
	            Collection<String> fieldsName = resultDoc.getFieldNames();
	            HashMap<String, Object> data = new HashMap<String, Object>();
	            for (String field : fieldsName) {
	                Object value = resultDoc.getFieldValue(field);
	                
	                data.put(field, value);
	            }
	
	            dataList.add(data);
	        }
        }catch (Exception e) {
        	log.debug("query", e);
        }
		
        return dataList;
	}
}
