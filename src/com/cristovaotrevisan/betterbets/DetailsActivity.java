package com.cristovaotrevisan.betterbets;

import com.google.android.youtube.player.*;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DetailsActivity extends YouTubeBaseActivity implements OnClickListener{
	protected static String DEVELOPER_KEY = "AIzaSyC7cM9o50MYiF1jxIhumzC8iUS3BVJ4Jrs";
	private Bet bet;
	private TextView userLikes, daredUserLikes;
	private YouTubePlayerFragment player1Fragment, player2Fragment;
	private YouTubePlayer player1=null, player2=null;
	public void setPlayer(YouTubePlayer player, boolean user){
		if(user) this.player1 = player;
		else  this.player2 = player;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details);
	    bet = getIntent().getExtras().getParcelable("Bet");
	    player1Fragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.details_user_video);
	    player1Fragment.initialize(DEVELOPER_KEY, new MyYouTubeListener(this, bet.getUserUrl(), true));
	    player2Fragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.details_dared_user_video);
	    player2Fragment.initialize(DEVELOPER_KEY, new MyYouTubeListener(this, bet.getDaredUserUrl(), false));
	    TextView description = (TextView) findViewById(R.id.details_user_description);
	    description.setText(bet.getDescription());
	    TextView prize = (TextView) findViewById(R.id.details_user_prize);
	    prize.setText(bet.getPrize());
	    userLikes = (TextView) findViewById(R.id.details_user_likes);
	    userLikes.setText("Your Views: " + Integer.toString(bet.getUserLikes()));
	    userLikes.setOnClickListener(this);
	    daredUserLikes = (TextView) findViewById(R.id.details_dared_user_likes);
	    String daredName = (bet.getDaredUserName().equals("Me")) ? "Also Your" : bet.getDaredUserName();
	    daredUserLikes.setText(daredName+" Views: "+Integer.toString(bet.getDaredUserLikes()));
	    daredUserLikes.setOnClickListener(this);
	    showPlayer(true);
	  }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_dared_user_likes:
			showPlayer(false);
			return;
		case R.id.details_user_likes:
			showPlayer(true);
			return;
		default:
			return;
		}
	}
	
	public void showPlayer(boolean user){
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction()
		          .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
		          .show(user? player1Fragment:player2Fragment)
		          .commit();
		if(player1!=null && !user) player1.pause();
		fm.beginTransaction()
	        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
	        .hide(user? player2Fragment:player1Fragment)
	        .commit();
		if(player2!=null && user) player2.pause();
	}
	

}
