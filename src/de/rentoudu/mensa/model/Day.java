package de.rentoudu.mensa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Day implements Serializable {

	private int week;
	private int day;
	
	private String title;
	private String guid;
	
	private List<Menu> menus;
	
	private String notes;
	
	public Day() {
		this.menus = new ArrayList<Menu>();
	}
	
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
	
	public void addMenu(Menu menu) {
		menus.add(menu);
	}
	
	public List<Menu> getMenus() {
		return menus;
	}
	
	public boolean hasMenus() {
		return menus.isEmpty() == false;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
