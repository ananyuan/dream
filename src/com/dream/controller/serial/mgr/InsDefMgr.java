package com.dream.controller.serial.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dream.controller.serial.model.InsBtn;
import com.dream.controller.serial.model.InsDef;
import com.dream.controller.serial.model.InsField;
import com.dream.service.serial.InsBtnService;
import com.dream.service.serial.InsDefService;
import com.dream.service.serial.InsFieldService;
import com.dream.utils.SpringContextUtil;

public class InsDefMgr {
	
	private static InsDefService insDefService = SpringContextUtil.getBean("insDefService");
	
	private static InsBtnService insBtnService = SpringContextUtil.getBean("insBtnService");
	
	private static InsFieldService insFieldService = SpringContextUtil.getBean("insFieldService");
	
	
	private String inscode = "";
	
	private InsDef insDef = new InsDef();
	
	private HashMap<String, InsBtn> insBtnMap = new HashMap<String, InsBtn>();
	
	private List<InsBtn> insBtns = new ArrayList<InsBtn>();
	
	private List<InsField> insFields = new ArrayList<InsField>();
	
	public InsDefMgr(String inscode) {
		this.setInscode(inscode);
		
		initInsDef();
	}
	
	
	private void initInsDef() {
		loadInsFields();
		
		loadInsBtns();
		
		loadInsDef();
	}


	private void loadInsDef() {
		this.insDef = insDefService.findDef(inscode);
	}


	private void loadInsBtns() {
		this.insBtns = insBtnService.findBtns(inscode);
		
		for (InsBtn node: insBtns) {
			insBtnMap.put(node.getCode(), node);
		}
	}


	private void loadInsFields() {
		this.insFields = insFieldService.findFields(inscode);
	}


	public InsBtn getInsBtn(String btnCode) {
		return insBtnMap.get(btnCode);
	}
	
	public List<InsField> getFields() {
		return this.insFields;
	}


	public String getInscode() {
		return inscode;
	}


	public void setInscode(String inscode) {
		this.inscode = inscode;
	}


	public InsDef getInsDef() {
		return insDef;
	}


	public void setInsDef(InsDef insDef) {
		this.insDef = insDef;
	}


	public List<InsBtn> getInsBtns() {
		return insBtns;
	}


	public void setInsBtns(List<InsBtn> insBtns) {
		this.insBtns = insBtns;
	}
}
