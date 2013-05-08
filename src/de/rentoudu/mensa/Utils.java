package de.rentoudu.mensa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
	
	public static String getFormattedDate(String unformattedDate) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		SimpleDateFormat simpleDateFormat = (SimpleDateFormat) dateFormat;
		Date date;
		try {
			date = new SimpleDateFormat("E, d.M.y", Locale.GERMANY).parse(unformattedDate);
		} catch (Exception e) {
			date = new Date();
		}
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}
	
	public static boolean isAfter(int hour, int minute) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		Date closingDate = calendar.getTime();
		Date currentDate = new Date();
		return currentDate.after(closingDate);
	}
	
	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
    	calendar.setFirstDayOfWeek(Calendar.MONDAY);
    	return calendar;
	}
	
}
