package com.diary.service;

import java.util.List;
import java.util.Set;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.dao.ActivityDao;
import com.diary.model.Activity;
import com.diary.model.Goal;

@Service("aManager")
public class ActivityManagerImpl extends GenericManagerImpl<Activity, Long>
		implements ActivityManager {

	private ActivityDao aDao;
	
	@Autowired
	public ActivityManagerImpl(ActivityDao aDao) {
		// TODO Auto-generated constructor stub
		super(aDao);
		this.aDao = aDao;
	}
	
//	@Override
//	public List<Activity> getActivitesForDailyRecord(long dailyRecordID) {
//		// TODO Auto-generated method stub
//		return aDao.getActivitesForDailyRecord(dailyRecordID);
//	}

//	@Override
//	public Activity addActivity(Activity activity) {
//		// TODO Auto-generated method stub
//		return aDao.addActivity(activity);
//	}

	@Override
	public Set<Goal> getGoalsForActivity(Activity activity) {
		// TODO Auto-generated method stub
		return aDao.getGoalsForActivity(activity);
	}

	@Override
	public void deleteGoalsForActivity(Activity activity) {
		// TODO Auto-generated method stub
		aDao.deleteGoalsForActivity(activity);
	}
	
	@Override
	public void remove(Activity object) {
		// TODO Auto-generated method stub
		aDao.remove(object);
	}
	
	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		aDao.remove(id);
	}

}
