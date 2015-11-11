package com.diary.dao;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diary.dao.PlanDao;
import com.diary.model.Goal;
import com.diary.model.Plan;
import com.diary.model.enums.ActivityType;


public class PlanDaoTest extends BaseDaoTestCase {

	@Autowired
	private PlanDao planDao;
	
	@Test
	public void testGetPlansFrom() {
		
		//there is only one plan for member with id=-1 and this play has dateFrom 2015-1-1
		long memberID = -1L;
		List<Plan> plans = planDao.getPlansFrom(Date.valueOf("2015-1-1"),memberID);
		assertEquals(1, plans.size());
	}
	
	@Test
	public void testGetAllUnfinishedPlans() {
		//there is 1 unfinished plans for user with ID=-1 in sample data
		long memberID = -1L;
		List<Plan> plans = planDao.getAllUnfinishedPlans(memberID);
		assertEquals(1, plans.size());
	}
	
	@Test
	public void testSave() {
		//there is no plan with memberID=10 in sample data
		final long memberID = 10L;
		final String name = "test plan";
		final Date fromDate = Date.valueOf("2015-1-1");
		final Plan plan = new Plan();
		plan.setMemberID(memberID);
		plan.setName(name);
		plan.setFromDate(fromDate);
		
		planDao.save(plan);
		
		List<Plan> pList = planDao.getAllUnfinishedPlans(memberID);
		assertEquals(1, pList.size());
		Plan res = pList.get(0);
		assertEquals(memberID, res.getMemberID());
		assertEquals(name, plan.getName());
		assertEquals(fromDate, plan.getFromDate());
	}
	
	@Test
	public void testCanAccess() {
		//plan with ID -1 can be accessed only by member with ID -1
		assertTrue(planDao.canAccess(-1L, -1L));
		assertFalse(planDao.canAccess(1L, -1L));
	}
	
	@Test
	public void testGetGoalsFor() {
		//there is one goal assigned to plan with ID=-1
		Set<Goal> goals = planDao.getGoalsFor(-1L);
		assertFalse(goals.isEmpty());
	}
	
	@Test
	public void testGetGoalsFor2() {
		//there is one goal assigned to plan with ID=-1 and activity type of running
		Set<Goal> goals = planDao.getGoalsFor(-1L,ActivityType.Running);
		assertFalse(goals.isEmpty());
	}
}
