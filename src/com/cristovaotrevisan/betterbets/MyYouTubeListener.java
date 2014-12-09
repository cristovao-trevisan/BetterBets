package com.cristovaotrevisan.betterbets;

/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Activity;
import android.widget.Toast;

/**
 * An abstract activity which deals with recovering from errors which may occur during API
 * initialization, but can be corrected through user action.
 */
public class MyYouTubeListener implements YouTubePlayer.OnInitializedListener {
  protected static String DEVELOPER_KEY = "AIzaSyC7cM9o50MYiF1jxIhumzC8iUS3BVJ4Jrs";
  
  private static final int RECOVERY_DIALOG_REQUEST = 1;
  private Activity activity;
  private String url;
  private boolean user;
  
  public MyYouTubeListener(Activity activity, String url, boolean user){
	  this.activity = activity;
	  this.url = url;
	  this.user = user;
  }
  
  @Override
  public void onInitializationFailure(YouTubePlayer.Provider provider,
      YouTubeInitializationResult errorReason) {
    if (errorReason.isUserRecoverableError()) {
      errorReason.getErrorDialog(activity, RECOVERY_DIALOG_REQUEST).show();
    } else {
      String errorMessage = String.format(activity.getString(R.string.error_player), errorReason.toString());
      Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
    }
  }
  
  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
      boolean wasRestored) {
	  if(!wasRestored){
		  player.cueVideo(url);
		  ((DetailsActivity) activity).setPlayer(player, user);
	  }
  }
}