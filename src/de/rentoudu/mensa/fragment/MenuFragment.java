package de.rentoudu.mensa.fragment;

import de.rentoudu.mensa.R;
import de.rentoudu.mensa.fragment.RatingFragment.ThumbState;
import de.rentoudu.mensa.model.Menu;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This fragments represents menus of a day.
 */
public class MenuFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		
		TextView menuTitle = (TextView) view.findViewById(R.id.menu_title);
		TextView menuAppetizer = (TextView) view.findViewById(R.id.menu_appetizer);
    	TextView menumainCourse = (TextView) view.findViewById(R.id.menu_maincourse);
    	TextView menuSideDish = (TextView) view.findViewById(R.id.menu_sidedish);
    	
    	Menu menu = getMenu();
    	
		// Menu value
		menuTitle.setText(menu.getTitle());
		menuAppetizer.setText(menu.getAppetizer());
		menumainCourse.setText(menu.getMainCourse());
    	menuSideDish.setText(menu.getSideDish());
		
    	// Rating bar
    	if(savedInstanceState == null) {
    		RatingFragment ratingFragment = RatingFragment.fromRating(ThumbState.UP, 12, 5);
    		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    		transaction.add(R.id.menu_header_container, ratingFragment).commit();
    	}
    	
		return view;
	}
	
	public static MenuFragment fromMenu(Menu menu) {
		MenuFragment fragment = new MenuFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("menu", menu);
    	fragment.setArguments(args);
    	return fragment;
	}
	
	public Menu getMenu() {
		return (Menu) getArguments().get("menu");
	}
}
