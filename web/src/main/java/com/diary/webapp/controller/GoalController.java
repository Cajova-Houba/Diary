package com.diary.webapp.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diary.model.Activity;
import com.diary.model.DailyRecord;
import com.diary.model.Plan;
import com.diary.model.Goal;
import com.diary.model.Plan;
import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;
import com.diary.service.ActivityManager;
import com.diary.service.DRManager;
import com.diary.service.GoalManager;
import com.diary.service.PlanManager;

@Controller
@RequestMapping("/goal")
public class GoalController extends BaseFormController{
	
	private GoalManager goalManager;
	
	private UserManager userManager;
	
	private PlanManager planManager;
	
	private DRManager drManager;
	
	private ActivityManager aManager;
	
	@Autowired
	public void setGoalManager(@Qualifier("goalManager") GoalManager goalManager) {
		this.goalManager = goalManager;
	}
	
	@Autowired
	public void setUserManager(@Qualifier("userManager") UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setPlanManager(@Qualifier("planManager") PlanManager planManager) {
		this.planManager = planManager;
	}
	
	@Autowired
	public void setDrManager(@Qualifier("drm")DRManager drManager) {
		this.drManager = drManager;
	}
	
	@Autowired
	public void setaManager(@Qualifier("aManager")ActivityManager aManager) {
		this.aManager = aManager;
	}
	
	public GoalController() {
		setSuccessView("redirect:/plans");
		setCancelView("redirect:/plans");
	}
	
	/**
	 * Handling GET request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String getHandle() {
		return getSuccessView();
	}
	
	/**
	 * Deletes a goal if goal ID is provided.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public ModelAndView deleteGoal(HttpServletRequest request) {
		ModelAndView m = new ModelAndView();
		
		Long memberID = 0L;
		//load logged user ID
		try {
			String username = request.getRemoteUser();
			memberID = userManager.getUserByUsername(username).getId();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while loading the logged user ID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		log.info("Logged user ID loaded.");
		
		//load goal ID
		Long goalID = 0L;
		try {
			goalID = Long.parseLong(request.getParameter("goalId"));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while loading goal ID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		
		//load planID from goal
		Goal g = goalManager.get(goalID);
		Plan p = g.getPlan();
		if (p == null) {
			log.error("Error: no plan for goal with ID ."+goalID);
			return new ModelAndView(getCancelView()); 
		}
		
		//check if user can access this plan
		if(planManager.canAccess(memberID, p.getID())) {
			//can access -> can delete goal
			goalManager.remove(goalID);
			m.setViewName("redirect:/plandetail"); //redirect back to plan detail
			m.addObject("pID", p.getID());
		}
		else {
			//can't access
			log.error("Access denied for memberID="+memberID+" to planID="+p.getID());
			return new ModelAndView(getCancelView());
		}
		
		
		return m;
	}
	
	/**
	 * Add new goal.
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView addGoal(Goal goal, BindingResult errors, HttpServletRequest request) {
		ModelAndView m = new ModelAndView();
		
		log.info("Entering addGoal method.");
		
		//check if goal attribute exists
		if (goal == null) {
			log.error("Error: the goal is null.");
			return new ModelAndView(getCancelView());
		}
		
		//check if goalmanager exists - for testing purpose, not necessary
		if (goalManager == null) {
			log.error("Error: the goal manager is null.");
			return new ModelAndView(getCancelView());
		}
		
		//check the cancel
		if (request.getParameter("cancel") != null) {
			return new ModelAndView(getCancelView());
		}
		
		Long memberID = 0L;
		//load logged user ID
		try {
			String username = request.getRemoteUser();
			memberID = userManager.getUserByUsername(username).getId();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while loading the logged user ID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		log.info("Logged user ID loaded.");
		
		//load plan ID
		Long planID = 0L;
		try {
			planID = Long.parseLong(request.getParameter("planID"));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while parsing planID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		log.info("Plan ID loaded.");
		
		//check if user memberID is able to edit plan planID
		if (planManager.canAccess(memberID, planID)) {
			log.info("User can edit plan.");
			goal.setPlan(planManager.get(planID));
			
			//is goal new?
			if(goal.getID() == null) {
				//it is
				//it needs to get activities assigned
				goal = assignActivities(goal, memberID);
				log.debug("Activities assigned.");
				
				goal = goalManager.save(goal);
				log.debug("Goal added.");
				
			}
			else {
				//it isn't, so the wrong method has been called
				//return cancel view
				log.error("Trying to add existing goal. ID = "+goal.getID());
				return new ModelAndView(getCancelView());
				
			}
			
			//plan detail of plan with ID planID will be displayed
			m.setViewName("redirect:/plan/detail");
			m.addObject("pID",planID);
		}
		else {
			log.error("Access deniend for user with ID "+memberID+" to edit plan with ID "+planID);
			m.setViewName(getCancelView());
		}
		
		
		return m;
	}

	/**
	 * Saves the edited goal.
	 * @param goal
	 * @param errors
	 * @param request
	 * @return
	 */
	public ModelAndView editGoal(Goal goal, BindingResult errors, HttpServletRequest request) {
		ModelAndView m = new ModelAndView();
		
		log.info("Entering editGoal method.");
		
		//check if goal attribute exists
		if (goal == null) {
			log.error("Error: the goal is null.");
			return new ModelAndView(getCancelView());
		}
		
		//check if goalmanager exists - for testing purpose, not necessary
		if (goalManager == null) {
			log.error("Error: the goal manager is null.");
			return new ModelAndView(getCancelView());
		}
		
		//check the cancel
		if (request.getParameter("cancel") != null) {
			return new ModelAndView(getCancelView());
		}
		
		Long memberID = 0L;
		//load logged user ID
		try {
			String username = request.getRemoteUser();
			memberID = userManager.getUserByUsername(username).getId();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while loading the logged user ID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		log.info("Logged user ID loaded.");
		
		//load plan ID - for validation
		Long planID = 0L;
		try {
			planID = Long.parseLong(request.getParameter("planID"));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while parsing planID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		log.info("Plan ID loaded.");
		
		
		//check if user memberID is able to edit plan planID
		if (planManager.canAccess(memberID, planID)) {
			log.info("User can edit plan.");
			goal.setPlan(planManager.get(planID));

		//has the activity type changed?
		if(goalManager.get(goal.getID()).getActType() == goal.getActType()) {
			//type is the same, edited goal can be saved
			goal = goalManager.save(goal);
		}
		else {
			//activity type isn't the same, assigned activities need to be removed
			//and new activities will be assigned
			log.debug("Saving edited ");
			goalManager.deleteActivitiesForGoal(goal);
			
			//assign activities
			goal = assignActivities(goal, memberID);
			
			goal = goalManager.save(goal);
		}
			
			//plan detail of plan with ID planID will be displayed
			m.setViewName("redirect:/plandetail");
			m.addObject("pID",planID);
		}
		else {
			log.error("Access deniend for user with ID "+memberID+" to edit plan with ID "+planID);
			m.setViewName(getCancelView());
		}
		
		
		return m;
	}
	
	/**
	 * Displays the detail of the goal if parameter "gID" containing gooal's ID is provided.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView displayGoal(HttpServletRequest request) {
		ModelAndView m = new ModelAndView("goalForm");
		
		//check if parameter gID exists
		Long gID = 0L;
		try {
			gID = Long.parseLong(request.getParameter("gID"));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while parsing goal ID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		
		//load user ID
		String username = request.getRemoteUser();
		Long memberID = userManager.getUserByUsername(username).getId();
		
		//load goal
		Goal g = goalManager.get(gID);
		if (g == null) {
			log.error("Error: no activity with ID="+gID);
			return new ModelAndView(getCancelView()); 
		}
		//load plan this goal belongs to
		Plan p = g.getPlan();
		if (p == null) {
			log.error("Error: no plan for goal with ID="+gID+" found");
			return new ModelAndView(getCancelView());
		}
		//check if user can access this goal
		if (planManager.canAccess(memberID, p.getID())) {
			m.addObject("goal", g);
		}
		else {
			log.error("Error: member with ID="+memberID+" can't access plan with ID="+p.getID());
			return new ModelAndView(getCancelView());
		}
		
		return m;
	}
	
	/**
	 * This method will assign activities to a certain goal and then returns the goal.
	 * @param goal
	 * @param memberID Logged member's ID.
	 * @return
	 */
	private Goal assignActivities(Goal goal, Long memberID) {
		//go through all daily records from FromDate of a plan this goal belongs to
		log.debug("Entering assignActivities method..");
		log.debug("Goal: "+goal.toString());
		
		Plan p = goal.getPlan();
		log.debug("Plan ID: "+p.getID());
		
		List<DailyRecord> drs = drManager.getDailyRecordsFrom(p.getFromDate(), memberID);
		log.debug(drs.size()+" daily records from date "+p.getFromDate()+" found.");
		
		for (DailyRecord dailyRecord : drs) {
			//check the type of every activity and assign it if the type is same
			Set<Activity> as = drManager.getActivitiesFor(dailyRecord.getID());
			log.debug(as.size()+" activities for daily record with ID="+dailyRecord.getID()+" found.");
			for(Activity a : as) {
				log.debug("Activity type of activity with ID="+a.getID()+" is "+a.getActType().toString());
				if (a.getActType() == goal.getActType()) {
					log.debug("Assigning activity with ID="+a.getID()+" to the goal with ID="+goal.getID());
					goal.getActivities().add(a);
				}
			}
		}
		
		return goal;
	}
}
