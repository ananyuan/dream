package com.dream.base.acl;

import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dream.model.org.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {
 
    final Logger log = LoggerFactory.getLogger(getClass());

    private static HashSet<String> noNeedLoginUrlMap = new HashSet<String>();
    
    static {
    	noNeedLoginUrlMap.add("/article/list");
    	noNeedLoginUrlMap.add("/article/search");
    	noNeedLoginUrlMap.add("/sick/search");
    	noNeedLoginUrlMap.add("/slog/search");
    	noNeedLoginUrlMap.add("/srecord/search");
    	noNeedLoginUrlMap.add("/task/search");
    	noNeedLoginUrlMap.add("/user/login");
    	noNeedLoginUrlMap.add("/user/authenticate");
    	noNeedLoginUrlMap.add("/insDef/page/xxx");
    	noNeedLoginUrlMap.add("/insDef/page/xxx/channel1");
    	noNeedLoginUrlMap.add("/insDef/page/xxx/channel2");
    	noNeedLoginUrlMap.add("/insDef/page/xxx/channel3");
    	noNeedLoginUrlMap.add("/insDef/page/xxx/channel4");
    	noNeedLoginUrlMap.add("/insDef/page/QnhyFaNao7vbu9kzP2jjuJ");
    	noNeedLoginUrlMap.add("/insDef/page/QnhyFaNao7vbu9kzP2jjuJ/channel1");
    }
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	log.debug("-------------LoginInterceptor preHandle ------------------------------");
    	
        if (handler instanceof HandlerMethod) {
    		HandlerMethod handler2 = (HandlerMethod) handler;
    		NoNeedLogin login = handler2.getMethodAnnotation(NoNeedLogin.class);

    		if (null != login) {
    			return true;
    		}
        } else {
        	return true;
        }
    	
    	String reqUrl=request.getRequestURI().replace(request.getContextPath(), "");  

        if(noNeedLoginUrlMap.contains(reqUrl) || reqUrl.startsWith("/js") || reqUrl.startsWith("/css")){  
            return true;  
        }else{  
            HttpSession session=request.getSession(); 
            
            User user = (User)session.getAttribute("USER");
            
            if(user==null || "".equals(user.toString())){
				request.setAttribute("message", "请登录");

				request.setAttribute("toLogin", "yes");

				WebApplicationContext webApplicationContext = ContextLoader
						.getCurrentWebApplicationContext();
				ServletContext servletContext = webApplicationContext
						.getServletContext();

				RequestDispatcher rd = null;
				rd = servletContext.getRequestDispatcher("/error.jsp"); // 定向的页面
				rd.forward(request, response);
            }  
        } 
        
        return true;  
    }
    
}
