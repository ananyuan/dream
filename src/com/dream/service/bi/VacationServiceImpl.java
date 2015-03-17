package com.dream.service.bi;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.bi.VacationDao;
import com.dream.model.bi.Vacation;

public class VacationServiceImpl implements VacationService {

	private VacationDao vacationDao;
	
	public VacationDao getVacationDao() {
		return vacationDao;
	}

	public void setVacationDao(VacationDao vacationDao) {
		this.vacationDao = vacationDao;
	}

	@Override
	public int insert(Vacation vacation) {
		return this.vacationDao.insert(vacation);
	}

	@Override
	public int update(Vacation vacation) {
		return this.vacationDao.update(vacation);
	}

	@Override
	public int delete(int id) {
		return this.vacationDao.delete(id);
	}

	@Override
	public List<Vacation> findVacations(Page<?> page) {
		return this.vacationDao.findVacations(page);
	}

	@Override
	public Vacation findVacation(int id) {
		return this.vacationDao.findVacation(id);
	}

}
