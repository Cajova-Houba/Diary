package com.diary.webapp.decorator;

import org.displaytag.decorator.TableDecorator;

import com.diary.model.Activity;

/**
 * This decorator will make every line clickable in /dr activity table
 * @author Zdenda
 *
 */
public class ActivityRowDecorator extends TableDecorator {

	@Override
	public String addRowClass() {
		// TODO Auto-generated method stub
		//the parameter passed to the function will be same as displayed activity's id
				//exact pattern of id is "roww%d" where %d is activity ID
		Activity a = (Activity)getCurrentRowObject();
		//a little injection
		return "\" onClick=\"onRowClick('row"+a.getID()+"')";
	}
	
	@Override
	public String addRowId() {
		// TODO Auto-generated method stub
		Activity a = (Activity)getCurrentRowObject();
		return "row"+a.getID();
	}
}
