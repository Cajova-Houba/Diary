package com.diary.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class ActivityTypeConverter implements
		AttributeConverter<ActivityType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ActivityType arg0) {
		// TODO Auto-generated method stub
		return arg0.getID();
	}

	@Override
	public ActivityType convertToEntityAttribute(Integer arg0) {
		// TODO Auto-generated method stub
		return ActivityType.getByID(arg0);
	}

}
