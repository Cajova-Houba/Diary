package com.diary.dao;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.appfuse.dao.SearchException;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.diary.model.Goal;
import com.diary.model.Plan;
import com.diary.model.enums.ActivityType;

@Repository("planDao")
public class PlanDaoHibernate extends GenericDaoHibernate<Plan, Long> implements
		PlanDao {

	public PlanDaoHibernate() {
		super(Plan.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Plan> getPlansFrom(Date date, long memberID) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Plan.class).add(Restrictions.ge("fromDate", date))
																.add(Restrictions.eq("memberID", memberID)).list();
	}

	@Override
	public List<Plan> getAllUnfinishedPlans(long memberID) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Plan.class).add(Restrictions.eq("memberID", memberID)).list();
	}

	@Override
	public boolean canAccess(long memberID, long planID) {
		// TODO Auto-generated method stub
		List<Plan> plans = getSession().createCriteria(Plan.class).add(Restrictions.eq("memberID", memberID))
															.add(Restrictions.eq("ID", planID)).list();
		
		//true is the list isn't empty (that means there is a plan in the database with planID and memberID)
		return (!plans.isEmpty());
	}

	@Override
	public Set<Goal> getGoalsFor(long planID) {
		// TODO Auto-generated method stub
		return get(planID).getGoals();
	}

	@Override
	public Set<Goal> getGoalsFor(long planID, ActivityType actType) {
		// TODO Auto-generated method stub
		List<Goal> list = getSession().createCriteria(Goal.class).add(Restrictions.eq("plan", get(planID)))
				                                                 .add(Restrictions.eq("actType",actType))
				                                                 .list();
		HashSet<Goal> res = new HashSet<>(0);
		
		for (Goal goal : list) {
			res.add(goal);
		}
		
		return res;
	}

}
