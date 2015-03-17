package com.dream.service.org;

import java.util.List;

import com.dream.dao.org.SeatDao;
import com.dream.model.org.Seat;

public class SeatServiceImpl implements SeatService {

	private SeatDao seatDao;
	
	
	@Override
	public int insert(Seat seat) {
		return this.seatDao.insert(seat);
	}

	@Override
	public int update(Seat seat) {
		return this.seatDao.update(seat);
	}

	@Override
	public int delete(String seatid) {
		return this.seatDao.delete(seatid);
	}

	@Override
	public int countAll() {
		return this.seatDao.countAll();
	}

	@Override
	public Seat findSeat(String seatid) {
		return this.seatDao.findSeat(seatid);
	}

	public SeatDao getSeatDao() {
		return seatDao;
	}

	public void setSeatDao(SeatDao seatDao) {
		this.seatDao = seatDao;
	}

	@Override
	public List<Seat> findSeatByUser(String userid) {
		return this.seatDao.findSeatByUser(userid);
	}

}
