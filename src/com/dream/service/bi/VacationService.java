package com.dream.service.bi;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.bi.Vacation;

public interface VacationService {

	public int insert(Vacation vacation);

	public int update(Vacation vacation);

	public int delete(int id);
	
	public List<Vacation> findVacations(Page<?> page);

	public Vacation findVacation(int id);
	
}
