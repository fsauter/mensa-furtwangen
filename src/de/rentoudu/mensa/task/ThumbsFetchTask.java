package de.rentoudu.mensa.task;

import java.io.IOException;

import com.appspot.mensa_furtwangen.thumbs.Thumbs;
import com.appspot.mensa_furtwangen.thumbs.model.ThumbsQuery;
import com.appspot.mensa_furtwangen.thumbs.model.ThumbsResult;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.os.AsyncTask;
import de.rentoudu.mensa.fragment.RatingFragment;
import de.rentoudu.mensa.fragment.RatingFragment.ThumbState;

/**
 * This task fetches the total rating count for a specific menu.
 */
public class ThumbsFetchTask extends AsyncTask<String, Void, ThumbsResult> {

	private RatingFragment ratingFragment;
	
	public ThumbsFetchTask(RatingFragment ratingFragment) {
		this.ratingFragment = ratingFragment;
	}

	@Override
	protected ThumbsResult doInBackground(String... params) {
		Thumbs.Builder thumbsBuilder = new Thumbs.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		Thumbs thumbsService = thumbsBuilder.build();
		
		ThumbsQuery query = new ThumbsQuery();
		query.setMenuId(params[0]);
		query.setDeviceId(params[1]);
		
		try {
			return thumbsService.fetchThumbs(query).execute();
		} catch (IOException e) {
			return new ThumbsResult();
		}
	}
	
	@Override
	protected void onPostExecute(ThumbsResult result) {
		if(ThumbState.UP.toString().equals(result.getDeviceState())) {
			ratingFragment.setThumb(ThumbState.UP);
		} else if (ThumbState.DOWN.toString().equals(result.getDeviceState())) {
			ratingFragment.setThumb(ThumbState.DOWN);
		}
		ratingFragment.setThumbCount(ThumbState.UP, result.getThumbsUp());
		ratingFragment.setThumbCount(ThumbState.DOWN, result.getThumbsDown());
	}

}
