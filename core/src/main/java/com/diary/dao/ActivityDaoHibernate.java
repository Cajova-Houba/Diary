package com.diary.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.appfuse.dao.SearchException;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.diary.model.Activity;
import com.diary.model.DailyRecord;
import com.diary.model.Goal;

@Repository("aDao")
public class ActivityDaoHibernate extends GenericDaoHibernate<Activity, Long> implements
		ActivityDao {

	public ActivityDaoHibernate() {
		super(Activity.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns a list of activities for one certain daily record.
	 * @param dailyRecordID	ID of daily record.
	 * @return	List of activities.
	 */
//	@Override
//	public List<Activity> getActivitesForDailyRecord(long dailyRecordID) {
//		// TODO Auto-generated method stub
//		return getSession().createCriteria(Activity.class).add(Restrictions.eq("drID", dailyRecordID)).list();
//		return null;
//	}

	@Override
	public Set<Goal> getGoalsForActivity(Activity activity) {
		// TODO Auto-generated method stub
		return get(activity.getID()).getGoals();
	}

	@Override
	public void deleteGoalsForActivity(Activity activity) {
		// TODO Auto-generated method stub
		deleteGoalsForActivity(activity.getID());
	}
	
	@Override
	public void remove(Activity object) {
		// TODO Auto-generated method stub
		//first, delete everything from goal_activity table
		deleteGoalsForActivity(object);
		super.remove(object);
	}
	
	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		//first, delete everything from goal_activity table
		deleteGoalsForActivity(id);	
		super.remove(id);
	}
	
	private void deleteGoalsForActivity(Long id) {
		getSession().createSQLQuery("DELETE FROM goal_activity WHERE activityID="+id).executeUpdate();
	}

}
