package com.dream.dao.org;

import java.util.List;

import com.dream.model.org.Seat;

public interface SeatDao {
	public int insert(Seat seat);

	public int update(Seat seat);

	public int delete(String seatid);

	public int countAll();

	public Seat findSeat(String seatid);
	
	public List<Seat> findSeatByUser(String userid);
	
}
