package com.diary.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.diary.model.Activity;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;

@Repository("goalDao")
public class GoalDaoHibernate extends GenericDaoHibernate<Goal, Long> implements
		GoalDao {

	public GoalDaoHibernate() {
		// TODO Auto-generated constructor stub
		super(Goal.class);
	}
	
//	@Override
//	public List<Goal> getGoalsForPlan(long planID) {
//		// TODO Auto-generated method stub
//		return getSession().createCriteria(Goal.class).add(Restrictions.eq("plan_ID", planID)).list();
//	}

//	@Override
//	public List<Goal> getGoalsForPlan(long planID, ActivityType type) {
//		// TODO Auto-generated method stub
//		return getSession().createCriteria(Goal.class).add(Restrictions.eq("plan_ID", planID))
//													  .add(Restrictions.eq("actType", type)).list();
//	}

	@Override
	public List<Goal> getCompletedGoalsForPlan(long planID) {
		// TODO Auto-generated method stub
		return getSession().createSQLQuery("SELECT * FROM Goal WHERE plan_ID="+planID+" AND completed="+true).list();
	}

	@Override
	public List<Goal> getUncompletedGoalsForPlan(long planID) {
		// TODO Auto-generated method stub
		return getSession().createSQLQuery("SELECT * FROM Goal WHERE plan_ID="+planID+" AND completed="+false).list();
	}

	@Override
	public void removeGoals(long planID) {
		// TODO Auto-generated method stub
		//get ids of goals to delete
		List<BigInteger> ids = getSession().createSQLQuery("SELECT ID FROM Goal WHERE plan_ID="+planID).list();
		for (BigInteger id : ids) {
			//delete rows from goal_activity table
			deleteActivitiesForGoal(id.longValue());
		}
		//now delete the goals
		getSession().createSQLQuery("DELETE FROM Goal WHERE plan_ID="+planID).executeUpdate();		
	}

	@Override
	public Set<Activity> getActivitiesForGoal(Goal goal) {
		// TODO Auto-generated method stub
		return get(goal.getID()).getActivities();
	}

	@Override
	public void deleteActivitiesForGoal(Goal goal) {
		// TODO Auto-generated method stub
		deleteActivitiesForGoal(goal.getID());
	}
	
	private void deleteActivitiesForGoal(Long goalID) {
		getSession().createSQLQuery("DELETE FROM goal_activity WHERE goalID="+goalID).executeUpdate();
	}

	@Override
	public Goal calculateProgress(Goal goal) {
		// TODO Auto-generated method stub
		//get activities of the goal
		log.debug("Entering calculateProgress method..");
		log.debug("Goal ID: "+goal.getID());
		Set<Activity> activities = getActivitiesForGoal(goal);
		double sum = 0;
		
		log.debug("Activities found: "+activities.size());
		
		//sum up the values of the activities
		for (Activity activity : activities) {
			sum += activity.defaultUnitValue();
		}
		
		//calculate the progress and save the goal
		goal.setProgress(sum/goal.defaultUnitValue());
		
		log.debug("Progress calculated: "+goal.getProgress());
		
		getSession().update(goal);
		
		log.debug("Goal saved.");
		
		return goal;
	}


}
