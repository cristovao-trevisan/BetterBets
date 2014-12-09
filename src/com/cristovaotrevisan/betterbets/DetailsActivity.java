package com.cristovaotrevisan.betterbets;

import com.google.android.youtube.player.*;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends YouTubeFailureRecoveryActivity implements OnClickListener{
	protected static String DEVELOPER_KEY = "AIzaSyC7cM9o50MYiF1jxIhumzC8iUS3BVJ4Jrs";
	private Bet bet;
	private Button userLikes, daredUserLikes;
	private YouTubePlayer player;
	private boolean user = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details);
	    bet = getIntent().getExtras().getParcelable("Bet");
	    YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.details_video);
	    playerView.initialize(DEVELOPER_KEY, this);
	    TextView description = (TextView) findViewById(R.id.details_user_description);
	    description.setText(bet.getDescription());
	    TextView prize = (TextView) findViewById(R.id.details_user_prize);
	    prize.setText(bet.getPrize());
	    userLikes = (Button) findViewById(R.id.details_user_likes);
	    userLikes.setText("Your Views: " + Integer.toString(bet.getUserLikes()));
	    userLikes.setOnClickListener(this);
	    daredUserLikes = (Button) findViewById(R.id.details_dared_user_likes);
	    String daredName = (bet.getDaredUserName().equals("Me")) ? "Also Your" : bet.getDaredUserName();
	    daredUserLikes.setText(daredName+" Views: "+Integer.toString(bet.getDaredUserLikes()));
	    daredUserLikes.setOnClickListener(this);
	  }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_dared_user_likes:
			player.loadVideo(bet.getDaredUserUrl());
			return;
		case R.id.details_user_likes:
			player.loadVideo(bet.getUserUrl());
			return;
		default:
			return;
		}
	}
	
	@Override
	  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
	      boolean wasRestored) {
	    if (!wasRestored) {
	      player.cueVideo(bet.getUserUrl());
	      this.player = player;
	    }
	  }

	  @Override
	  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
	    return (YouTubePlayerView) findViewById(R.id.details_video);
	  }
}
