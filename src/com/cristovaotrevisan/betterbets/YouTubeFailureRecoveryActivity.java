package com.cristovaotrevisan.betterbets;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import android.content.Intent;
import android.widget.Toast;

/**
 * An abstract activity which deals with recovering from errors which may occur during API
 * initialization, but can be corrected through user action.
 */
public abstract class YouTubeFailureRecoveryActivity extends YouTubeBaseActivity implements
    YouTubePlayer.OnInitializedListener {
	protected static String DEVELOPER_KEY = "AIzaSyC7cM9o50MYiF1jxIhumzC8iUS3BVJ4Jrs";
	private static final int RECOVERY_DIALOG_REQUEST = 1;

	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider,
	    YouTubeInitializationResult errorReason) {
	  if (errorReason.isUserRecoverableError()) {
	    errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
	  } else {
	    String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
	    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	  }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (requestCode == RECOVERY_DIALOG_REQUEST) {
	    // Retry initialization if user performed a recovery action
	    getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
	  }
	}
	
	protected abstract YouTubePlayer.Provider getYouTubePlayerProvider();

}