package de.rentoudu.mensa.fragment;

import java.io.Serializable;

import com.appspot.mensa_furtwangen.thumbs.model.Thumb;
import com.appspot.mensa_furtwangen.thumbs.model.ThumbsQuery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import de.rentoudu.mensa.R;
import de.rentoudu.mensa.Utils;
import de.rentoudu.mensa.task.ThumbInsertTask;
import de.rentoudu.mensa.task.ThumbRemoveTask;
import de.rentoudu.mensa.task.ThumbsFetchTask;

/**
 * This fragments represents the rating of a menu.
 */
public class RatingFragment extends Fragment implements OnClickListener {

	private ThumbState currentState;
	private String menuId;
	
	@Override	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rating, container, false);
		TextView thumbUpView = (TextView) view.findViewById(R.id.rating_thumb_up);
		TextView thumbDownView = (TextView) view.findViewById(R.id.rating_thumb_down);
		thumbUpView.setOnClickListener(this);
		thumbDownView.setOnClickListener(this);
		
		menuId = getArguments().getString("menuId");

		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// Start asynchronous rating fetch task.
		new ThumbsFetchTask(this).execute(menuId, Utils.getDeviceId());
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
	public void setThumbCount(ThumbState thumbState, int count) {
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
		
		// Creating thumb model for interaction with service.
		// Also note:
		// There is no need to remove old thumb states on server side
		// (getting done automatically after insert)
		Thumb thumb = new Thumb();
		thumb.setDeviceId(Utils.getDeviceId());
		thumb.setMenuId(menuId);
		thumb.setState(newState.toString());
		
		ThumbsQuery thumbsQuery = new ThumbsQuery();
		thumbsQuery.setDeviceId(Utils.getDeviceId());
		thumbsQuery.setMenuId(menuId);
		
		// Remove thumbs (from up)
		if(ThumbState.UP.equals(newState) && ThumbState.UP.equals(currentState)) {
			thumbUpView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good, 0, 0, 0);
			setThumbCount(ThumbState.UP, getThumbCount(ThumbState.UP) - 1);
			currentState = null;
			new ThumbRemoveTask().execute(thumbsQuery);
			
		// Thumb up and may remove thumb down
		} else if (ThumbState.UP.equals(newState)) {
			thumbUpView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good_green, 0, 0, 0);
			thumbDownView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad, 0, 0, 0);
			setThumbCount(ThumbState.UP, getThumbCount(ThumbState.UP) + 1);
			if(ThumbState.DOWN.equals(currentState)) {
				setThumbCount(ThumbState.DOWN, getThumbCount(ThumbState.DOWN) - 1);
			}
			currentState = ThumbState.UP;
			new ThumbInsertTask().execute(thumb);
		
		// Remove thumbs (from down)
		} else if (ThumbState.DOWN.equals(newState)  && ThumbState.DOWN.equals(currentState)) {
			thumbDownView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad, 0, 0, 0);
			setThumbCount(ThumbState.DOWN, getThumbCount(ThumbState.DOWN) - 1);
			currentState = null;
			new ThumbRemoveTask().execute(thumbsQuery);
			
		// Thumb down and may remove thumb up
		} else if (ThumbState.DOWN.equals(newState)) {
			thumbUpView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good, 0, 0, 0);
			thumbDownView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad_red, 0, 0, 0);
			setThumbCount(ThumbState.DOWN, getThumbCount(ThumbState.DOWN) + 1);
			if(ThumbState.UP.equals(currentState)) {
				setThumbCount(ThumbState.UP, getThumbCount(ThumbState.UP) - 1);
			}
			currentState = ThumbState.DOWN;
			new ThumbInsertTask().execute(thumb);
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
	public void setThumb(ThumbState newState) {
		if(ThumbState.UP.equals(newState)) {
			getThumbUp().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_good_green, 0, 0, 0);
		} else if(ThumbState.DOWN.equals(newState)) {
			getThumbDown().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_bad_red, 0, 0, 0);
		}
		currentState = newState;
	}
	
	public enum ThumbState implements Serializable {
		UP, DOWN
	}

}
