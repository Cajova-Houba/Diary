package com.diary.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum ActivityUnit {

	//casual units
	m(0,1),
	km(1,1000),
	cm(2,0.01),
	step(3,1),
	
	//not so casual units
	test(-1,1),
	unknown(-2,1);
	
	private int id;
	
	/**
	 * The k coefficient so that value*k = value in default unit.
	 * The k of default unit is always 1.
	 */
	private double k;
	
	private ActivityUnit(int id, double k) {
		this.id = id;
		this.k = k;
	}
	
	public int getId() {
		return this.id;
	}
	
	/**
	 * The k coefficient so that value*k = value in default unit.
	 * The k of default unit is always 1.
	 */
	public double getK() {
		return this.k;
	}
	
	public static ActivityUnit getByID(int id) {
		for(ActivityUnit au : ActivityUnit.values()) {
			if(au.getId() == id) {
				return au;
			}
		}
		
		return ActivityUnit.unknown;
	}
	
	public static Map<Integer, String> getMap() {
		Map<Integer, String> map = new HashMap<Integer,String>();
		
		for(ActivityUnit au : ActivityUnit.values()) {
			map.put(au.getId(), au.toString());
		}
		
		return map;
	}
}
