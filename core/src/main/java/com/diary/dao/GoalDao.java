package com.diary.dao;

import java.util.List;
import java.util.Set;

import org.appfuse.dao.GenericDao;

import com.diary.model.Activity;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;

public interface GoalDao extends GenericDao<Goal, Long>{

	/**
	 * Returns a list of goals for a certain plan.
	 * @param planID ID of a plan.
	 * @return List of goals. Empty if no goals are found.
	 */
//	public List<Goal> getGoalsForPlan(long planID);
	
	/**
	 * Returns a list of goals for a certain plan with certain activity type.
	 * @param planID ID of a plan.
	 * @param type Type of goal's activity.
	 * @return List of goals. Empty if no goals are found.
	 */
//	public List<Goal> getGoalsForPlan(long planID, ActivityType type);
	
	/**
	 * Returns a list of completed goals for a certain plan.
	 * @param planID ID of a plan.
	 * @return List of completed goals. Empty if no goals are found.
	 */
	public List<Goal> getCompletedGoalsForPlan(long planID);
	
	/**
	 * Returns a list of uncompleted goals for a certain plan.
	 * @param planID
	 * @return
	 */
	public List<Goal> getUncompletedGoalsForPlan(long planID);
	
	/**
	 * Removes every goal which belongs to a certain plan.
	 * @param planID All goals with this planID will be removed.
	 */
	public void removeGoals(long planID);
	
	/**
	 * Returns list of activities assigned to a certain goal.
	 * @param goal
	 * @return
	 */
	public Set<Activity> getActivitiesForGoal(Goal goal);
	
	/**
	 * Removes the assigned activities from the goal.
	 * @param goal
	 */
	public void deleteActivitiesForGoal(Goal goal);
	
	/**
	 * Calculates the progress of the goal, saves it and returns the goal.
	 * @param goal
	 * @return
	 */
	public Goal calculateProgress(Goal goal);
	
}
