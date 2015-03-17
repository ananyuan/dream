package com.dream.service.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.wf.LineDao;
import com.dream.model.wf.Line;

public class LineServiceImpl implements LineService {

	private LineDao lineDao;
	
	@Override
	public int insert(Line line) {
		return this.lineDao.insert(line);
	}

	@Override
	public int update(Line line) {
		return this.lineDao.update(line);
	}

	@Override
	public int delete(String id) {
		return this.lineDao.delete(id);
	}

	@Override
	public List<Line> findLines(Page<?> page) {
		return this.lineDao.findLines(page);
	}

	@Override
	public Line findLine(String id) {
		return this.lineDao.findLine(id);
	}

	public LineDao getLineDao() {
		return lineDao;
	}

	public void setLineDao(LineDao lineDao) {
		this.lineDao = lineDao;
	}

}
