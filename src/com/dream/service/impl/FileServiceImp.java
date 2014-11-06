package com.dream.service.impl;

import java.util.List;

import com.dream.dao.FileDao;
import com.dream.model.FileBean;
import com.dream.service.FileService;

public class FileServiceImp implements FileService {

	private FileDao fileDao;
	
	
	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@Override
	public int insert(FileBean fileItem) {
		return fileDao.insert(fileItem);
	}

	@Override
	public int update(FileBean fileItem) {
		
		return fileDao.update(fileItem);
	}

	@Override
	public int delete(String id) {
		
		return fileDao.delete(id);
	}

	@Override
	public int countAll() {
		
		return fileDao.countAll();
	}

	@Override
	public FileBean findFile(String id) {
		
		return fileDao.findFile(id);
	}

	@Override
	public List<FileBean> findFiles(String dataid) {
		
		return fileDao.findFiles(dataid);
	}

}
