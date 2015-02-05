package com.dream.service;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Task;

public interface TaskService {
	public int insert(Task task);

	public int update(Task task);

	public int delete(String id);

	public List<Task> selectAll();

	public int countAll();

	public Task findTask(int id);
	
	public List<Task> findTasks(Page page);
	
	public List<Task> findTasksFinish(Page page);
	
	public List<Task> findTasksTodo(Page page);
}
