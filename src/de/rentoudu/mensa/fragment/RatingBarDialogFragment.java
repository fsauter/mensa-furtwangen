package de.rentoudu.mensa.fragment;

import de.rentoudu.mensa.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class RatingBarDialogFragment extends DialogFragment{

	private RatingBarDialogListener ratingBarDialogListener;
	private int selectedRating = 0;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		String[] ratingItems = new String[] { getString(R.string.rating_5),
				getString(R.string.rating_4), getString(R.string.rating_3),
				getString(R.string.rating_2), getString(R.string.rating_1) };
		
		builder.setTitle(getString(R.string.rating_header))
			.setSingleChoiceItems(ratingItems, -1, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which) {
						case 0:
							selectedRating = 5;
							break;
						case 1:
							selectedRating = 4;
							break;
						case 2:
							selectedRating = 3;
							break;
						case 3:
							selectedRating = 2;
							break;
						case 4:
							selectedRating = 1;
							break;
						default:
							selectedRating = 0;
							break;
					}
				}
			})
			.setPositiveButton(getString(R.string.text_ok), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ratingBarDialogListener.onDialogPositiveClick(getSelectedRating());
				}
				
			})
			.setNegativeButton(getString(R.string.text_cancel), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Nothing to do.
				}
			});
		
		
		return builder.create();
	}
	
	public void setRatingBarDialogListener(RatingBarDialogListener ratingBarDialogListener) {
		this.ratingBarDialogListener = ratingBarDialogListener;
	}
	
	public int getSelectedRating() {
		return selectedRating;
	}
	
	public interface RatingBarDialogListener {
        public void onDialogPositiveClick(int rating);
    }
}
