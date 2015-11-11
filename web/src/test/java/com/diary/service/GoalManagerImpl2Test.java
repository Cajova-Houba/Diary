package com.diary.service;

import static org.junit.Assert.*;

import java.util.List;

import org.appfuse.service.BaseManagerTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diary.model.Goal;

public class GoalManagerImpl2Test extends BaseManagerTestCase {

	@Autowired
	private GoalManager goalManager;
	
	@Test
	public void testCalculateProgressForGoals() {
		//there are two goals assigned to plan with id=-1
		//the first goal has one activity assigned, the second has no activity assigned
		final long planID = -1L;
		final double err = 0.005;
		List<Goal> goals = goalManager.calculateProgressForGoals(planID);
		
		assertEquals(2, goals.size());
		
		//the goal with id = -1 should have progress 0.5
		//the goal with id = -2 should have progress 0
		for (Goal goal : goals) {
			if (goal.getID() == -1L) {
				assertEquals(0.5, goal.getProgress(),err);
			}
			else if (goal.getID() == -2L) {
				assertEquals(0, goal.getProgress(),err);
			}
		}
	}
}
