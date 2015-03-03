package com.dream.dao;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Menu;

/**
 * 
 * @author anan
 *
 */
public interface MenuDao {
	public int insert(Menu menu);

	public int update(Menu menu);

	public int delete(int id);
	
	public List<Menu> findMenus(Page<?> page);

	public Menu findMenu(int id);
}
