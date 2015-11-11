package com.diary.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.dao.DailyRecordDao;
import com.diary.model.Activity;
import com.diary.model.DailyRecord;


@Service("drm")
public class DRManagerImpl extends GenericManagerImpl<DailyRecord, Long> implements
		DRManager {

	private DailyRecordDao drDao;
	
	@Autowired
	public DRManagerImpl(DailyRecordDao dailyRecordDao) {
		super(dailyRecordDao);
		this.drDao = dailyRecordDao;
	}
	
	@Override
	public List<DailyRecord> getAll() {
		// TODO Auto-generated method stub
		return drDao.getAll();
	}
	
	@Override
	public List<DailyRecord> getDailyRecordsFrom(Date date, Long memberID) {
		// TODO Auto-generated method stub
		return drDao.getDailyRecordsFrom(date, memberID);
	}

	@Override
	public DailyRecord getTodayDailyRecord(Long memberID) {
		// TODO Auto-generated method stub
		return drDao.getTodayDailyRecord(memberID);
//		return null;
//		return new DailyRecord();
//		DailyRecord dr = new DailyRecord();
//		dr.setID(-5L);
//		return dr;
//		return drDao.getDailyRecord(memberID);
	}
	
	public boolean canAccess(long memberID, long drID) {
		return drDao.canAccess(memberID, drID);
	}

	@Override
	public Set<Activity> getActivitiesFor(long drID) {
		// TODO Auto-generated method stub
		return drDao.getActivitiesFor(drID);
	}

}
