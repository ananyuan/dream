package com.dream.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dream.base.Page;
import com.dream.model.Task;

public interface TaskDao {
	public int insert(Task task);

	public int update(Task task);

	public int delete(String id);

	public List<Task> selectAll();
	
	public List<Task> findTasksFinish(Page<?> page);
	
	public List<Task> findTasksTodo(@Param("page") Page<?> page);

	public int countAll();

	public Task findTask(int id);
}
