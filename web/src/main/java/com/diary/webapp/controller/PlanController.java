package com.diary.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diary.model.Plan;
import com.diary.service.DRManager;
import com.diary.service.PlanManager;

/**
 * This controller is used to display list of plans.
 * @author Zdenda
 *
 */
@Controller
@RequestMapping("/plans")
public class PlanController {
	
	/**
	 * Manager for Plan.
	 */
	private PlanManager planManager;
	
	private UserManager userManager;
	
	@Autowired
	public void setPlanManager(@Qualifier("planManager") PlanManager planManager) {
		this.planManager = planManager;
	}
	
	@Autowired
	public void setUserManager(@Qualifier("userManager") UserManager userManager) {
		this.userManager = userManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request) {
		
		//gets the users ID
		String username = request.getRemoteUser();
		long id = userManager.getUserByUsername(username).getId();
		
		ModelAndView m = new ModelAndView();
		m.addObject("planList", planManager.getAllUnfinishedPlans(id));
		m.addObject("plan",new Plan()); //for the form
		
		return m;
	}
}
