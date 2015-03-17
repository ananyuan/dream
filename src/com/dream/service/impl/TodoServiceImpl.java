package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.TodoDao;
import com.dream.model.Todo;
import com.dream.service.TodoService;

public class TodoServiceImpl implements TodoService {

	private TodoDao todoDao;
	
	@Override
	public int insert(Todo todo) {
		return this.todoDao.insert(todo);
	}

	@Override
	public int update(Todo todo) {
		return this.todoDao.update(todo);
	}

	@Override
	public int delete(String id) {
		return this.todoDao.delete(id);
	}

	@Override
	public List<Todo> findTodosFinish(Page<?> page) {
		return this.todoDao.findTodosFinish(page);
	}

	@Override
	public List<Todo> findTodos(Page<?> page) {
		return this.todoDao.findTodos(page);
	}

	@Override
	public int countAll() {
		return this.todoDao.countAll();
	}

	@Override
	public Todo findTodo(int id) {
		return this.todoDao.findTodo(id);
	}

	public TodoDao getTodoDao() {
		return todoDao;
	}

	public void setTodoDao(TodoDao todoDao) {
		this.todoDao = todoDao;
	}

}
