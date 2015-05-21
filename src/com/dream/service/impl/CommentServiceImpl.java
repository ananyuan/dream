package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.CommentDao;
import com.dream.model.Comment;
import com.dream.service.CommentService;

public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;
	
	@Override
	public int insert(Comment comment) {
		
		return this.commentDao.insert(comment);
	}

	@Override
	public int update(Comment comment) {
		return this.update(comment);
	}

	@Override
	public int delete(int id) {
		return this.commentDao.delete(id);
	}

	@Override
	public List<Comment> findCommnets(Page<?> page) {
		return this.commentDao.findCommnets(page);
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public Comment findComment(int id) {
		return this.commentDao.findComment(id);
	}

}
