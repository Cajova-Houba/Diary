package com.diary.dao;

import java.util.List;
import java.util.Set;

import org.appfuse.dao.GenericDao;

import com.diary.model.Activity;
import com.diary.model.Goal;

public interface ActivityDao extends GenericDao<Activity, Long> {

	/**
	 * Returns a list of activities for one certain daily record.
	 * @param dailyRecordID	ID of daily record.
	 * @return	List of activities.
	 */
	//public List<Activity> getActivitesForDailyRecord(long dailyRecordID);
	
	/**
	 * Return a set of goals this activity is assigned to.
	 * @param activity Activity.
	 * @return Set of Goals.
	 */
	public Set<Goal> getGoalsForActivity(Activity activity);
	
	/**
	 * Remove relationship bewteen activity and goals it's assigned to. 
	 * @param activity
	 */
	public void deleteGoalsForActivity(Activity activity);
}
