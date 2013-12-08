package de.rentoudu.mensa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RatingBar;

import com.google.api.services.ratings.model.Rating;

import de.rentoudu.mensa.model.Menu;
import de.rentoudu.mensa.task.RatingFetchTask;
import de.rentoudu.mensa.task.RatingInsertTask;

public class RatingBarController implements OnTouchListener {
	
	private Fragment activeFragment;
	private RatingBar activeRatingBar;
	private Menu activeMenu;
	
	public RatingBarController(Fragment fragment, RatingBar ratingBar, Menu menu) {
		this.activeFragment = fragment;
		this.activeRatingBar = ratingBar;
		this.activeMenu = menu;
	}
	
	/**
	 * Updates the view asynchrony.
	 */
	public void refresh() {
		new RatingFetchTask(this).execute();
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if(getFragmentManager().findFragmentByTag("RatingDialogFragment") == null) {
		}
		return true;
	}
	
	public Menu getActiveMenu() {
		return activeMenu;
	}
	
	public RatingBar getActiveRatingBar() {
		return activeRatingBar;
	}
	
	public FragmentActivity getActivity() {
		return activeFragment.getActivity();
	}
	
	public FragmentManager getFragmentManager() {
		return activeFragment.getFragmentManager();
	}
	
}