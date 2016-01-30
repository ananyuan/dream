package com.dream.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Context;
import com.dream.base.Page;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.message.MsgSender;
import com.dream.model.ActLog;
import com.dream.model.Article;
import com.dream.model.DictEntry;
import com.dream.model.Task;
import com.dream.model.org.User;
import com.dream.search.IndexArticleTask;
import com.dream.service.ActLogService;
import com.dream.service.ArticleService;
import com.dream.service.TaskService;
import com.dream.service.wechat.msg.ArticleItem;
import com.dream.utils.CommUtils;
import com.dream.utils.DateUtils;
import com.dream.utils.DictMgr;
import com.dream.utils.DrThreadPool;
import com.dream.utils.FileMgr;
import com.dream.utils.FreeMarkerUtils;
import com.dream.utils.KafkaProducer;
import com.dream.utils.SpringContextUtil;
import com.dream.utils.UuidUtils;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbsController {
	
	private static Log log = LogFactory.getLog(ArticleController.class);
	
	private static final String ARTICLE_FIRST_PAGE_FILE_NAME = "articles_first_page.html";
	
	@Autowired
	private ArticleService articleService;
	
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
    	if (null == session.getAttribute("USER")) {
    		mav.setViewName("login");
    	} else {
            mav.setViewName("article");
            
            //添加栏目列表
            List<DictEntry> chanList = DictMgr.getDict(Constant.DICT_CHANNEL).getChilds();
            
            mav.addObject("chanList", chanList);
            
            if (id.equals("_ADD_")) {
            	Article article = new Article();
            	article.setId(UuidUtils.base58Uuid());
            	mav.addObject("article", article);
            } else {
            	Article	article = articleService.findArticle(id);
                
                mav.addObject("article",article);
            }
    	}
    	
        return mav;
    }
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody String delete(@PathVariable String id, HttpSession session){
        
        //delete the files related
        FileMgr.deleteByDataId(id);
        
        //delete the generated html file 
        String localurl = CommUtils.getArticleLocal(id);
        File htmlFile = new File(Context.getSYSPATH() + localurl.substring(1));
        if (htmlFile.exists()) {
        	htmlFile.delete();
        }
        
        //delete main record
        articleService.delete(id);
        
        //添加一条删除的记录， 供客户端获取之后，删除客户端的记录
        ActLogService actLogService = SpringContextUtil.getBean("actLogService");
        
        ActLog actLog = new ActLog();
        actLog.setActType(Constant.ACT_TYPE_DELETE);
        actLog.setAtime(DateUtils.getDatetime());
        actLog.setDataId(id);
        actLog.setModelType("article");
        
        actLogService.insert(actLog);
    	
        //删除索引
        Article article = new Article();
        article.setId(id);
    	IndexArticleTask indexTask = new IndexArticleTask(article, IndexArticleTask.INDEX_TYPE_DEL);
    	DrThreadPool.getDefaultPool().execute(indexTask);
        
        return "";
    }
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Article save(@RequestBody Article article) {
    	boolean addFlag = false;
    	if (StringUtils.isEmpty(article.getAtime())) { //添加时间为空， 则为添加
    		
    		article.setAtime(DateUtils.getDatetime());
    		
    		addFlag = true;
    	}
    	
    	if (!StringUtils.isEmpty(article.getSummary())) {
    		String summary = CommUtils.getText(article.getSummary());
    		
    		if (summary.length() > 300) {
    			summary = summary.substring(0, 300);
    		}

			article.setSummary(summary);
    	}
    	
    	int chanId = article.getChanId();
    	
    	DictEntry entry = DictMgr.getEntry(Constant.DICT_CHANNEL, String.valueOf(chanId));
    	article.setChanname(entry.getName());
    	
    	String localurl = CommUtils.getArticleLocal(article.getId()); 
    	
    	article.setLocalurl(localurl);
    	
    	if (addFlag) {
    		articleService.insert(article);	
    		
    		//添加發一條推送的消息
    		MsgSender.pushOneUser("新消息", article.getTitle());
    	} else {
    		articleService.update(article);
    	}
    	
    	//根据模板生成文件
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("article", article);
    	
    	String fileDir = Context.getSYSPATH() + "ftl" + File.separator;
    	
    	String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articleToHtml.ftl", dataMap); 
    	
    	String filePath = Context.getSYSPATH() + localurl.substring(1);
    	File file = new File(filePath);
    	
    	try {
			FileUtils.writeStringToFile(file, articlesHtml, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//单个文件静态化完成之后，将列表第一页的也静态化
    	staticArticleList(Context.getSYSPATH() + CommUtils.getArticleLocalDir());
    	
    	//做索引
    	IndexArticleTask indexTask = new IndexArticleTask(article, IndexArticleTask.INDEX_TYPE_ADD);
    	DrThreadPool.getDefaultPool().execute(indexTask);
    	
    	//如果是添加才去发消息
    	if (addFlag) {
        	JSONObject obj = JSONObject.fromObject(article);
        	KafkaProducer.sendData(obj.toString(), "newsTopic");
    	}
    	
		return article;
	}

    /**
     * 静态化列表的第一页
     * @param filePath
     */
	private void staticArticleList(String filePath) {
		Page page = new Page();
		List<Article> articles = getArticles(page);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("articles", articles);
		dataMap.put("_PAGE_", page);
		
		String fileDir = Context.getSYSPATH() + "ftl" + File.separator;
		
		String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articles.ftl", dataMap); //TODO
		
    	try {
    		File file = new File(filePath + ARTICLE_FIRST_PAGE_FILE_NAME);
			FileUtils.writeStringToFile(file, articlesHtml, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	staticIndex2(articles, fileDir);
    	
        // 已/未 完成
        TaskService taskService = SpringContextUtil.getBean("taskService");
        Page taskPage = new Page();
        List<Task> finishTasks = taskService.findTasksFinish(taskPage);
        List<Task> todoTasks = taskService.findTasksTodo(taskPage);
    	
        dataMap.put("channels", DictMgr.getDict(Constant.DICT_CHANNEL).getChilds());
        dataMap.put("finishTasks", finishTasks);
        dataMap.put("todoTasks", todoTasks);
        
        String indexhtml = FreeMarkerUtils.parseString(fileDir, "index.html.ftl", dataMap);
        
    	try {
    		File file = new File(Context.getSYSPATH() + "index.html");
			FileUtils.writeStringToFile(file, indexhtml, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
	}

	
	/**
	 * 
	 * @param articles
	 * @param fileDir
	 */
	private void staticIndex2(List<Article> articles, String fileDir) {
    	//获取到图片列表
		Page page = new Page();
		page.setPageSize(4);
		List<Article> imgArticles = articleService.findArticlesHasImgs(page);
		for (Article myArticle: imgArticles) {
			String imgUrl = "";
			if (StringUtils.isNotEmpty(myArticle.getImgids())) {
				if (myArticle.getImgids().indexOf(",") > 0) {
					imgUrl = myArticle.getImgids().substring(0, myArticle.getImgids().indexOf(",")); 
				} else {
					imgUrl = myArticle.getImgids();
				}
				
				imgUrl = "http://yuananan.cn/file/" + imgUrl;
			} 
			myArticle.setImgids(imgUrl);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (articles.size() > 8) {
			dataMap.put("textArticles", articles.subList(0, 8));
		} else {
			dataMap.put("textArticles", articles);
		}
		
		dataMap.put("imgArticles", imgArticles);
		
    	String index2Html = FreeMarkerUtils.parseString(fileDir, "index2.html.ftl", dataMap); //TODO
    	try {
    		File file = new File(Context.getSYSPATH() + "index2.html");
			FileUtils.writeStringToFile(file, index2Html, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param page 分页对象
	 * @return 
	 */
	private List<Article> getArticles(Page page) {
		List<Article> articles = articleService.findArticles(page);
		
		for (Article article: articles) {
			if (article.getImgids().length() > 0) {
				String imgId = article.getImgids();
				if (imgId.indexOf(",") > 0) {
					imgId = imgId.substring(0, imgId.indexOf(","));
				}
				
				article.setImgids(imgId);
			}
		}
		return articles;
	}

	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value="/articles", method = RequestMethod.POST)
	public void getArticles(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    	HashMap<String, Object> params = CommUtils.getParams(request);
    	Page page;
    	if (null == params.get("_PAGE_")) {
    		page = new Page();  //获取第一页的数据
    	} else {
    		page = CommUtils.getPage(String.valueOf(params.get("_PAGE_")));
    	}
    	
    	String articlesHtml = "";
    	
    	if (page.getPageNo() == 1 && null == session.getAttribute("USER")) { // 如果是第一页, 并且没有登录，读取模板
    		
    		File file = new File(Context.getSYSPATH() + CommUtils.getArticleLocalDir() + ARTICLE_FIRST_PAGE_FILE_NAME); 
    		try {
				articlesHtml = FileUtils.readFileToString(file, "UTF-8");
			} catch (IOException e) {
				log.error("getArticles", e);
				articlesHtml = getArticleHtmlFromFtl(page);
			}
    	} else {
        	articlesHtml = getArticleHtmlFromFtl(page);
    	}
    	
    	String header = "text/html; charset=utf-8";
    	String content = "{\"_DATA_\":\""+CommUtils.encode(articlesHtml)+"\"}";
    	
    	try {
	    	response.setContentType(header);
	        PrintWriter out = response.getWriter();
	        out.write(content);
	        out.flush();
	        out.close();
    	}catch (Exception e) {
    		log.error("getArticles ", e);
    	}
	}
	
	/**
	 * 
	 * @param page 分页信息
	 * @return 通过ftl替换后的页面
	 */
	private String getArticleHtmlFromFtl(Page page) {
		List<Article> articles = getArticles(page);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("articles", articles);
		dataMap.put("_PAGE_", page);
		
		String fileDir = Context.getSYSPATH() + "ftl" + File.separator;
		
		String articlesHtml = FreeMarkerUtils.parseString(fileDir, "articles.ftl", dataMap); //TODO
		return articlesHtml;
	}
    
	@NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/id/{id}", method = RequestMethod.GET)
	public @ResponseBody Article getArticleInJSON(@PathVariable String id, HttpSession session) {
 
    	return articleService.findArticle(id);
	}
	
	@NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/clickCount/{id}", method = RequestMethod.GET)
	public @ResponseBody int clickCount(@PathVariable String id, HttpSession session) {
		Article article = articleService.findArticle(id);
		article.setClickcount(article.getClickcount() + 1);
		articleService.update(article);
		
    	return article.getClickcount();
	}
	
    
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/list/{lasttime}", method = RequestMethod.GET)
	public @ResponseBody List<Article> getArticlesInJSON(@PathVariable String lasttime) {
    	return articleService.findArticles(lasttime);
	}
    
    
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("article/article_list");
        
        return mav;
    }
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("SickController getRtnDataList");
		
    	User user = (User)session.getAttribute("USER");
    	
    	Page page = listPage.getPage();
    	
		List<Article> rtnList = articleService.findArticles(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		Map<String, String> entryMap = DictMgr.getEntrysMap(Constant.DICT_CHANNEL);
		
		for (Article article: rtnList) {
			String caozuo = "";
			if (null != user) {
				caozuo += "<a href='/article/edit/"+article.getId()+"'>编辑</a>";
				
				caozuo += "&nbsp;<a href='#' onclick=deleteItem('"+article.getId()+"')>删除</a>";
			}
			
			caozuo += "&nbsp;<a href='"+article.getLocalurl()+"' target='_blank'>预览</a>";
			
			Map<String, Object> map = pojoToMap(article);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			map.put("chanId", entryMap.get(String.valueOf(article.getChanId())));
			
			String title = "<a href='" + article.getLocalurl() + "' target='_blank'>" + article.getTitle() + "</a>";
			map.put("title", title);
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
}
