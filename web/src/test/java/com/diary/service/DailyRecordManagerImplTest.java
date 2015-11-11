package com.diary.service;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.service.impl.BaseManagerMockTestCase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.diary.dao.DailyRecordDao;
import com.diary.model.DailyRecord;

public class DailyRecordManagerImplTest extends BaseManagerMockTestCase {
	
	@InjectMocks
	private DRManagerImpl dailyRecordManager;
	
	@Mock
	private DailyRecordDao dailyRecordDao;
	
	@Test
	public void testGetPlan() {
		final Long id = 1L;
		final DailyRecord dr = new DailyRecord();
		given(dailyRecordDao.get(id)).willReturn(dr);
		
		DailyRecord result = dailyRecordManager.get(id);
		
		assertSame(dr,result);
	}
	
	@Test
	public void testGetDailyRecordsFrom() {
		//there are daily records for member with ID=1L
		final List<DailyRecord> drs = new ArrayList<DailyRecord>();
		final Date from = Date.valueOf("2015-1-1");
		final long memberID = 1L;
		
		//given
		given(dailyRecordDao.getDailyRecordsFrom(from,memberID)).willReturn(drs);
		
		//when
		List<DailyRecord> result = dailyRecordManager.getDailyRecordsFrom(from, memberID);
		
		//then
		assertSame(drs, result);
	}
	
	@Test
	public void testGetTodayDailyRecord() {
		log.debug("test getTodayDailyRecord..");
		
		//there is no member with id=-5L so it should return null
		//given
		given(dailyRecordDao.getTodayDailyRecord(-5l)).willReturn(null);
		
		DailyRecord res = dailyRecordManager.getTodayDailyRecord(-5L);
		
		assertSame(null, res);
		
		//there is member with ID=-2L with no daily record for today
		//so the method should return new daily record for today
		long memberID = -2L;
		final DailyRecord dr = new DailyRecord();
		dr.setMemberID(memberID);
		
		//given 
		given(dailyRecordDao.getTodayDailyRecord(memberID)).willReturn(dr);
		
		//when
		DailyRecord result = dailyRecordManager.getTodayDailyRecord(memberID);
		
		//then
		assertSame(dr,result);
				
	}
}
