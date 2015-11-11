package com.diary.webapp.decorator;

import org.displaytag.decorator.TableDecorator;

import com.diary.model.Plan;

/**
 * This decorator will make every row clickable in /plans table. 
 * 
 * @author Zdenda
 *
 */
public class PlanRowDecorator extends TableDecorator {
	
	@Override
	public String addRowClass() {
		// TODO Auto-generated method stub
		//the parameter passed to the function will be same as displayed plan's id
		//exact pattern of id is "roww%d" where %d is plan ID
		Plan p = ((Plan)getCurrentRowObject());
		//a little injection
		return "\" onClick=\"onRowClick('row"+p.getID()+"')";
	}
	
	/**
	 * Adds id tag. The format is row+planID.
	 */
	@Override
	public String addRowId() {
		// TODO Auto-generated method stub
		Plan p = ((Plan)getCurrentRowObject());
		return "row"+Long.toString(p.getID());
	}
}
