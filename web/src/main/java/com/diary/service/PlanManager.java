package com.diary.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.appfuse.service.GenericManager;

import com.diary.model.Goal;
import com.diary.model.Plan;
import com.diary.model.enums.ActivityType;

public interface PlanManager extends GenericManager<Plan, Long>{
	
	/**
	 * Return a list of plans which started after certain date (inclusive).
	 * @param date Starting date.
	 * @return List of plans. The list will be empty if no plans are found.
	 */
	public List<Plan> getPlansFrom(Date date, long memberID);
	
	/**
	 * Returns a list of all unfinished plans of certain member.
	 * @param memberID	ID of member requested plans belong to.
	 * @return	List of plans. Empty if no plans are found.
	 */
	public List<Plan> getAllUnfinishedPlans(long memberID);
	
	/**
	 * Checks if member can plan.
	 * @param memberID ID of member who is trying to access plan.
	 * @param planID ID of plan which is being accessed.
	 * @return True or false.
	 */
	public boolean canAccess(long memberID, long planID);
	
	/**
	 * Returns a set of goals assigned to plan.
	 * @param planID ID of plan
	 * @return
	 */
	public Set<Goal> getGoalsFor(long planID);
	
	/**
	 * Returns a set of goals with certain activity type which are assigned to a plan.
	 * @param planID ID of a plan.
	 * @param actType Activity type.
	 * @return
	 */
	public Set<Goal> getGoalsFor(long planID, ActivityType actType);
}
