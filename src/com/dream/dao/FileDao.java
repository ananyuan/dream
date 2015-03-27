package com.dream.dao;

import java.util.List;

import com.dream.model.FileBean;

public interface FileDao {
	
	public int insert(FileBean fileBean);

	public int update(FileBean fileBean);

	public int delete(String id);

	public int countAll();

	public FileBean findFile(String id);

	public List<FileBean> findFiles(String dataid);
	
	public List<FileBean> findFiles2(String dataid, String model);
}
