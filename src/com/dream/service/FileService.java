package com.dream.service;

import java.util.List;

import com.dream.model.FileBean;

public interface FileService {
	public int insert(FileBean fileItem);

	public int update(FileBean fileItem);

	public int delete(String id);

	public int countAll();

	public FileBean findFile(String id);
	
	public List<FileBean> findFiles(String dataid);
}
