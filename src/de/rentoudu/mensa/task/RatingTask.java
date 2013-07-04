package de.rentoudu.mensa.task;

import android.os.AsyncTask;
import de.rentoudu.mensa.RatingBarController;

public abstract class RatingTask<P, C ,R> extends AsyncTask<P, C, R>{

	private RatingBarController activeController;
	
	public RatingTask(RatingBarController controller) {
		this.activeController = controller;
	}
	
	public RatingBarController getActiveController() {
		return activeController;
	}
	
}
