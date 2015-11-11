package com.diary.model;

import org.appfuse.model.BaseObject;

/**
 * This class serves as base class for every entity. For now its only purpose is to be assured, that
 * every entity class has ID.
 * @author Zdenda
 *
 */
public class BaseEntityObject extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Unique ID in database.
	 */
	protected Long ID;
	
	/**
	 * Work name or another text representation of the object.
	 */
	protected String name;
	
	public Long getID() {
		return ID;
	}
	
	public void setID(long id) {
		this.ID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	/**
	 * Returns the "ID=%d,name=%s" formated string. 
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("ID=%d, name=%s", this.getID(),this.getName());
	}

}
