package com.dream.controller.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

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
import com.dream.base.Page;
import com.dream.base.TreeBean;
import com.dream.controller.AbsController;
import com.dream.controller.ListPageData;
import com.dream.model.org.Dept;
import com.dream.model.org.User;
import com.dream.service.org.UserService;
import com.dream.utils.DictMgr;
import com.dream.utils.KafkaProducer;
import com.dream.utils.UuidUtils;
//import com.easemob.server.example.httpclient.apidemo.EasemobIMUsers;
 
@Controller
@RequestMapping("/user")
public class UserController extends AbsController {
	
	private static Log log = LogFactory.getLog(UserController.class);
 
	@Autowired
	private UserService userService;
    
    @RequestMapping(value="/login")
    public ModelAndView login(HttpSession session){
        ModelAndView mav=new ModelAndView();
        
    	if (null == session.getAttribute("USER")) {
    		mav.setViewName("login");
    	} else {
            mav.setViewName("dashboard");
    	}
        
        return mav;
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> logout(HttpSession session){
        if (session != null) {
            session.invalidate();
        }
        
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public @ResponseBody User authenticate(@RequestBody User user, HttpSession session){
        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
        	User userFind = userService.findUserByLoginname(user.getUsername());
        	if (null != userFind && userFind.getPassword().equalsIgnoreCase(user.getPassword())) {
        		session.setAttribute("USER", userFind);
        		return userFind;
        	}
        } 
        
        return new User();
    }
    
    
    
    
    
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("org/user");
        
        return mav;
    }
    
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("UserController getRtnDataList");
		
    	Page page = listPage.getPage();
		List<User> rtnList = userService.findUsers(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		Map<String, String> sexMap = DictMgr.getEntrysMap(Constant.DICT_SEX);
		
		for (User user: rtnList) {
			String caozuo = "<a href='/user/edit/"+user.getId()+"'>编辑</a>&nbsp;";
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('"+user.getId()+"')>删除</a>";
			
			Map<String, Object> map = pojoToMap(user);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			map.put("deptname", DeptMgr.getDept(user.getDeptcode()).getName());
			map.put("sex", sexMap.get(String.valueOf(user.getSex())));
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("org/user_item");
        
        if (id.equals("_ADD_")) {
        	User user = new User();
        	mav.addObject("itemObj", user);
        } else {
        	User user = userService.findUser(id);
        	
        	if (user.getDeptcode().length() > 0) {
        		Dept dept = DeptMgr.getDept(user.getDeptcode());
        		
        		user.setDeptname(dept.getName());
        	}
        	
            mav.addObject("itemObj", user);
        }
    	
        return mav;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody User save(@RequestBody User user) {
    	String code = user.getId();
    	boolean addFlag = false;
    	
    	if (code.length() == 0) {
    		addFlag = true;
    		code = UuidUtils.base58Uuid();
    		user.setId(code);
    	}
    	JSONObject obj = JSONObject.fromObject(user);
    	String dataStr = obj.toString();
    	
    	if (addFlag) {
    		userService.insert(user);
    		
    		dataStr = "_ADD_" + dataStr;
    		
    		//EasemobIMUsers.addHxUser(user.getLoginname(), user.getPassword());
    	} else {
    		userService.update(user);
    		dataStr = "_UPDATE_" + dataStr;
    	}
    	
    	KafkaProducer.sendData(dataStr);
    	
    	//更新用户的字典
        UserMgr.putCache(code, user);
        UserMgr.clearCacheList();
        
        return user;
    }	
    
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> delete(@PathVariable String id, HttpSession session){
    	userService.delete(id); //删除用户
    	
    	UserMgr.clearCache(id); //删除缓存
    	UserMgr.clearCacheList();
    	
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("result", "success");
    	
    	
        return rtnMap;
    }    
    
    @RequestMapping(value="/getUserListForTree", method = RequestMethod.GET)
    public @ResponseBody List<TreeBean> getUserListForTree(HttpSession session){
    	
    	return UserMgr.getUserList();
    }
    
    @RequestMapping(value="/getUser/{userid}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String userid, HttpSession session){
    	User user = UserMgr.getUser(userid);
    	if (user.getDeptcode().length() > 0) {
    		Dept dept = DeptMgr.getDept(user.getDeptcode());
    		
    		user.setDeptname(dept.getName());
    	}
    	
    	return user;
    }
    
    
    
}