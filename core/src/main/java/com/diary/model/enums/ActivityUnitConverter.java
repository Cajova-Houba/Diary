package com.diary.model.enums;

import javax.persistence.AttributeConverter;

public class ActivityUnitConverter implements
		AttributeConverter<ActivityUnit, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ActivityUnit arg0) {
		// TODO Auto-generated method stub
		return arg0.getId();
	}

	@Override
	public ActivityUnit convertToEntityAttribute(Integer arg0) {
		// TODO Auto-generated method stub
		return ActivityUnit.getByID(arg0);
	}

}
