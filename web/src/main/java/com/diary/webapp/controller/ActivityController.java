package com.diary.webapp.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.facet.search.DrillDown;
import org.appfuse.service.GenericManager;
import org.appfuse.service.UserManager;
import org.bouncycastle.asn1.x509.qualified.TypeOfBiometricData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diary.dao.GoalDao;
import com.diary.model.Activity;
import com.diary.model.DailyRecord;
import com.diary.model.Plan;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;
import com.diary.service.ActivityManager;
import com.diary.service.DRManager;
import com.diary.service.GoalManager;
import com.diary.service.PlanManager;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseFormController {
	
	private ActivityManager aManager;
	
	private UserManager userManager;
	
	private DRManager drManager;
	
	private PlanManager planManager;
	
	private GoalManager goalManager;
	
	
	@Autowired
	public void setAManager(@Qualifier("aManager") ActivityManager aManager) {
		this.aManager = aManager;
	}
	
	@Autowired
	public void setUserManager(@Qualifier("userManager") UserManager userManager) {
		// TODO Auto-generated method stub
		this.userManager = userManager;
	}
	
	@Autowired
	public void setDrManager(@Qualifier("drm") DRManager drManager) {
		this.drManager = drManager;
	}
	
	@Autowired
	public void setPlanManager(@Qualifier("planManager")PlanManager planManager) {
		this.planManager = planManager;
	}
	
	@Autowired
	public void setGoalManager(@Qualifier("goalManager")GoalManager goalManager) {
		this.goalManager = goalManager;
	}
	
	public ActivityController() {
		setCancelView("redirect:/dr");
		setSuccessView("redirect:/dr");
	}
	
	@ModelAttribute
	@RequestMapping(method=RequestMethod.GET)
	public String showForm() {
		//just redirect to dr
		return getSuccessView();
	}
	
	/**
	 * Method for saving existing activity.
	 * @param request
	 * @param activity
	 * @param errors
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editActivity(HttpServletRequest request, Activity activity, BindingResult errors) {
		
		log.debug("Entering editActivity metho..");
		
		if(activity == null) {
			log.error("Error: no activity.");
			return getCancelView();
		}
		
		log.debug(activity.toString());
		
		//load user ID
		String username = request.getRemoteUser();
		Long memberID = userManager.getUserByUsername(username).getId();
		
		//load dr ID
		DailyRecord dr = activity.getDailyRecord();
		if (dr == null) {
			log.error("No daily record for activity with ID="+activity.getID());
			return getCancelView();
		}
		Long drID = dr.getID();
		
		//check if user can edit this dr
		if(drManager.canAccess(memberID, drID)) {
			log.debug("Member with ID="+memberID+" can access daily record with ID="+drID);
			
			//check if type of the activity was changed
			if(activity.getActType() == aManager.get(activity.getID()).getActType()) {
				//it wasn't, everything is okay
				activity = aManager.save(activity);
				log.debug("Saved "+activity.toString());
			} else {
				//the type was changed, so the activity needs to be reassigned to 
				//goals with right activity type
				
				//delete the relationship between edited activity and goals the activity is assigned to
				aManager.deleteGoalsForActivity(activity);
				activity = aManager.save(activity);
				
				//now it's the same as adding new activity
				//assign activity to goals
				activity = assignToGoals(activity, memberID);
			}
			
			return getSuccessView();
		}
		else {
			log.error("Error: user with ID="+memberID+" can't access daily record with ID="+drID);
			return getCancelView();
		}
	}
	
	/**
	 * Method for adding new activity
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public String onSubmit(HttpServletRequest request) {
		
		//loading parameters
		String val = "default";
		long id = 0L;
		
		String name = val;
		int actType = ActivityType.unknown.getID();
		int unit = ActivityUnit.unknown.getId();
		float value = 0;
		
		//logged member ID
		String username = request.getRemoteUser();
		Long memberID = userManager.getUserByUsername(username).getId();
		
		try {
			id = Long.parseLong(request.getParameter("id"));
			name = request.getParameter("name");
			actType = Integer.parseInt(request.getParameter("actType"));
			unit = Integer.parseInt(request.getParameter("unit"));
			value = Float.parseFloat(request.getParameter("value"));
		} catch (Exception e) {
			// TODO: handle exception
			return getCancelView();
		} 
		
		log.debug(String.format("parameters: drID=%d,name=%s,actType=%s,actUnit=%s,value=%f",
							     id,name,ActivityType.getByID(actType).toString(),ActivityUnit.getByID(unit).toString(),
							     value));
		
		Activity activity = new Activity();
		activity.setDailyRecord(drManager.get(id));
		activity.setName(name);
		activity.setActType(ActivityType.getByID(actType));
		activity.setUnit(ActivityUnit.getByID(unit));
		activity.setValue(value);
		
		log.debug("Activity before saving: "+activity.toString());
		
		//now the ID oc activity is setted
		//if this isn't done, activity would save up to the activity table many times
		//via the goalManager.save(goal) command
		activity = aManager.save(activity);
		log.debug("Activity after saving: "+activity.toString());
		log.debug("Saved activity: "+aManager.get(activity.getID()).toString());
		
		//assign activity to goals
		activity = assignToGoals(activity, memberID);
		
//		for (Goal goal : goals) {
//			goal.getActivities().add(activity);
//			//this will save the activity and also adds the row into the goal_activity table
//			goalManager.save(goal);
//		}
		
		log.debug("Saved activity: "+aManager.get(activity.getID()).toString());
		return getSuccessView();
	}
	
	/**
	 * Detail of activity. Parameter "aID" with activity ID must be provided.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView display(HttpServletRequest request) {
		ModelAndView m = new ModelAndView("activityForm");
		
		//check if parameter aID exists
		Long aID = 0L;
		try {
			aID = Long.parseLong(request.getParameter("aID"));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while parsing activity ID. Exception message: "+e.toString());
			return new ModelAndView(getCancelView());
		}
		
		//load user ID
		String username = request.getRemoteUser();
		Long memberID = userManager.getUserByUsername(username).getId();
		
		//load activity
		Activity a = aManager.get(aID);
		if (a == null) {
			log.error("Error: no activity with ID="+aID);
			return new ModelAndView(getCancelView()); 
		}
		//load daily record this activity belongs to
		DailyRecord dr = a.getDailyRecord();
		if (dr == null) {
			log.error("Error: no daily record for activity with ID="+aID+" found");
			return new ModelAndView(getCancelView());
		}
		//check if user can access this activity
		if (drManager.canAccess(memberID, dr.getID())) {
			m.addObject("activity", a);
		}
		else {
			log.error("Error: member with ID="+memberID+" can't access daily record with ID="+dr.getID());
			return new ModelAndView(getCancelView());
		}
		
		return m;
	}
	
	/**
	 * Deletes an activity if parameter "activityID" containing ID of an activitiy is provided.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String deleteActivity(HttpServletRequest request) {
		
		//check the activityID parameter
		Long aID = 0L;
		try {
			aID = Long.parseLong(request.getParameter("activityID"));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error while parsing activityID parameter. Exception message: "+e.toString());
			return getCancelView();
		}
		
		//get logged user
		String username = request.getRemoteUser();
		Long memberID = userManager.getUserByUsername(username).getId();
		
		//get activity
		Activity a = aManager.get(aID);
		if(a == null) {
			log.error("Error: no activity with ID="+aID+" found.");
			return getCancelView();
		}
		
		//get daily record activity belongs to
		DailyRecord dr = a.getDailyRecord();
		if(dr == null) {
			log.error("Error: no daily record for activity with ID="+aID+" found.");
			return getCancelView();
		}
		
		//check if user can access this daily record
		if(drManager.canAccess(memberID, dr.getID())) {
			aManager.remove(aID);
			return getSuccessView();
		}
		else {
			log.error("Error: user with ID="+memberID+" can't access daily record with ID="+dr.getID());
			return getCancelView();
		}
	}
	
	/**
	 * Assigns activity to goals with the same activity type and returns the activity.
	 * @param activity
	 * @param memberID ID of logged member
	 * @return
	 */
	private Activity assignToGoals(Activity activity, Long memberID) {
		log.debug("Entering assignToGoals method..");
		log.debug(activity.toString());
		
		//get active plans and their goals with the same type as activity
		List<Plan> plans = planManager.getAllUnfinishedPlans(memberID);
		log.debug(plans.size()+" plans for user "+memberID+" found");
		
		for (Plan plan : plans) {
			log.debug("Plan ID: "+plan.getID());
			//add new activity for every goal of active plans with matching type
			//i will have to think of a better way of doing this
			Set<Goal> goals = planManager.getGoalsFor(plan.getID(), activity.getActType());
			log.debug(goals.size()+" goals found.");
			for(Object g : goals) {
				Goal goal;
				if (g instanceof Goal) {
					goal = (Goal) g;
				} else {
					log.debug("Object: "+g.getClass());
					continue;
				}
				log.debug(goal.toString());
				
				Set<Activity> activities = goalManager.getActivitiesForGoal(goal);
				log.debug(activities.size()+" activities found.");
				
				activities.add(activity);
				goal.setActivities(activities);
				goalManager.save(goal);
				log.debug("Saved activity: "+aManager.get(activity.getID()).toString());
			}
		}
		return activity;
	}

}
