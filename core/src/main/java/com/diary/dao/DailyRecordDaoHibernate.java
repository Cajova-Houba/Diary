package com.diary.dao;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.diary.model.Activity;
import com.diary.model.DailyRecord;

@Repository("dailyRecordDao")
public class DailyRecordDaoHibernate extends GenericDaoHibernate<DailyRecord, Long>
		implements DailyRecordDao {

	public DailyRecordDaoHibernate() {
		super(DailyRecord.class);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Return a list of all daily records from a certain date (inclusive).
	 * @param date Date from which daily records will be listed.
	 * @return List of daily records.
	 */
	@Override
	public List<DailyRecord> getDailyRecordsFrom(Date date, Long memberID) {
		// TODO Auto-generated method stub
		return  getSession().createCriteria(DailyRecord.class).add(Restrictions.ge("date", date))
				                                              .add(Restrictions.eq("memberID", memberID))
				                                              .list();
	}
	
	/**
	 * Returns today daily record. If there's no record for today in database, method will create new one and return it.
	 * @return Daily record for today.
	 */
	@Override
	public DailyRecord getTodayDailyRecord(Long memberID) {
		
		//try to find today record for member with id memberID
		List<DailyRecord> drs = getSession().createQuery("FROM DailyRecord WHERE DATE(date)=DATE(NOW()) AND memberID="+memberID).list();
		
		//if there is no record a new one will be created
		if(drs.isEmpty()) {
			DailyRecord dr = new DailyRecord();
			dr.setDate(new Date(Calendar.getInstance().getTime().getTime())); //set today's date
			dr.setMemberID(memberID);  //set memberID
			
			super.save(dr);
			
			//this is because the ID of new diary record is setted up by the database, so I need to get the newly added
			//daily record. This solution is not good, I will try to make it better overtime
			drs = getSession().createQuery("FROM DailyRecord WHERE DATE(date)=DATE(NOW()) AND memberID="+memberID).list();
			
			return drs.get(0);
		}
		
		return drs.get(0);
	}
	
	/**
	 * Checks if member can access daily record.
	 * @param memberID ID of member who is trying to access daily record.
	 * @param drID ID of daily record which is being accessed.
	 * @return True or false.
	 */
	@Override
	public boolean canAccess(long memberID, long drID) {
		List<DailyRecord> drs = getSession().createCriteria(DailyRecord.class).add(Restrictions.eq("ID", drID))
																			  .add(Restrictions.eq("memberID",memberID))
																			  .list();
		return (!drs.isEmpty());
	}

	@Override
	public Set<Activity> getActivitiesFor(long drID) {
		// TODO Auto-generated method stub
		return super.get(drID).getActivities();
	}

}
