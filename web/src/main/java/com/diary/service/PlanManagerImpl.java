package com.diary.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diary.dao.PlanDao;
import com.diary.model.Goal;
import com.diary.model.Plan;
import com.diary.model.enums.ActivityType;

@Service("planManager")
public class PlanManagerImpl extends GenericManagerImpl<Plan, Long> implements PlanManager{

	private PlanDao planDao;
	
	@Autowired
	public PlanManagerImpl(PlanDao planDao) {
		super(planDao);
		this.planDao = planDao;
	}
	
	public List<Plan> getPlansFrom(Date date, long memberID) {
		return planDao.getPlansFrom(date, memberID);
	}

	@Override
	public List<Plan> getAllUnfinishedPlans(long memberID) {
		// TODO Auto-generated method stub
		return planDao.getAllUnfinishedPlans(memberID);
	}

	@Override
	public boolean canAccess(long memberID, long planID) {
		// TODO Auto-generated method stub
		return planDao.canAccess(memberID, planID);
	}

	@Override
	public Set<Goal> getGoalsFor(long planID) {
		// TODO Auto-generated method stub
		return planDao.getGoalsFor(planID);
	}

	@Override
	public Set<Goal> getGoalsFor(long planID, ActivityType actType) {
		// TODO Auto-generated method stub
		return planDao.getGoalsFor(planID, actType);
	}
}
