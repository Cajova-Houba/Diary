package com.diary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.dao.GoalDao;
import com.diary.dao.PlanDao;
import com.diary.model.Activity;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;

@Service("goalManager")
public class GoalManagerImpl extends GenericManagerImpl<Goal, Long> implements
		GoalManager {

	GoalDao goalDao;
	
	@Autowired
	private PlanDao planDao;
	
	@Autowired
	public GoalManagerImpl(GoalDao goalDao) {
		// TODO Auto-generated constructor stub
		super(goalDao);
		this.goalDao = goalDao;
	}
	
//	@Override
//	public List<Goal> getGoalsForPlan(long planID) {
//		// TODO Auto-generated method stub
//		return goalDao.getGoalsForPlan(planID);
//	}

	@Override
	public List<Goal> getCompletedGoalsForPlan(long planID) {
		// TODO Auto-generated method stub
		return goalDao.getCompletedGoalsForPlan(planID);
	}

	@Override
	public List<Goal> getUncompletedGoalsForPlan(long planID) {
		// TODO Auto-generated method stub
		return goalDao.getUncompletedGoalsForPlan(planID);
	}

	@Override
	public void removeGoals(long planID) {
		goalDao.removeGoals(planID);
		
	}

//	@Override
//	public List<Goal> getGoalsForPlan(long planID, ActivityType type) {
//		// TODO Auto-generated method stub
//		return goalDao.getGoalsForPlan(planID, type);
//	}

	@Override
	public Set<Activity> getActivitiesForGoal(Goal goal) {
		// TODO Auto-generated method stub
		return goalDao.getActivitiesForGoal(goal);
	}

	@Override
	public void deleteActivitiesForGoal(Goal goal) {
		// TODO Auto-generated method stub
		goalDao.deleteActivitiesForGoal(goal);
	}

	@Override
	public Goal calculateProgress(Goal goal) {
		// TODO Auto-generated method stub
		log.debug("Entering calculateProgress method..");
		log.debug("Goal ID: "+goal.getID());
		return goalDao.calculateProgress(goal);
	}

	@Override
	public List<Goal> calculateProgressForGoals(long planID) {
		// TODO Auto-generated method stub
		try {
			//get the list of goals
			log.debug("Entering calculateProgressForGoals method..");
			log.debug("Plan ID: "+planID);
			
			Set<Goal> goals = planDao.getGoalsFor(planID);
			log.debug("Loaded goals: "+goals.size());
			List<Goal> res = new ArrayList<Goal>();
			
			for (Goal goal : goals) {
				goal = calculateProgress(goal);
				res.add(goal);
			}
			
			return res;
		} catch (Exception e) {
			log.error("Exception: "+e.toString());
			return new ArrayList<Goal>();
		} 
	}
	
	

}
