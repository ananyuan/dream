package com.dream.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dream.base.Page;
import com.dream.model.Todo;

public interface TodoDao {
	public int insert(Todo todo);

	public int update(Todo todo);

	public int delete(String id);
	
	public List<Todo> findTodosFinish(Page<?> page);
	
	public List<Todo> findTodos(Page<?> page);

	public int countAll();

	public Todo findTodo(int id);

	//TODO
	public Todo findTodo(int niid, String userid);
}
