package com.diary.webapp.controller;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diary.model.Activity;
import com.diary.model.DailyRecord;
import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;
import com.diary.service.ActivityManager;
import com.diary.service.DRManager;

/**
 * This controller uses both daily record manager and activity manager.
 * @author Zdenda
 *
 */
@Controller	
@RequestMapping("/dr")
public class DRController {
	
	private DRManager drm;
	private ActivityManager aManager;
	private UserManager userManager;
	
	@Autowired
	public void setDRManager(@Qualifier("drm") DRManager drm) {
		this.drm = drm;
	}
	
	@Autowired
	public void setaManager(@Qualifier("aManager") ActivityManager aManager) {
		this.aManager = aManager;
	}
	
	@Autowired
	public void setUserManager(@Qualifier("userManager") UserManager userManager) {
		this.userManager = userManager;
	}
	
	/**
	 * Returns a ModelAndView with added daily record and it's activities. Daily record object's name is "dr" and
	 * name of the list of activities is "activities".
	 * @return ModelAndView with daily record and it's activities.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() {
		ModelAndView res = new ModelAndView();
		
		Long mID = getLoggedUserID();
		DailyRecord dr = drm.getTodayDailyRecord(mID);
		Activity dummy = new Activity();  //dummy activity for formular
		dummy.setName("new activity");
		dummy.setValue(0);
		dummy.setActType(ActivityType.Running);
		dummy.setUnit(ActivityUnit.km);
		
		res.addObject("dr",dr);
		res.addObject("activities", dr.getActivities());
		res.addObject("activity", dummy);
		res.addObject("activityUnit",ActivityUnit.getMap());
		res.addObject("activityType",ActivityType.getMap());
		return res;
	}
	
	/**
	 * Returns an ID of logged user or null if no user is logged. It would probably be better to pass
	 * http request as an argument to this function and get the ID from that request. 
	 * @return
	 */
	private Long getLoggedUserID() {
		if(SecurityContextHolder.getContext() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				User user;
				if (auth.getPrincipal() instanceof UserDetails) {
					UserDetails ud = (UserDetails)auth.getPrincipal();
					user = userManager.getUserByUsername(ud.getUsername());
				}
				else {
					String ud = (String)auth.getPrincipal();
					user = userManager.getUserByUsername(ud);
				}
				
				if (user != null) {
					return user.getId();
				}
			}
		}
		
		return null;
	}
}
