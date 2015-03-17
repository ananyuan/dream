package com.dream.service;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Todo;

public interface TodoService {
	
	public int insert(Todo todo);

	public int update(Todo todo);

	public int delete(String id);
	
	public List<Todo> findTodosFinish(Page<?> page);
	
	public List<Todo> findTodos(Page<?> page);

	public int countAll();

	public Todo findTodo(int id);
}
