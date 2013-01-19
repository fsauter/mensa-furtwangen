package de.rentoudu.mensa;

import java.util.Calendar;
import java.util.Locale;

import de.rentoudu.mensa.model.Day;
import de.rentoudu.mensa.model.Diet;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DayPagerAdapter extends FragmentPagerAdapter {

	private Resources resources;
	private Diet diet;
	
    public DayPagerAdapter(Diet diet, Resources resources, FragmentManager fm) {
        super(fm);
        this.diet = diet;
        this.resources = resources;
    }
    
    public void setDiet(Diet diet) {
		this.diet = diet;
	}

	@Override
    public Fragment getItem(int position) {
    	DayFragment fragment = new DayFragment();
    	Day day = diet.getDays().get(position);
    	Bundle args = new Bundle();
    	args.putSerializable("day", day);
    	fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return diet.getDays().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	Day day = diet.getDays().get(position);
    	int week =  day.getWeek();
    	switch (day.getDay()) {
            case Calendar.MONDAY:
                return resources.getString(R.string.weekday_mo).toUpperCase(Locale.GERMAN) + " (" + week + ")";
            case Calendar.TUESDAY:
            	return resources.getString(R.string.weekday_tu).toUpperCase(Locale.GERMAN) + " (" + week + ")";
            case Calendar.WEDNESDAY:
            	return resources.getString(R.string.weekday_we).toUpperCase(Locale.GERMAN) + " (" + week + ")";
            case Calendar.THURSDAY:
            	return resources.getString(R.string.weekday_th).toUpperCase(Locale.GERMAN) + " (" + week + ")";
            case Calendar.FRIDAY:
            	return resources.getString(R.string.weekday_fr).toUpperCase(Locale.GERMAN) + " (" + week + ")";
        }
        return null;
    }
}