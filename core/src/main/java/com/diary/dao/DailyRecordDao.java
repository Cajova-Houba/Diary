package com.diary.dao;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.appfuse.dao.GenericDao;

import com.diary.model.Activity;
import com.diary.model.DailyRecord;

public interface DailyRecordDao extends GenericDao<DailyRecord, Long> {

	/**
	 * Returns a list of all daily records from a certain date (inclusive).
	 * @param date Date from which daily records will be listed.
	 * @param memberID ID of member requested daily record belongs to.
	 * @return List of daily records. The list will be empty if there are no daily records found.
	 */
	public List<DailyRecord> getDailyRecordsFrom(Date date, Long memberID);
	
	/**
	 * Returns today daily record. If there's no record for today in database, method will create new one and return it.
	 * @param memberID ID of member requested daily record belongs to.
	 * @return Daily record for today.
	 */
	public DailyRecord getTodayDailyRecord(Long memberID);
	
	/**
	 * Checks if member can access daily record.
	 * @param memberID ID of member who is trying to access daily record.
	 * @param drID ID of daily record which is being accessed.
	 * @return True or false.
	 */
	public boolean canAccess(long memberID, long drID);
	
	/**
	 * Returns a set of activities assigned to daily record.
	 * @param drID ID of daily record.
	 * @return Set of activities.
	 */
	public Set<Activity> getActivitiesFor(long drID);
}
