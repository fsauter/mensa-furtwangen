package de.rentoudu.mensa.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Menu implements Serializable {
	
	public String appetizer;
	public String mainCourse;
	public String sideDish;
 
	public Menu() {}

	public String getAppetizer() {
		return appetizer;
	}

	public void setAppetizer(String appetizer) {
		this.appetizer = appetizer;
	}

	public String getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse = mainCourse;
	}

	public String getSideDish() {
		return sideDish;
	}

	public void setSideDish(String sideDish) {
		this.sideDish = sideDish;
	}
	
}
