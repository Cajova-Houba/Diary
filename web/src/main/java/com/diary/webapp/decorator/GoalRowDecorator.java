package com.diary.webapp.decorator;

import org.displaytag.decorator.TableDecorator;

import com.diary.model.Goal;

/**
 * This decorator will make every line clickable in /pladetail goal table
 * @author Zdenda
 *
 */
public class GoalRowDecorator extends TableDecorator {

	@Override
	public String addRowClass() {
		// TODO Auto-generated method stub
		//the parameter passed to the function will be same as displayed goal's id
		//exact pattern of id is "roww%d" where %d is goal ID
		Goal g = ((Goal)getCurrentRowObject());
		//a little injection
		return "\" onClick=\"onRowClick('row"+g.getID()+"')";
	}
	
	/**
	 * Adds id tag. The format is row+goalID.
	 */
	@Override
	public String addRowId() {
		// TODO Auto-generated method stub
		Goal g = (Goal)getCurrentRowObject();
		return "row"+g.getID();
	}
}
