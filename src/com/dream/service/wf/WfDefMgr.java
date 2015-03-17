package com.dream.service.wf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.base.Constant;
import com.dream.model.WfBaseBean;
import com.dream.model.wf.WfDef;
import com.dream.start.CacheMgr;


/**
 * 
 * @author anan
 *
 */
public class WfDefMgr {
	
	private static Log log = LogFactory.getLog(WfDefMgr.class);

	
	
	
	/**
	 * 
	 * @param modelname
	 * @return
	 */
	public static WfDef getWfDef(String modelname) {
		
		WfDef wfDef = getObjFromCache(modelname);
        if (null == wfDef) { 
        	wfDef = new WfDef(modelname);
        	
            if (null != wfDef) {
                addToCache(modelname, wfDef); 
            } 
        }
		
		return wfDef;
	}
	
	
    private static WfDef getObjFromCache(String modelname) {
        return (WfDef) CacheMgr.getInstance().get(modelname, Constant.CACHE_TYPE_WF);
    }

    private static void addToCache(String modelname, WfDef wfDef) {
        CacheMgr.getInstance().set(modelname, wfDef, Constant.CACHE_TYPE_WF);
    }

    
    public static void removeFromCache(String modelname) {
        try {
            CacheMgr.getInstance().remove(modelname, Constant.CACHE_TYPE_WF);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
	
}
