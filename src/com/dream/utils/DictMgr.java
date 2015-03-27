package com.dream.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.model.Dict;
import com.dream.model.DictEntry;
import com.dream.service.DictEntryService;
import com.dream.service.DictService;
import com.dream.start.CacheMgr;

/**
 * 字典
 * @author anan
 *
 */
public class DictMgr {

	private static DictService dictService = SpringContextUtil.getBean("dictService");
	
	private static DictEntryService entryService = SpringContextUtil.getBean("dictEntryService");
	
	/**
	 * 
	 * @param dictId 字典编码
	 * @return 字典
	 */
	public static Dict getDict(String dictId) {
		
		Dict dict = (Dict) CacheMgr.getInstance().get(dictId, Constant.CACHE_TYPE_DICT);
		if (null != dict) {
			return dict;
		}
		
	    dict = dictService.findDict(dictId);
	    
	    Page page = new Page();
	    page.setStr("dictid", dictId);
	    page.setPageSize(1000);
	    page.setOrder("dlevel, esort");  //先按层级， 再按序号排序
	    List<DictEntry> entrys = entryService.findEntrys(page);
	    //dict.setChilds(recurEntry(entrys));
	    dict.setChilds(entrys);
		
	    CacheMgr.getInstance().set(dictId, dict, Constant.CACHE_TYPE_DICT);
		
		return dict;
	}
	
	/**
	 * 
	 * @param entrys  
	 * @return
	 */
	private static List<DictEntry> recurEntry(List<DictEntry> entrys) {
		HashMap<String, DictEntry> entryMap = new HashMap<String, DictEntry>();
		DictEntry rootEntry = new DictEntry();
		rootEntry.setChilds(new ArrayList<DictEntry>());
		entryMap.put("ROOT", rootEntry);
		
		String pCode = "";
		for (DictEntry entry: entrys) {
			entry.setChilds(new ArrayList<DictEntry>());
			entryMap.put(entry.getCode(), entry);
			
			if (!entry.getPcode().equalsIgnoreCase(pCode)) {
				pCode = entry.getPcode();
			}
			
			if (pCode.length() == 0) {
				List<DictEntry> entryListTemp = rootEntry.getChilds();
				
				entryListTemp.add(entry);
			} else {
				DictEntry entryTemp = entryMap.get(pCode);
				List<DictEntry> entryListTemp = entryTemp.getChilds();
				
				entryListTemp.add(entry);
			}
		}
		
		return rootEntry.getChilds();
	}
	
	/**
	 * 
	 * @param dictId 字典ID
	 * @return 根据字典ID获取entrys
	 */
	public static List<DictEntry> getEntrys(String dictId) {
		Dict dict = getDict(dictId);
		if (null != dict) {
			return dict.getChilds();
		} 
		
		return null;
	}
	
	/**
	 * @param dictId 字典ID
	 * @return 字典Map
	 */
	public static Map<String, String> getEntrysMap(String dictId) {
		Map<String, String> entryMap = new HashMap<String, String>();
		List<DictEntry> entrys = getEntrys(dictId); 
		for (DictEntry entry: entrys) {
			entryMap.put(entry.getCode(), entry.getName());
		}
		
		return entryMap;
	}
	
	/**
	 * 
	 * @param dictId 字典ID
	 * @param entryId 条目ID
	 */
	public static DictEntry getEntry(String dictId, String entryId) {
		Dict dict = getDict(dictId);
		
		
		return getEntry(dict.getChilds(), entryId);
	}
	
	/**
	 * 
	 * @param entrys 
	 * @param entryId
	 * @return
	 */
	private static DictEntry getEntry(List<DictEntry> entrys, String entryId) {
		for (DictEntry entry: entrys) {
			if (entry.getCode().equalsIgnoreCase(entryId)) {
				return entry;
			}
//			if (entry.getChilds().size() > 0) {
//				DictEntry subEntry = getEntry(entry.getChilds(), entryId);
//				if (null == subEntry) {
//					continue;
//				} else {
//					return subEntry;
//				}
//			}
		}
		
		return null;
	}
	
	
	/**
	 * 清除缓存
	 * @param dictId 字典ID
	 */
	public static void clearCache(String dictId) {
		Dict dict = (Dict) CacheMgr.getInstance().get(dictId, Constant.CACHE_TYPE_DICT);
		if (dict == null) {
			return;
		}
		CacheMgr.getInstance().remove(dictId, Constant.CACHE_TYPE_DICT);
	}
}
