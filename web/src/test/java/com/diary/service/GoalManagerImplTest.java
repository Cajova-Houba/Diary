package com.diary.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.appfuse.service.impl.BaseManagerMockTestCase;
import org.bouncycastle.jce.provider.JDKGOST3410Signer.gost3410;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.diary.dao.GoalDao;
import com.diary.dao.PlanDao;
import com.diary.model.Activity;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.assertSame;

public class GoalManagerImplTest extends BaseManagerMockTestCase {

	@InjectMocks
	private GoalManagerImpl goalManager;
	
	@InjectMocks
	private PlanManagerImpl planManager;
	
	@Mock
	private GoalDao goalDao;
	
	@Mock
	private PlanDao planDao;
	
//	@Test
//	public void testGetGoalsForPlan() {
//		//there are two goals for plan with ID 1 in sample data
//		//given
//		final Long id = 1L;
//		final List<Goal> goals = new ArrayList<Goal>();
//		given(goalDao.getGoalsForPlan(id)).willReturn(goals);
//		
//		//when
//		List<Goal> result = goalManager.getGoalsForPlan(id);
//		
//		//then
//		assertSame(goals,result);
//	}
	
//	@Test
//	public void testGetGoalsForPlan2() {
//		//there are 4 goals for plan with ID 2 in sample data
//		//two goals for running
//		//one goal for swimming
//		//one goal for jogging
//		final Long planID = 2L;
//		
//		final ActivityType running = ActivityType.Running;
//		final List<Goal> runningGoals = new ArrayList<Goal>(); //size = 2
//		runningGoals.add(new Goal());
//		runningGoals.add(new Goal());
//		
//		final ActivityType swimming = ActivityType.Swimming;
//		final List<Goal> swimmingGoals = new ArrayList<Goal>(); //size = 1
//		swimmingGoals.add(new Goal());
//		
//		final ActivityType jogging = ActivityType.Jogging;
//		final List<Goal> joggingGoals = new ArrayList<Goal>(); //size = 1
//		joggingGoals.add(new Goal());
//		
//		//given
//		given(goalDao.getGoalsForPlan(planID, running)).willReturn(runningGoals);
//		given(goalDao.getGoalsForPlan(planID, swimming)).willReturn(swimmingGoals);
//		given(goalDao.getGoalsForPlan(planID, jogging)).willReturn(joggingGoals);
//		
//		//when
//		List<Goal> runningResult = goalManager.getGoalsForPlan(planID, running);
//		List<Goal> swimmingResult = goalManager.getGoalsForPlan(planID, swimming);
//		List<Goal> joggingResult = goalManager.getGoalsForPlan(planID, jogging);
//		
//		//then
//		assertSame(runningGoals, runningResult);
//		assertSame(swimmingGoals, swimmingResult);
//		assertSame(joggingGoals, joggingResult);
//	}
	
	@Test
	public void testGetCompletedGoalsForPlan() {
		//there is one completed goal for plan with ID 1 in sample data
		//given
		final Long id = 1L;
		final List<Goal> goals = new ArrayList<Goal>();
		given(goalDao.getCompletedGoalsForPlan(id)).willReturn(goals);
		
		//when
		List<Goal> result = goalManager.getCompletedGoalsForPlan(id);
		
		//then
		assertSame(goals,result);
	}
	
	@Test
	public void testGetUncompletedGoalsForPlan() {
		//there is one uncompleted goal for plan with ID 1 in sample data
		//given
		final Long id = 1L;
		final List<Goal> goals = new ArrayList<Goal>();
		given(goalDao.getUncompletedGoalsForPlan(id)).willReturn(goals);
		
		//when
		List<Goal> result = goalManager.getUncompletedGoalsForPlan(id);
		
		//then
		assertSame(goals,result);
	}
	
	@Test
	public void testRemoveGoals() {
		//there are two goals (ID=4 and ID=5) for plan with ID=5
		final long planID = 5L;
		
		//given
		willDoNothing().given(goalDao).removeGoals(planID);
		
		//when
		goalManager.removeGoals(planID);
		
		//then
		verify(goalDao).removeGoals(planID);
	}
	
	@Test
	public void testGetActivitiesForGoal() {
		//there are three activities assigned to goal with ID=10
		final Goal g = new Goal();
		g.setID(10L);
		
		final Set<Activity> activities = new HashSet<>(); //size = 3
		activities.add(new Activity());
		activities.add(new Activity());
		activities.add(new Activity());
		
		//given
		given(goalDao.getActivitiesForGoal(g)).willReturn(activities);
		
		//when
		Set<Activity> result = goalManager.getActivitiesForGoal(g);
		
		//then
		assertSame(activities, result);
	}
	
	@Test
	public void testDeleteActivitiesForGoal() {
		//the are three activities assigned to goal with ID=10
		final Goal g = new Goal();
		g.setID(10L);
		
		Set<Activity> empty = new HashSet<>();
		
		//given
		willDoNothing().given(goalDao).deleteActivitiesForGoal(g);
		given(goalDao.getActivitiesForGoal(g)).willReturn(empty);
		
		//when
		goalManager.deleteActivitiesForGoal(g);
		Set<Activity> result = goalManager.getActivitiesForGoal(g);
		
		//then
		verify(goalDao).deleteActivitiesForGoal(g);
		assertSame(empty, result);
	}
	
	@Test
	public void testCalculateProgress() {
		//there are three activities assigned to goal with ID=2
		//sum of those activities is 6km
		//value of goal with ID=2 is 12km
		//so the progress should be 0.5
		final Goal g = new Goal();
		g.setID(2L);
		final Goal progress = new Goal();
		g.setID(2L);
		g.setProgress(0.5);
		
		//given
		given(goalDao.calculateProgress(g)).willReturn(progress);
		
		//when
		Goal result = goalManager.calculateProgress(g);
		
		//then
		assertSame(progress, result);
	}
	
//	@Test
//	public void testCalculateProgressForGoals() {
//		//there are two goals assigned to plan with id=-1
//		//the first goal has one activity assigned, the second has no activity assigned
//		final long planID = -1L;
//		final double err = 0.005;
//		List<Goal> goals = gManager.calculateProgressForGoals(planID);
//		
//		assertEquals(2, goals.size());
//		
//		//the goal with id = -1 should have progress 0.5
//		//the goal with id = -2 should have progress 0
//		for (Goal goal : goals) {
//			if (goal.getID() == -1L) {
//				assertEquals(0.5, goal.getProgress(),err);
//			}
//			else if (goal.getID() == -2L) {
//				assertEquals(0, goal.getProgress(),err);
//			}
//		}
//	}
	
	
}
