package com.dream.service.org;

import java.util.List;

import com.dream.model.org.Seat;

public interface SeatService {

	public int insert(Seat seat);

	public int update(Seat seat);

	public int delete(String seatid);

	public int countAll();

	public Seat findSeat(String seatid);
	
	public List<Seat> findSeatByUser(String userid);
	
}
