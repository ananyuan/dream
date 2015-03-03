package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.MenuDao;
import com.dream.model.Menu;
import com.dream.service.MenuService;

public class MenuServiceImpl implements MenuService {

	private MenuDao menuDao;
	
	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public int insert(Menu menu) {
		
		return this.menuDao.insert(menu);
	}

	@Override
	public int update(Menu menu) {
		return this.menuDao.update(menu);
	}

	@Override
	public int delete(int id) {
		return this.menuDao.delete(id);
	}

	@Override
	public Menu findMenu(int id) {
		return this.menuDao.findMenu(id);
	}

	@Override
	public List<Menu> findMenus(Page page) {
		return this.menuDao.findMenus(page);
	}


}
