package de.rentoudu.mensa.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Day implements Serializable {

	private int week;
	private int day;
	
	private Menu menuOne;
	private Menu menuTwo;
	
	private String notes;
	
	public Day() {}
	
	public void setWeek(int week) {
		this.week = week;
	}
	
	public int getWeek() {
		return week;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getDay() {
		return day;
	}
	
	public void setMenuOne(Menu menuOne) {
		this.menuOne = menuOne;
	}
	
	public void setMenuTwo(Menu menuTwo) {
		this.menuTwo = menuTwo;
	}
	
	public Menu getMenuOne() {
		return menuOne;
	}
	
	public Menu getMenuTwo() {
		return menuTwo;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
}
