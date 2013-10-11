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
    	
    	// Ratings bars.
    	RatingBar menuOneRatingBar = (RatingBar) view.findViewById(R.id.menuOne_ratingBar);
    	RatingBar menuTwoRatingBar = (RatingBar) view.findViewById(R.id.menuTwo_ratingBar);
    	
    	Day day = (Day) getArguments().get("day");
    	Menu menuOne = day.getMenuOne();
    	Menu menuTwo = day.getMenuTwo();
    	
    	if(menuOne.getMainCourse() == "") {
    		menuOneAppetizer.setText("");
    		menuOnemainCourse.setText(getString(R.string.text_menu_no_offer));
        	menuOneSideDish.setText("");
        	menuOneRatingBar.setVisibility(View.INVISIBLE);
    	} else {
    		// Menu one
    		menuOneAppetizer.setText(menuOne.getAppetizer());
    		menuOnemainCourse.setText(menuOne.getMainCourse());
        	menuOneSideDish.setText(menuOne.getSideDish());
    		// Rating bar menu one
        	menuOneRatingBarController = new RatingBarController(this, menuOneRatingBar, menuOne);
        	menuOneRatingBar.setOnTouchListener(menuOneRatingBarController);
        	menuOneRatingBarController.refresh();
    	}
    	
    	if(menuTwo.getMainCourse() == "") {
    		menuTwoAppetizer.setText("");
    		menuTwomainCourse.setText(getString(R.string.text_menu_no_offer));
    		menuTwoSideDish.setText("");
    		menuTwoRatingBar.setVisibility(View.INVISIBLE);
    	} else {
    		// Menu two
    		menuTwoAppetizer.setText(menuTwo.getAppetizer());
    		menuTwomainCourse.setText(menuTwo.getMainCourse());
        	menuTwoSideDish.setText(menuTwo.getSideDish());
    		// Rating bar menu two
        	menuTwoRatingBarController = new RatingBarController(this, menuTwoRatingBar, menuTwo);
        	menuTwoRatingBar.setOnTouchListener(menuTwoRatingBarController);
        	menuTwoRatingBarController.refresh();
    	}
    	
    	notes.setText(day.getNotes());
    	
        return view;
    }
    
}