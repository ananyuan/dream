package com.dream.dao.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.wf.Line;

public interface LineDao {
	public int insert(Line line);

	public int update(Line line);

	public int delete(String id);
	
	public List<Line> findLines(Page<?> page);

	public Line findLine(String id);
}
