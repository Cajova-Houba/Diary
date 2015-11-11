package com.diary.model;

import javax.persistence.Convert;

import com.diary.model.enums.ActivityType;
import com.diary.model.enums.ActivityUnit;

/**
 * This class serves as base class for all activity-related classes (Activity, Goal..).
 * @author Zdenda
 *
 */
public class BaseActivityObject extends BaseEntityObject {
	
	@Convert(converter=ActivityType.class)
	protected ActivityType actType;
	
	@Convert(converter=ActivityUnit.class)
	protected ActivityUnit unit;

	/**
	 * Value of activity, combined with the type and unit it makes the whole activity. For example: running 2 km and so on.
	 */
	protected double value;
	
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
	
	@Override
	/**
	 * Returns the "ID=%d,name=%s" formated string. 
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
