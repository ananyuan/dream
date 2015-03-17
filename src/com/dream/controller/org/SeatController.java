package com.dream.controller.org;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.model.org.Dept;
import com.dream.model.org.Seat;
import com.dream.model.org.User;
import com.dream.service.org.SeatService;


@Controller
@RequestMapping("/seat")
public class SeatController {

	
	@Autowired
	private SeatService seatService;
	
    @RequestMapping(value="/getSeat/{seatid}", method = RequestMethod.GET)
    public @ResponseBody Seat getSeat(@PathVariable String seatid, HttpSession session){
    	
    	Seat seat = seatService.findSeat(seatid);
    	
    	if (null != seat && seat.getUserid().length() > 0) {
    		User user = UserMgr.getUser(seat.getUserid());
        	
    		if (null != user && user.getDeptcode().length() > 0) {
        		Dept dept = DeptMgr.getDept(user.getDeptcode());
        		
        		user.setDeptname(dept.getName());
        	}
        	seat.setUser(user);
        	
    	} else if (null == seat) {
    		seat = new Seat();
    	}
    	
    	return seat;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Seat save(@RequestBody Seat seat) {
    	String seatid = seat.getSeatid();
    	
    	Seat oldSeat = seatService.findSeat(seatid);
    	if (null == oldSeat) {
    		seatService.insert(seat);
    	} else {
    		seatService.update(seat);
    	}
        
        return seat;
    }
    
    @RequestMapping(value="/findUserSeat/{userid}", method = RequestMethod.GET)
	public @ResponseBody List<Seat> findUserSeat(@PathVariable String userid) {
    	List<Seat> seats = seatService.findSeatByUser(userid);
        
        return seats;
    }    
    
    
    @RequestMapping(value="seatMgr")
    public ModelAndView seatMgr(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("org/seat_edit");
        
        return mav;
    }
    
    @RequestMapping(value="seatView")
    public ModelAndView seatView(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("org/seat_view");
        
        return mav;
    }    
    
}
