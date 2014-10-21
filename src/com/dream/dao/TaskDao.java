package com.dream.dao;

import java.util.List;

import com.dream.model.Task;

public interface TaskDao {
	public int insert(Task task);

	public int update(Task task);

	public int delete(String id);

	public List<Task> selectAll();
	
	public List<Task> findTasksFinish();
	
	public List<Task> findTasksTodo();

	public int countAll();

	public Task findTask(int id);
}
