package com.diary.webapp.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diary.model.Goal;
import com.diary.model.Plan;
import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;
import com.diary.service.GoalManager;
import com.diary.service.PlanManager;

/**
 * This controller is used to display a detailed view of a certain plan, adding new plans, editing and deleting 
 * existing ones.
 * @author Zdenda
 *
 */
@Controller
@RequestMapping("/plan")
public class PlanDetailController extends BaseFormController {
	
	private PlanManager planManager;
	
	private UserManager userManager;
	
	private GoalManager goalManager;
	
	@Autowired
    public void setPersonManager(@Qualifier("planManager") PlanManager planManager) {
        this.planManager = planManager;
    }
	
	@Autowired
	public void setUserManager(@Qualifier("userManager") UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setGoalManager(@Qualifier("goalManager") GoalManager goalManager) {
		this.goalManager = goalManager;
	}
	
	public PlanDetailController() {
        setCancelView("redirect:/plans");
        setSuccessView("redirect:/plans");
    }
	
	/**
	 * Returns ModelAndView for blank formular.
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	protected ModelAndView displayAddForm() {
		ModelAndView m = new ModelAndView();
		m.setViewName("planForm");
		
		Plan dummy = new Plan();
		dummy.setName("New plan");
		dummy.setFromDate(new Date(Calendar.getInstance().getTime().getTime()));
		
		m.addObject("plan",dummy);
		
		return m;
	}
	
	/**
	 * Adds a new plan.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addPlan(Plan plan, BindingResult errors, HttpServletRequest request) {
		
		log.info("Entering addPlan method");
		
		//check if plan exists
		if (plan == null) {
			log.error("Plan is null.");
			return getCancelView();
		}
		
		//cancel button in formdetail pressed
		if (request.getParameter("cancel") != null) {
			return getCancelView();
		}
		
		boolean isNew = (plan.getID() == null);
		
		//gets users ID
		String username = request.getRemoteUser();
		long memberID = userManager.getUserByUsername(username).getId();
		
		//save new Plan
		if (isNew) {
			//i may try to implement setFromDate(String date) in Plan so it will be done automatically
			Date date = Date.valueOf(request.getParameter("fromDate"));
			plan.setFromDate(date);
			plan.setMemberID(memberID);
			planManager.save(plan);
		}
		else {
			//trying to add an existing plan - wrong method called
			log.error("Trying to add existing plan. Please use method for editing existing plan.");
			return getCancelView();
		}
		
		return getSuccessView();
	}
	
	/**
	 * Edits a plan. This method is also used for deleting a plan.
	 * @param plan
	 * @param errors
	 * @param request
	 */
	@RequestMapping(value="/edit",method = RequestMethod.POST)
	protected String editPlan(Plan plan, BindingResult errors, HttpServletRequest request) {
		
		log.info("Entering editPlan method.");
		
		//check if the plan isn't null
		if(plan == null) {
			log.error("Plan is null.");
			return getCancelView();
		}
		
		//check the cancel paramete
		if(request.getParameter("cancel") != null) {
			return getCancelView();
		}
		
		//check if plan isn't new
		if(plan.getID() == null) {
			log.error("Plan doesn't exist in database. Please use the addPlan method.");
			return getCancelView();
		}
		
		//gets users ID
		String username = request.getRemoteUser();
		long memberID = userManager.getUserByUsername(username).getId();
		
		//check that user can edit this one
		//this may be duplicate, but i'am not sure if one can send fake request
		//if it's possible then this validation is needed
		boolean canAccess = planManager.canAccess(memberID, plan.getID());
		
		if (canAccess) {
			//save edited plan
			if (request.getParameter("save") != null) {
				//i may try to implement setFromDate(String date) in Plan so it will be done automatically
				Date date = Date.valueOf(request.getParameter("fromDate"));
				plan.setFromDate(date);
				plan.setMemberID(memberID);
				planManager.save(plan);
			}
			
			//or delete it
			else if (request.getParameter("delete") != null) {
				goalManager.removeGoals(plan.getID());
				planManager.remove(plan.getID());
			}
		}
		
		return getSuccessView();
	}
	
	/**
	 * Displays the detail of plan if parameter pID with plan ID is provided.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/detail",method = RequestMethod.GET)
	protected ModelAndView showDetail(HttpServletRequest request) {
		
		//if cancel is pressed
		if(request.getParameter("cancel") != null) {
			return new ModelAndView(getCancelView());
		}
		
		ModelAndView m = new ModelAndView();
		Map<String,String> errors = new HashMap<String,String>();
		
		//gets the users ID
		String username = request.getRemoteUser();
		long id = userManager.getUserByUsername(username).getId();
		long planID = -1L;
		
		try {
			planID = Long.parseLong(request.getParameter("pID"));
		} catch (Exception e) {
			// TODO: handle exception
			errors.put("ParseError", e.getMessage());
		}
		
		//validation
		/* can member with this id see plan with provided ID? */
		boolean canAcces = planManager.canAccess(id, planID);
		if (!canAcces) {
			errors.put("AccessDenied", "Sorry, you can't view this plan.");
			m.addObject("plan", new Plan());  //it would fail otherwise;
			m.addObject("planID", new Long(0L)); //planID for hidden input in goal form
		}
		else {
			m.addObject("plan",planManager.get(planID));
			m.addObject("planID", planID);  //planID for hidden input in goal form
			
			// list of goals for certain plan
			m.addObject("goalList",goalManager.calculateProgressForGoals(planID)/*planManager.getGoalsFor(planID)*/);
			
		}
		
		//dummy goal for formular
		Goal dummy = new Goal();
		dummy.setName("new goal");
		dummy.setValue(0);
		dummy.setActType(ActivityType.Running);
		dummy.setUnit(ActivityUnit.km);
		
		m.addObject("goal", dummy);
		m.addAllObjects(errors);
		m.setViewName("plandetail");
		return m;
	}

}
