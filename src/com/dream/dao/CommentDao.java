package com.dream.dao;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Comment;

public interface CommentDao {
	public int insert(Comment comment);

	public int update(Comment comment);

	public int delete(int id);
	
	public Comment findComment(int id);
	
	public List<Comment> findCommnets(Page<?> page);
}
