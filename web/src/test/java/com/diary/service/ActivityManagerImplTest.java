package com.diary.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.appfuse.service.impl.BaseManagerMockTestCase;
import org.hibernate.jdbc.Expectation;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.diary.dao.ActivityDao;
import com.diary.model.Activity;
import com.diary.model.DailyRecord;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

public class ActivityManagerImplTest extends BaseManagerMockTestCase {

	@InjectMocks
	private ActivityManagerImpl aManager;
	
	@Mock
	private ActivityDao aDao;
	
//	@Test
//	public void testGetActivitesForDailyRecord() {
//		final List actvts = new ArrayList<>();
//		given(aDao.getActivitesForDailyRecord(5)).willReturn(actvts);
//		List res = aManager.getActivitesForDailyRecord(5);
//		assertSame(actvts,res);
//	}
	
	@Test
	public void testSave() {
		
		log.debug("test addActivity..");
		final Activity a = new Activity();
		
		//there is daily record with id 1 in test data
		long drID = 1L;
		a.setDailyRecord(new DailyRecord(drID));
		a.setName("testtest");
		a.setActType(ActivityType.test);
		a.setUnit(ActivityUnit.km);
		a.setValue(1);
		
		given(aDao.save(a)).willReturn(a);
		//when
		aManager.save(a);
		//then
		verify(aDao).save(a);
	}
	
	@Test
	public void testGetGoalsForActivity() {
		log.debug("test getGoalsForActivity..");
		
		//activity with ID=6L is assigned to two goals
		final Activity a = new Activity();
		a.setID(6L);
		
		Set<Goal> goals = new HashSet<>();
		//given
		given(aDao.getGoalsForActivity(a)).willReturn(goals);
		//when
		Set<Goal> result = aManager.getGoalsForActivity(a);
		//then
		assertSame(goals, result);
	}
	
	@Test
	public void testDeleteGoalsForActivity() {
		log.debug("test deleteGoalsForActivity..");
		
		//activity with ID=6 is assigned to 2 goals
		final Activity a = new Activity();
		a.setID(6L);
		
		//empty set
		Set<Goal> empty = new HashSet<>();
		
		//given
		willDoNothing().given(aDao).deleteGoalsForActivity(a);
		given(aDao.getGoalsForActivity(a)).willReturn(empty);
		
		//when
		aManager.deleteGoalsForActivity(a);
		Set<Goal> result = aManager.getGoalsForActivity(a);
		
		//verify
		verify(aDao).deleteGoalsForActivity(a);
		assertSame(empty, result);
		
	}
}
