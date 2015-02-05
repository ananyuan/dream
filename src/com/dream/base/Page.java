package com.dream.base;

import java.util.HashMap;
import java.util.List;
 
/**
 * 对分页的基本数据进行一个简单的封装
 */
public class Page<T> extends HashMap<Object, Object> {
 
    private int pageNo = 1; //页码，默认是第一页
    private int pageSize = 5; //每页显示的记录数，默认是15
    private int totalRecord; //总记录数
    private int totalPage; //总页数
    private List<T> results; //对应的当前页记录
    
    private String order; //排序
 
    public Page(){
    	this.setPageNo(1); //设置默认的页码是1
    }
    
    public int getPageNo() {
       return pageNo;
    }
 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
       this.setInt("pageNo", pageNo);
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
       this.setInt("pageSize", pageSize);
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
       
       this.setInt("totalRecord", totalRecord);
       this.setInt("totalPage", totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
       this.setInt("totalPage", totalPage);
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
 
    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
              .append(pageSize).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

    /**
	 * 设置上查询的值
	 * @param key
	 * @param value
	 */
	public void setStr(String key, String value) {
        put(key, value);
	}
	
	/**
	 * 获取查询的值
	 * @param key
	 * @return
	 */
	public String getStr(String key) {
        return (String)get(key);
	}
	
	
	/**
	 * 设置上查询的值
	 * @param key
	 * @param value
	 */
	public void setInt(String key, int value) {
        put(key, value);
	}
	
	/**
	 * 获取查询的值
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
        return Integer.parseInt((String)get(key));
	}
}