package com.dream.controller.wf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.controller.AbsController;
import com.dream.controller.ListPageData;
import com.dream.model.wf.Line;
import com.dream.model.wf.Node;
import com.dream.model.wf.WfBean;
import com.dream.service.wf.LineService;
import com.dream.service.wf.NodeService;
import com.dream.service.wf.WfDefMgr;
import com.dream.service.wf.WfService;

@Controller
@RequestMapping("/wfdef")
public class WfDefController extends AbsController {
	
	private static Log log = LogFactory.getLog(WfDefController.class);
	
	@Autowired
	private WfService wfService;
	
	@Autowired
	private NodeService nodeService;
	
	@Autowired
	private LineService lineService;

    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("wf/wf");
        
        return mav;
    }
	
	
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("wf/wf_item");
        
        if (id.equals("_ADD_")) {
        	WfBean wfBean = new WfBean();
        	wfBean.setJsondef("{}");
        	mav.addObject("itemObj", wfBean);
        } else {
        	if (StringUtils.endsWithIgnoreCase(id, Constant.WF_URL_SUFFIX)) {
        		id = id.replace(Constant.WF_URL_SUFFIX, "");
        	}
        	
        	WfBean wfBean = wfService.findWf(id);
            
            mav.addObject("itemObj", wfBean);
        }
        
        return mav;
    }
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	Page page = listPage.getPage();
    	
		List<WfBean> rtnList = wfService.findWfs(page);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (WfBean wfBean: rtnList) {
			String editUrl = wfBean.getCode() + Constant.WF_URL_SUFFIX;
			
			String caozuo = "<a href='/wfdef/edit/" + editUrl + "'>编辑</a>&nbsp;<a href='/wfdef/delete/"+wfBean.getCode()+"'>删除</a>";
			
			Map<String, Object> map = pojoToMap(wfBean);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			mapList.add(map);
		}
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Map save(HttpServletRequest request, HttpSession session) {
    	try {
    		JSONObject myJsonObject =  (JSONObject) JSONSerializer.toJSON(request.getReader().readLine());
        	String wfdef = myJsonObject.getString("wfdef");
        	
        	String wfname = myJsonObject.getString("wfname");
        	String wfcode = myJsonObject.getString("wfcode");
        	WfBean wfBean = new WfBean();
        	wfBean.setCode(wfcode);
        	wfBean.setJsondef(wfdef);
        	wfBean.setName(wfname);
        	
        	WfBean oldWf = wfService.findWf(wfcode);
        	if (null == oldWf) {
        		wfService.insert(wfBean);	
        	} else {
        		wfService.update(wfBean);
        	}
        	
        	
        	//节点列表
        	JSONArray nodeArray = myJsonObject.getJSONArray("nodedef");
        	for (Object json: nodeArray) {
        		JSONObject jsonObj = (JSONObject) json;
        		Node node = new Node();
        		String nodeId = jsonObj.getString("id");
        		node.setId(nodeId);
        		node.setWfcode(wfcode);
        		node.setJsonstr(json.toString());
        		
        		Node oldNode = nodeService.findNode(nodeId);
        		if (null == oldNode) {
        			nodeService.insert(node);	
        		} else {
        			nodeService.update(node);
        		}
        	}
        
        	//连线列表
        	JSONArray lineArray = myJsonObject.getJSONArray("linedef");
        	for (Object json: lineArray) {
        		JSONObject jsonObj = (JSONObject) json;
        		Line line = new Line();
        		String lineId = jsonObj.getString("id");
        		
        		line.setId(lineId);
        		line.setWfcode(wfcode);
        		line.setJsonstr(json.toString());
        		
        		Line oldLine = lineService.findLine(lineId);
        		
        		if (null == oldLine) {
        			lineService.insert(line);	
        		} else {
        			lineService.update(line);
        		}
        	}
        	
        	//刷新缓存 TODO
        	WfDefMgr.removeFromCache(wfcode);
    	} catch (Exception e) {
    		log.error("save wf error ", e);
    	}

    	
    	return new HashMap();
    }	
    
    
    
    
}
