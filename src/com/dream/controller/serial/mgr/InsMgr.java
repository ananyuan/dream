package com.dream.controller.serial.mgr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.base.Constant;
import com.dream.start.CacheMgr;

public class InsMgr {

	private static Log log = LogFactory.getLog(InsMgr.class);
	
	public static InsDefMgr getInsDef(String inscode) {
		InsDefMgr insDef = getObjFromCache(inscode);
        if (null == insDef) { 
        	insDef = new InsDefMgr(inscode);
        	
            if (null != insDef) {
                addToCache(inscode, insDef); 
            } 
        }
		
		return insDef;
	}

	private static void addToCache(String inscode, InsDefMgr insDef) {
		CacheMgr.getInstance().set(inscode, insDef, Constant.CACHE_TYPE_INS_DEF);
		
	}

	private static InsDefMgr getObjFromCache(String inscode) {
		return (InsDefMgr) CacheMgr.getInstance().get(inscode, Constant.CACHE_TYPE_INS_DEF);
	}
	
	
    
    public static void removeFromCache(String inscode) {
        try {
            CacheMgr.getInstance().remove(inscode, Constant.CACHE_TYPE_INS_DEF);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
