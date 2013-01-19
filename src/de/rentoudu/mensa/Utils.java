package de.rentoudu.mensa;

import java.util.Calendar;

public class Utils {

	public static int getDay() {
		return getCalendar().get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getWeek() {
		return getCalendar().get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int getYear() {
		return getCalendar().get(Calendar.YEAR);
	}
	
	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
    	calendar.setFirstDayOfWeek(Calendar.MONDAY);
    	return calendar;
	}
	
}
