package com.dream.dao.serial;

import java.util.List;

import com.dream.base.Page;
import com.dream.controller.serial.model.InsBtn;

public interface InsBtnDao {
	public int insert(InsBtn insBtn);

	public int update(InsBtn insBtn);

	public int delete(String id);

	public InsBtn findBtn(String id);
	

	public List<InsBtn> findBtns(String insid);
	
	public List<InsBtn> findBtnsByPage(Page<?> page);
}
