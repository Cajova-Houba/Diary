package com.diary.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diary.model.Activity;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;


public class GoalDaoTest extends BaseDaoTestCase{

	@Autowired(required=true)
	private GoalDao goalDao;
	
	@Autowired(required=true)
	private PlanDao planDao;
	
//	@Test
//	public void testGetGoalsForPlan() {
//		//there is one goal assigned to 
//		List<Goal> goals = goalDao.getGoalsForPlan(1L);
//		assertEquals(3,goals.size());
//	}
	
//	@Test
//	public void testGetGoalsForPlan2() {
//		//there are 4 goals for plan with ID 2 in sample data
//		//two goals for running
//		//one goal for swimming
//		//one goal for jogging
//		List<Goal> goals1 = goalDao.getGoalsForPlan(2L, ActivityType.Running);
//		assertEquals(2, goals1.size());
//		
//		List<Goal> goals2 = goalDao.getGoalsForPlan(2L, ActivityType.Swimming);
//		assertEquals(1, goals2.size());
//		
//		List<Goal> goals3 = goalDao.getGoalsForPlan(2L, ActivityType.Jogging);
//		assertEquals(1, goals3.size());
//	}
	
	@Test
	public void testGetCompletedGoalsForPlan() {
		//there is one completed goal for plan with ID -1 in sample data
		List<Goal> goals = goalDao.getCompletedGoalsForPlan(-1L);
		assertEquals(1, goals.size());
	}
	
	@Test
	public void testGetUncompletedGoalsForPlan() {
		//there is one uncompleted goal for plan with ID -1 in sample data
		List<Goal> goals = goalDao.getUncompletedGoalsForPlan(-1L);
		assertEquals(1, goals.size());
	}
	
	@Test
	public void testRemoveGoals() {
		//there are two goals for plan with id -1
		long planID = -1L;
		goalDao.removeGoals(planID);
		
		Set<Goal> goals = planDao.getGoalsFor(planID);
		assertTrue(goals.isEmpty());
	}
	
	@Test
	public void testMNRelationship() {
		//goal with ID=-1 should have one activity assigned
		Goal g = goalDao.get(-1L);
		assertFalse(g.getActivities().isEmpty());
	}
	
	@Test
	public void testGetActivitiesForGoal() {
		//there is one activity assigned to goal with ID=-1
		Goal g = new Goal();
		g.setID(-1L);
		Set<Activity> activities = goalDao.getActivitiesForGoal(g);
		
		assertFalse(activities.isEmpty());
		assertSame(1, activities.size());
	}
	
	@Test
	public void testDeleteActivitiesForGoal() {
		//there is one activity assigned to goal with ID=-1
		Goal g = new Goal();
		g.setID(-1L);
		goalDao.deleteActivitiesForGoal(g);
		g = goalDao.get(-1L);
		assertTrue(g.getActivities().isEmpty());
	}
	
	@Test
	public void testCalculateProgress() {
		////there is one activity assigned to goal with ID=-1
		//sum of those activities is 3km
		//value of goal with ID=-1 is 5km
		//so the progress should be 0.5
		Goal g = goalDao.get(-1L);
		goalDao.calculateProgress(g);
		
		g = goalDao.get(-1L);
		assertEquals(0.5, g.getProgress(),0.001);
	}
}
