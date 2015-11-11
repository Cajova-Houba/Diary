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
 * This class servers as model for plans. A plan is used to plan you sport activities.
 * @author Zdenda
 *
 */
@Entity
public class Plan extends BaseEntityObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The date when the plan has started.
	 */
	private Date fromDate;
	
	/**
	 * The id of a member this plan belongs to.
	 */
	private long memberID;
	
	/**
	 * Goals assigned to this plan.
	 */
	private Set<Goal> goals = new HashSet<>(0);
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getID() {
		// TODO Auto-generated method stub
		return super.getID();
	}
	
	/**
	 * A name of the plan.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * The date when the plan has started.
	 */
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	
	
	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="plan")
	public Set<Goal> getGoals() {
		return goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "The plan "+getName()+", started on "+getFromDate();
	}
}
