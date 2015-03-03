package com.dream.service;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Menu;

public interface MenuService {
	public int insert(Menu menu);

	public int update(Menu menu);

	public int delete(int id);

	public Menu findMenu(int id);
	
	public List<Menu> findMenus(Page page);
}
