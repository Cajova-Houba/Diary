package com.diary.dao;

import static org.junit.Assert.*;

import java.util.Set;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diary.model.Activity;
import com.diary.model.DailyRecord;
import com.diary.model.Goal;
import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;

public class ActivityDaoTest extends BaseDaoTestCase{
	
	@Autowired(required = true)
	private ActivityDao aDao;
	
	
	@Test
	public void testSaveActivity() {
		assertNotNull(aDao);
		Activity a = new Activity();
		
		//there is daily record with id -5 in test data
		long drID = -5L;
		//a.setDrID(drID);
//		a.setDailyRecord(new DailyRecord(drID));
		a.setName("testtest");
		a.setActType(ActivityType.test);
		a.setUnit(ActivityUnit.km);
		a.setValue(1);
		
		a = aDao.save(a);
		
		assertSame(a, aDao.get(a.getID()));
	}
	
	@Test
	public void testGetGoalsForActivity() {
		//activity with ID=-1 is assigned to 1 goal
		Activity a = new Activity();
		a.setID(-1L);
		
		Set<Goal> goals = aDao.getGoalsForActivity(a);
		assertFalse(goals.isEmpty());
	}
	
	@Test
	public void testDeleteGoalsForActivity() {
		//activity with ID=-1 is assigned to 1 goal
		Activity a = new Activity();
		a.setID(-1L);
		
		aDao.deleteGoalsForActivity(a);
		Set<Goal> goals = aDao.getGoalsForActivity(a);
		assertTrue(goals.isEmpty());
	}
	
	@Test
	public void testToString() {
		String name = "test";
		ActivityType actType = ActivityType.test;
		ActivityUnit actUnit = ActivityUnit.test;
		double value = 3.0;
		
		Activity a = new Activity();
		a.setName(name);
		a.setActType(actType);
		a.setUnit(actUnit);
		a.setValue(value);
		a = aDao.save(a);
		
		String expected = String.format("%s: [ ID=%d, name=%s, ActType=%s, ActUnit=%s, value=%f ]",Activity.class.getName(),
										 a.getID(),name,actType.toString(),actUnit.toString(),value);
		
		assertEquals(expected, aDao.get(a.getID()).toString());
	}
	
	@Test
	public void testGetDailyRecordForActivity() {
		/*activity with ID=-1 is assigned to daily record with ID=-5*/
		Activity activity = aDao.get(-1L);
		DailyRecord dr = activity.getDailyRecord();
		assertSame(-5L, dr.getID());
	}
	
	
}
