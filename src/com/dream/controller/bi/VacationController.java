package com.dream.controller.bi;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.controller.AbsController;
import com.dream.model.bi.Vacation;
import com.dream.service.bi.VacationService;
import com.dream.service.wf.WfAct;
import com.dream.service.wf.WfMgr;

@Controller
@RequestMapping("/vacation")
public class VacationController extends AbsController {
	
	
	@Autowired
	private VacationService vacationService;
	
    @RequestMapping(value="/edit/{id}/{niid}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, @PathVariable int niid, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("bi/vacation_item");
        
        mav.addObject("id", id);
        mav.addObject("niid", niid);
        
        return mav;
    }
    
    @RequestMapping(value="/editJson/{id}/{niid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> editJson(@PathVariable String id, @PathVariable int niid, HttpSession session){
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	if (id.equals("_ADD_")) {
    		Vacation vacation = new Vacation();
    		
    		rtnMap.put("itemObj", vacation);
        } else {
        	Vacation vacation = vacationService.findVacation(Integer.parseInt(id));
        	
        	rtnMap.put("itemObj", vacation);
            
            
        	rtnMap.put("nextSteps", getNextSteps(vacation, vacation.getClass().getName(), niid));
        }
    	
    	return rtnMap;
    } 
	
	
	
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Vacation save(@RequestBody Vacation vacation) {
    	
    	if (vacation.getId() == 0) {
    		vacationService.insert(vacation);
    		
    		vacation.setVmodel("vacation");
    		WfAct wfAct = WfMgr.startProcess(vacation, vacation.getTitle());
    		
    		vacation.setWfid(wfAct.getProcess().getWfInstId());
    		vacationService.update(vacation);
    		
    		vacation.setNiid(wfAct.getId());
    	} else {
    		vacationService.update(vacation);
    	}
        
        return vacation;
    }	
	
}
