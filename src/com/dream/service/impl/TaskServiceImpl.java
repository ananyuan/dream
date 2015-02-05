package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.TaskDao;
import com.dream.model.Task;
import com.dream.service.TaskService;

public class TaskServiceImpl implements TaskService {

	private TaskDao taskDao;
	
	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public int insert(Task task) {

		return this.taskDao.insert(task);
	}

	@Override
	public int update(Task task) {

		return this.taskDao.update(task);
	}

	@Override
	public int delete(String id) {
		return this.taskDao.delete(id);
	}

	@Override
	public List<Task> selectAll() {
		return this.taskDao.selectAll();
	}

	@Override
	public int countAll() {
		return this.taskDao.countAll();
	}

	@Override
	public Task findTask(int id) {
		return this.taskDao.findTask(id);
	}

	@Override
	public List<Task> findTasksFinish(Page page) {
		return this.taskDao.findTasksFinish(page);
	}

	@Override
	public List<Task> findTasksTodo(Page page) {

		return this.taskDao.findTasksTodo(page);
	}

	@Override
	public List<Task> findTasks(Page page) {
		return this.taskDao.findTasks(page);
	}

}
