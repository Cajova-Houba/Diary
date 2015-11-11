package com.diary.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;

/**
 * This class serves as a model for one activity. 
 * @author Zdenda
 *
 */
@Entity
public class Activity extends BaseActivityObject {


	private static final long serialVersionUID = 1L;
	
	/**
	 * A set of goals this activity is related to.
	 */
	private Set<Goal> goals = new HashSet<>(0);
	
	/**
	 * Daily record this activity belongs to.
	 */
	private DailyRecord dailyRecord;
	
	/*
	 * Getters and setters
	 */
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getID() {
		// TODO Auto-generated method stub
		return super.getID();
	}

	/**
	 * Name of the activity.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name of the activity.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public ActivityUnit getUnit() {
		return unit;
	}

	public void setUnit(ActivityUnit unit) {
		this.unit = unit;
	}

	public ActivityType getActType() {
		return actType;
	}

	public void setActType(ActivityType actType) {
		this.actType = actType;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@ManyToMany(mappedBy="activities",cascade={
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	public Set<Goal> getGoals() {
		return goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
	}
	
	public void removeGoals() {
		this.goals.clear();
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public DailyRecord getDailyRecord() {
		return dailyRecord;
	}

	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s: [ %s, ActType=%s, ActUnit=%s, value=%f ]", this.getClass().getName(),
																      		super.toString(),this.getActType().toString(),
																      		this.getUnit().toString(),this.getValue());
	}
	
	/**
	 * Returns the value of this goal in default units. Check the
	 * description of ActivityUnit.getK() to understand what does the
	 * default unit mean.
	 * @return
	 */
	public double defaultUnitValue() {
		return this.value * this.unit.getK();
	}
	
	
}
