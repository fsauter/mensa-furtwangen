package de.rentoudu.mensa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import de.rentoudu.mensa.model.Day;
import de.rentoudu.mensa.model.Menu;

/**
 * The fragment representing both menus and the rating bar.
 */
public class DayFragment extends Fragment {

	private RatingBarController menuOneRatingBarController;
	private RatingBarController menuTwoRatingBarController;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_day, container, false);
    	
    	TextView menuOneAppetizer = (TextView) view.findViewById(R.id.menuOne_appetizer);
    	TextView menuOnemainCourse = (TextView) view.findViewById(R.id.menuOne_maincourse);
    	TextView menuOneSideDish = (TextView) view.findViewById(R.id.menuOne_sidedish);
    	
    	TextView menuTwoAppetizer = (TextView) view.findViewById(R.id.menuTwo_appetizer);
    	TextView menuTwomainCourse = (TextView) view.findViewById(R.id.menuTwo_maincourse);
    	TextView menuTwoSideDish = (TextView) view.findViewById(R.id.menuTwo_sidedish);
    	
    	TextView notes = (TextView) view.findViewById(R.id.notes);
    	
    	Day day = (Day) getArguments().get("day");
    	Menu menuOne = day.getMenuOne();
    	Menu menuTwo = day.getMenuTwo();
    	
    	menuOneAppetizer.setText(menuOne.getAppetizer());
    	menuOneSideDish.setText(menuOne.getSideDish());
    	if(menuOne.getMainCourse() == "") {
    		menuOnemainCourse.setText(getString(R.string.text_menu_no_offer));
    	} else {
    		menuOnemainCourse.setText(menuOne.getMainCourse());
    	}
    	
    	menuTwoAppetizer.setText(menuTwo.getAppetizer());
    	menuTwoSideDish.setText(menuTwo.getSideDish());
    	if(menuTwo.getMainCourse() == "") {
    		menuTwomainCourse.setText(getString(R.string.text_menu_no_offer));
    	} else {
    		menuTwomainCourse.setText(menuTwo.getMainCourse());
    	}
    	
    	notes.setText(day.getNotes());
    	
    	// Rating bar
    	RatingBar menuOneRatingBar = (RatingBar) view.findViewById(R.id.menuOne_ratingBar);
    	RatingBar menuTwoRatingBar = (RatingBar) view.findViewById(R.id.menuTwo_ratingBar);
    	
    	menuOneRatingBarController = new RatingBarController(this, menuOneRatingBar, menuOne);
    	menuTwoRatingBarController = new RatingBarController(this, menuTwoRatingBar, menuTwo);
    	
    	menuOneRatingBar.setOnTouchListener(menuOneRatingBarController);
    	menuTwoRatingBar.setOnTouchListener(menuTwoRatingBarController);
    	
    	menuOneRatingBarController.refresh();
    	menuTwoRatingBarController.refresh();
    	
        return view;
    }
    
}