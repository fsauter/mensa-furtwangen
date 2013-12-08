package de.rentoudu.mensa.fragment;

import java.io.Serializable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import de.rentoudu.mensa.R;

/**
 * This fragments represents the rating of a menu.
 */
public class RatingFragment extends Fragment implements OnClickListener {

	private ThumbState currentState;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_rating, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		TextView thumbUpView = (TextView) view.findViewById(R.id.rating_thumb_up);
		TextView thumbDownView = (TextView) view.findViewById(R.id.rating_thumb_down);
		thumbUpView.setOnClickListener(this);
		thumbDownView.setOnClickListener(this);
		
		setThumb((ThumbState) getArguments().get("state"));
		setThumbCount(ThumbState.UP, getArguments().getInt("up"));
		setThumbCount(ThumbState.DOWN, getArguments().getInt("down"));
	}

	@Override
	public void onClick(View v) {
		if(v.equals(getView().findViewById(R.id.rating_thumb_up))) {
			updateThumb(ThumbState.UP);
		} else if (v.equals(getView().findViewById(R.id.rating_thumb_down))) {
			updateThumb(ThumbState.DOWN);
		} else {
			// Nothing to do.
		}
	}
	
	private TextView getThumbUp() {
		return (TextView) getView().findViewById(R.id.rating_thumb_up);
	}
	
	private TextView getThumbDown() {
		return (TextView) getView().findViewById(R.id.rating_thumb_down);
	}
	
	/**
	 * Sets the count for a given thumb.
	 * 
	 * @param thumbState
	 * @param count
	 */
	private void setThumbCount(ThumbState thumbState, int count) {
		if(ThumbState.UP.equals(thumbState)) {
			TextView thumbUpView = getThumbUp();
			thumbUpView.setText(String.valueOf(count));
		} else if (ThumbState.DOWN.equals(thumbState)) {
			TextView thumbDownView = getThumbDown();
			thumbDownView.setText(String.valueOf(count));
		} else {
			// Nothing to do.
		}
	}
	
	/**
	 * Returns the thumb count for a given state.
	 * 
	 * @param thumbState
	 * 
	 * @return the determined count
	 */
	private int getThumbCount(ThumbState thumbState) {
		if(ThumbState.UP.equals(thumbState)) {
			return Integer.parseInt(getThumbUp().getText().toString());
		} else if (ThumbState.DOWN.equals(thumbState)) {
			return Integer.parseInt(getThumbDown().getText().toString());
		} else {
			return 0;
		}
	}
	
	/**
	 * Sets the correct thumb icon for a given state.
	 * 
	 * @param thumb pass null to reset thumbs
	 */
	private void updateThumb(ThumbState newState) {
		TextView thumbUpView = getThumbUp();
		TextView thumbDownView = getThumbDown();
		
		// Disable interaction while rating
		thumbUpView.setOnClickListener(null);
		thumbDownView.setOnClickListener(null);
		
		if(ThumbState.UP.equals(newState) && ThumbState.UP.equals(currentState)) {
			// Already thumbed up - remove thumb up
			thumbUpView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good, 0, 0, 0);
			setThumbCount(ThumbState.UP, getThumbCount(ThumbState.UP) - 1);
			currentState = null;
		} else if (ThumbState.UP.equals(newState)) {
			// Set thumb up and may remove down state
			thumbUpView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good_green, 0, 0, 0);
			thumbDownView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad, 0, 0, 0);
			setThumbCount(ThumbState.UP, getThumbCount(ThumbState.UP) + 1);
			if(ThumbState.DOWN.equals(currentState)) {
				setThumbCount(ThumbState.DOWN, getThumbCount(ThumbState.DOWN) - 1);
			}
			currentState = ThumbState.UP;
		} else if (ThumbState.DOWN.equals(newState)  && ThumbState.DOWN.equals(currentState)) {
			// Already thumbed down - remove thumb down
			thumbDownView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad, 0, 0, 0);
			setThumbCount(ThumbState.DOWN, getThumbCount(ThumbState.DOWN) - 1);
			currentState = null;
		} else if (ThumbState.DOWN.equals(newState)) {
			// Set thumb down and may remove up state
			thumbUpView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good, 0, 0, 0);
			thumbDownView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad_red, 0, 0, 0);
			setThumbCount(ThumbState.DOWN, getThumbCount(ThumbState.DOWN) + 1);
			if(ThumbState.UP.equals(currentState)) {
				setThumbCount(ThumbState.UP, getThumbCount(ThumbState.UP) - 1);
			}
			currentState = ThumbState.DOWN;
		} else {
			// Nothing to do.
		}
		
		// Enable interaction after rating
		thumbUpView.setOnClickListener(this);
		thumbDownView.setOnClickListener(this);
	}
	
	/**
	 * Sets a new state and ignores counting (useful for initializing).
	 * 
	 * @param newState or null for no state.
	 */
	private void setThumb(ThumbState newState) {
		if(ThumbState.UP.equals(newState)) {
			getThumbUp().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good_green, 0, 0, 0);
		} else if(ThumbState.DOWN.equals(newState)) {
			getThumbDown().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad_red, 0, 0, 0);
		}
		currentState = newState;
	}
	
	public static RatingFragment fromRating(ThumbState thumbState, int thumbUpCount, int thumpDownCount) {
		 RatingFragment ratingFragment = new RatingFragment();
		 Bundle bundle = new Bundle();
		 bundle.putSerializable("state", thumbState);
		 bundle.putInt("up", thumbUpCount);
		 bundle.putInt("down", thumpDownCount);
		 ratingFragment.setArguments(bundle);
		 return ratingFragment;
	}
	
	public enum ThumbState implements Serializable {
		UP, DOWN
	}

}
