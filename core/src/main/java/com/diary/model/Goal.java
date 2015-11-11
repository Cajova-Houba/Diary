package com.diary.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;

/**
 * This class represents the Goal entity. Each plan is made of a list of goals which need to be fulfilled in case of completing the plan.  
 * @author Zdenda
 *
 */
@Entity
public class Goal extends BaseActivityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Set of activities related to this goal.
	 */
	private Set<Activity> activities = new HashSet<>(0);
	
	/**
	 * ID of plan this goal belongs to.
	 */
//	private Long planID;
	
	/**
	 * Plan this goal is assigned to.
	 */
	private Plan plan;
	
	/**
	 * Indicates if goal is completed or not.
	 */
	private boolean completed;
	
	/**
	 * Value that indicates the progress of the goal. It's calculated
	 * as goals value divided by sum of values of assigned activities.
	 */	
	private double progress;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getID() {
		// TODO Auto-generated method stub
		return super.getID();
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}
	
//	public Long getPlanID() {
//		return planID;
//	}
//
//	public void setPlanID(Long planID) {
//		this.planID = planID;
//	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public ActivityType getActType() {
		return actType;
	}

	public void setActType(ActivityType actType) {
		this.actType = actType;
	}

	public ActivityUnit getUnit() {
		return unit;
	}

	public void setUnit(ActivityUnit unit) {
		this.unit = unit;
	}

	@ManyToMany(cascade={
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	@JoinTable(name="goal_activity",joinColumns={
			@JoinColumn(name="goalID",nullable=false,updatable=false)
			},inverseJoinColumns={
			@JoinColumn(name="activityID",nullable=false,updatable=false)
	})
	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}
	
	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Plan getPlan() {
		return plan;
	}
	
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	/**
	 * Returns the value of this goal in default units. Check the
	 * description of ActivityUnit.getK() to understand what does the
	 * default unit mean. If the value is 0, only the default unit is returned (1*defUnit).
	 * @return
	 */
	public double defaultUnitValue() {
		if (Math.abs(this.value - 0) < 0.001) {
			return this.unit.getK();
		}
		return this.value * this.unit.getK();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s: [ %s, ActType=%s, ActUnit=%s, value=%f ]", this.getClass().getName(),
																			 super.toString(),
																			 this.getActType().toString(),
														    			     this.getUnit().toString(),
														    			     this.getValue());
	}
	
	
}
