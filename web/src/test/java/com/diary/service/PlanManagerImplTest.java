package com.diary.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.service.impl.BaseManagerMockTestCase;
import org.aspectj.weaver.MemberImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.diary.dao.PlanDao;
import com.diary.model.Plan;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

public class PlanManagerImplTest extends BaseManagerMockTestCase {
	
	@InjectMocks
	private PlanManagerImpl planManager;
	
	@Mock
	private PlanDao planDao;
	
	@Test
	public void testGetPlan() {
		final Long id = 1L;
		final Plan plan = new Plan();
		given(planDao.get(id)).willReturn(plan);
		
		Plan result = planManager.get(id);
		
		assertSame(plan,result);
	}
	
	@Test
	public void testSave() {
		log.debug("Testing addPlan()");
		//there is no plan with memberID=10 in sample data
		final long memberID = 10L;
		final String name = "test plan";
		final Date fromDate = Date.valueOf("2015-1-1");
		final Plan plan = new Plan();
		plan.setMemberID(memberID);
		plan.setName(name);
		plan.setFromDate(fromDate);
		
		given(planDao.save(plan)).willReturn(plan);
		
		planManager.save(plan);
		
		verify(planDao).save(plan);
	}
	
	@Test
	public void testGetPlansFrom() {
		//there is only one plan for member with id=-2 and this play has dateFrom 2015-1-1
		final List<Plan> plans = new ArrayList<Plan>(); //size = 1
		plans.add(new Plan());
		
		final Long memberID = 2L;
		final Date from = Date.valueOf("2015-1-1");
		
		//given
		given(planDao.getPlansFrom(from, memberID)).willReturn(plans);
		
		//when
		List<Plan> result = planManager.getPlansFrom(from, memberID);
		
		//then
		assertSame(plans, result);
	}
	
	@Test
	public void testGetAllUnfinishedPlans() {
		//there are 4 unfinished plans for user with ID=1 in sample data
		final Long memberID = 1L;
		final List<Plan> plans = new ArrayList<>(); //size 4
		plans.add(new Plan());
		plans.add(new Plan());
		plans.add(new Plan());
		plans.add(new Plan());
		
		//given
		given(planDao.getAllUnfinishedPlans(memberID)).willReturn(plans);
		
		//when
		List<Plan> result = planManager.getAllUnfinishedPlans(memberID);
		
		//then
		assertSame(plans, result);
	}
	
	@Test
	public void testCanAccess() {
		//plan with ID 1 can be accessed only by member with ID -2
		//and plan with ID 2 can be accessed only by member with ID 1
		final long member1 = -2L;
		final long plan1 = 1L;
		
		final long member2 = 1L;
		final long plan2 = 2L;
		
		//given
		given(planDao.canAccess(member1, plan1)).willReturn(true);
		given(planDao.canAccess(member1, plan2)).willReturn(false);
		given(planDao.canAccess(member2, plan1)).willReturn(false);
		given(planDao.canAccess(member2, plan2)).willReturn(true);
		
		//then
		assertSame(true, planManager.canAccess(member1, plan1));
		assertSame(false, planManager.canAccess(member1, plan2));
		assertSame(false, planManager.canAccess(member2, plan1));
		assertSame(true, planManager.canAccess(member2, plan2));
	}
}
