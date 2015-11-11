package com.diary.dao;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diary.dao.DailyRecordDao;
import com.diary.model.Activity;
import com.diary.model.DailyRecord;

public class DailyRecordDaoTest extends BaseDaoTestCase {

	@Autowired(required = true)
	private DailyRecordDao dailyRecordDao;
	
	@Test
	public void testGetDailyRecordsFrom() {
		/*Theres one daily record from 2015-5-15 for member with ID=1L*/
		List<DailyRecord> dr = dailyRecordDao.getDailyRecordsFrom(Date.valueOf("2015-5-15"),1L);
		assertFalse(dr.isEmpty());
	}
	
	@Test
	public void testGetTodayDailyRecord() {
		//there are no records for today for member with ID=0, so the method will have to create a new one, save it into database and return it
		DailyRecord dr = dailyRecordDao.getTodayDailyRecord(0L);
		assertNotNull(dr);
		
		//right now, there should be exactly one record for member with ID=0
		//test will try to call getTodayDailyRecord(0L) again and if dr.getID() is different from id, this test will fail.
		long id = dr.getID();
		dr = dailyRecordDao.getTodayDailyRecord(0L);
		assertEquals(id, dr.getID().longValue());
		}
	
	@Test
	public void testCanAccess() {
		//daily record with ID=-5L has memberID=1L so the member with ID=1L should be able to access it
		assertTrue(dailyRecordDao.canAccess(1L, -5L));
		assertFalse(dailyRecordDao.canAccess(2L, -5L)); //member with ID=2 shouldn't 
		
		//there is no member with ID=-10L and no daily record with ID=-10L
		assertFalse(dailyRecordDao.canAccess(-10L, -10L));
	}
	
	@Test
	public void testGetActivitiesFor() {
		//there is one activity assigned to daily record with ID=-5
		Set<Activity> activities = dailyRecordDao.getActivitiesFor(-5L);
		assertFalse(activities.isEmpty());
	}
}

