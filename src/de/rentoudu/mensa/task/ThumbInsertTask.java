package de.rentoudu.mensa.task;

import android.os.AsyncTask;

import com.appspot.mensa_furtwangen.thumbs.Thumbs;
import com.appspot.mensa_furtwangen.thumbs.model.Thumb;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class ThumbInsertTask extends AsyncTask<Thumb, Void, Void> {

	@Override
	protected Void doInBackground(Thumb... params) {
		Thumbs.Builder thumbsBuilder = new Thumbs.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		Thumbs ratingService = thumbsBuilder.build();
		try {
			ratingService.insertThumb(params[0]).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
