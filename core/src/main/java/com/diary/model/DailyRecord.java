package com.diary.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class servers as model for daily records. Daily record encapsulates all activities done on certain day.
 * @author Zdenda
 *
 */
@Entity
public class DailyRecord extends BaseEntityObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Date of the daily record.
	 */
	private Date date;
	
	/**
	 * ID of member this daily record belongs to.
	 */
	private Long memberID;
	
	/**
	 * Activities for daily record.
	 */
	private Set<Activity> activities = new HashSet<>(0);
	
	public DailyRecord() {
		super();
	}
	
	public DailyRecord(long id) {
		super();
		setID(id);
	}
	
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getID() {
		// TODO Auto-generated method stub
		return super.getID();
	}

	/**
	 * Date of the daily record.
	 */
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dailyRecord")
	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("[id=%d; date=%s; memberID=%d]", getID(),getDate().toString(),getMemberID());
	}
	
	
}
