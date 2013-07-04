package de.rentoudu.mensa;

import java.io.InputStream;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import de.rentoudu.mensa.model.Diet;
import de.rentoudu.mensa.task.DownloadRssTask;

/**
 * The main activity.
 * 
 * @author Florian Sauter
 */
public class MainActivity extends FragmentActivity {

	private static final String FEED_URL = "http://www.swfr.de/essen-trinken/speiseplaene/speiseplan-rss/?no_cache=1&Tag={day}&Ort_ID=641";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections.
	 */
	private DayPagerAdapter dayPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager viewPager;

	/**
	 * The fetched activity diet.
	 */
	private Diet diet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		dayPagerAdapter = new DayPagerAdapter(getResources(), getSupportFragmentManager());
		
		// Check if stored diet null.
		if (diet == null) {
			diet = fetchDiet();
		}

		// Check if fetched diet null.
		if (diet == null) {
			showToast(getString(R.string.error_diet_fetch));
		} else {
			dayPagerAdapter.setDiet(diet);
			// Create the adapter that will return a DayFragment for each of the
			// pages.
			// Set up the ViewPager with the sections adapter.
			viewPager.setAdapter(dayPagerAdapter);
			viewPager.setCurrentItem(getCurrentDayIndex());
		}
	}

	/**
	 * Returns the current day index.
	 */
	protected int getCurrentDayIndex() {
		// by default -2 because an we want to start counting with 0
		int dateBalancer = -2;
		// Have a look at the closing date.
		if (Utils.isAfter(13, 40)) {
			dateBalancer = -1;
		}
		return Utils.getDay() + dateBalancer;
	}

	/**
	 * Refreshes the view.
	 */
	protected void refreshView() {
		if(diet != null) {
			dayPagerAdapter.setDiet(diet);
			viewPager.setAdapter(dayPagerAdapter);
			viewPager.setCurrentItem(getCurrentDayIndex());
		}
	}

	/**
	 * Builds the feed URL to fetch based on the current weekday.
	 */
	protected String buildFeedUrl(int startDay) {
		return FEED_URL.replace("{day}", String.valueOf(startDay));
	}

	/**
	 * Loads the remote RSS file and parses the current diet.
	 * 
	 * @see DownloadRssTask#parseRss(InputStream)
	 */
	protected Diet fetchDiet() {
		try {
			String firstWeekFeedUrl = buildFeedUrl(-getCurrentDayIndex());
			String secondWeekFeedUrl = buildFeedUrl(-getCurrentDayIndex() + 7);
			DownloadRssTask task = new DownloadRssTask();
			Diet diet = task.execute(firstWeekFeedUrl, secondWeekFeedUrl).get();
			return diet;
		} catch (Exception e) {
			showToast(e.getMessage());
			return new Diet();
		}
	}

	/**
	 * Updates the saved/display diet using the SWFR RSS.
	 * 
	 * @see DownloadRssTask#parseRss(InputStream)
	 */
	protected void updateDiet() {
		Diet fetchedDiet = fetchDiet();
		if (fetchedDiet == null) {
			showToast(getString(R.string.error_diet_fetch));
		} else {
			diet = fetchedDiet;
			refreshView();
			showToast(getString(R.string.text_diet_synced));
		}

	}

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_sync:
			updateDiet();
			return true;
		case R.id.menu_about:
			String version = "-";
			try {
				PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				version = packageInfo.versionName;
			} catch (NameNotFoundException e) {
				// Nothing to do.
			}
			String message = String.format(getString(R.string.text_about), version);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(message)
				.setTitle(R.string.menu_about)
				.setNeutralButton(android.R.string.ok,
		            new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
				}).create().show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Shows a toast notification.
	 */
	public void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	protected void log(String message) {
		// Log.v(getClass().getSimpleName(), message);
	}
}
