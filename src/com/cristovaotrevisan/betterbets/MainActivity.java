package com.cristovaotrevisan.betterbets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
	public static final int LOGIN = 0;
	public static final int MENU = 1;
	private static final int FRAGMENT_COUNT = MENU +1;
	private boolean isResumed = false;
	private boolean infoThreadRunning= false;
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	
	private static final String getBetsUrl = "http://10.0.2.2:3000/api1/users/bet_info.json";
	private static final String createBetUrl = "http://10.0.2.2:3000/api1/users/create_bet.json";
	private DefaultHttpClient httpclient;
	
	private String userID;
	private String userName;
	private ArrayList<String> friendsIDs;
	private Map<String, String> usersNames;
	private ArrayList<Bet> userBets;
	private int detailsBet = -1;
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = 
	    new Session.StatusCallback() {
	    @Override
	    public void call(Session session, 
	            SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    friendsIDs = new ArrayList<String>();
	    usersNames = new HashMap<String, String>();
	    userBets = new ArrayList<Bet>();
	    httpclient = new DefaultHttpClient();
	    
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_main);

	    FragmentManager fm = getSupportFragmentManager();
	    fragments[LOGIN] = fm.findFragmentById(R.id.login_fragment);
	    fragments[MENU] = fm.findFragmentById(R.id.menu_fragment);

	    FragmentTransaction transaction = fm.beginTransaction();
	    for(int i = 0; i < fragments.length; i++) {
	        transaction.hide(fragments[i]);
	    }
	    transaction.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		return false;
	}
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();

	    if (session != null && session.isOpened()) {
	        showFragment(MENU, false);
	    } else {
	        // otherwise present the LOGIN screen
	        // and ask the person to login.
	        showFragment(LOGIN, false);
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    public void showFragment(int fragmentIndex, boolean addToBackStack) {
    	if (fragmentIndex == MENU) {
    		((MenuFragment) fragments[MENU]).showLoading();
    		getUserInfo();
		}
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible
        if (isResumed) {
            FragmentManager manager = getSupportFragmentManager();
            // Get the number of entries in the back stack
            int backStackSize = manager.getBackStackEntryCount();
            // Clear the back stack
            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            if (state.isOpened()) {
                // If the session state is open:
                // Show the authenticated fragment
                showFragment(MENU, false);
            } else if (state.isClosed()) {
                // If the session state is closed:
                // Show the login fragment
                showFragment(LOGIN, false);
            }
        }
    }
    
    public void getUserInfo(){
    	if(infoThreadRunning) return;
    	Session session = Session.getActiveSession();
    	if(session.isClosed()) return;
    	infoThreadRunning = true;
    	Request request =  Request.newMeRequest(session, new GraphUserCallback() {   
            public void onCompleted(GraphUser user, Response response) {                                
                if (user != null) {
                    userID = user.getId();
                    userName = user.getName();
                    if(userName == null) userName = user.getFirstName();
                    if(userName == null) userName = "Me";
                    usersNames.put(userID, userName);
                    Log.i("DEBUG", userID);
                    try {
                    	friendsIDs.clear();
                    	JSONArray friendsJSON =  ((JSONObject)response.getGraphObject().getProperty("friends")).getJSONArray("data");
                    	for (int i=0; i< friendsJSON.length(); i++){
                    		String s_id = friendsJSON.getJSONObject(i).getString("id");
                    		friendsIDs.add(s_id);
                    		usersNames.put(s_id, friendsJSON.getJSONObject(i).getString("name"));
                    	}
                    	getUserBetsFromServer();
                    	Log.i("DEBUG", friendsIDs.toString());
                    	Log.i("DEBUG", usersNames.toString());
					} catch (JSONException e) {
						Log.i("DEBUG", e.toString());
					}
                }
            }
        });
    	Bundle params = new Bundle();
    	params.putString("fields", "id,friends");
        request.setParameters(params);
        request.executeAsync();
    	Log.i("DEBUG", session.getAccessToken());
    }
    
    public void callFacebookLogout() {
        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        }
    }
    
    private void getUserBetsFromServer() {
    	Thread thread = new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    	        try {
    	        	if (Session.getActiveSession().isOpened() && userID != null){
			       		URI uri = URI.create(getBetsUrl+"?user="+userID);
			       		HttpUriRequest request = new HttpGet(uri);
			       		request.setHeader("Content-type", "application/json");
		
			       		HttpResponse response = null;
			       		 
			       		try {
			       			response = httpclient.execute(request);
			       		} catch (Exception e) {
			       			 Log.i("DEBUG", e.toString());
			       		}
			       		
			       		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			       		StringBuilder builder = new StringBuilder();
			       		for (String line = null; (line = reader.readLine()) != null;) {
			       		    builder.append(line).append("\n");
			       		}
			       		JSONTokener tokener = new JSONTokener(builder.toString());
			       		JSONArray json = new JSONArray(tokener);
			       		
			       		userBets.clear();
			       		for (int i=0; i< json.length(); i++){
			       		 	JSONObject objI = json.getJSONObject(i);
			       		 	String daredUserID = objI.getString("dared_user_id");
			       		 	String daredUserName = usersNames.get(daredUserID);
			       		 	Timestamp startDate =convertStringToTimestamp(objI.getString("start_date"));
			       		 	Timestamp endDate = convertStringToTimestamp(objI.getString("end_date"));
			       			String description = objI.getString("description");
			       			String prize = objI.getString("prize");
			       			String userUrl = objI.getString("user_url");
			       			String daredUserUrl = objI.getString("dared_user_url");
			       			int userLikes = userVideoLikes(userUrl);
			       			int daredUserLikes = userVideoLikes(daredUserUrl);
			       			userBets.add(new Bet(daredUserID, daredUserName, startDate, endDate, description, prize, userUrl, daredUserUrl, userLikes, daredUserLikes));
		            	}
			       		
			       		((MenuFragment)fragments[MENU]).update(userBets);
    	        	}

    	        } catch (Exception e) {
    	        	Log.i("DEBUG", e.toString());
    	        }
    	        infoThreadRunning = false;
    	    }
    	});

    	thread.start();
	}
    
    public String userNameForUserID(String s_id){
    	return usersNames.get(s_id);
    }
    
    @SuppressLint("SimpleDateFormat")
	public static Timestamp convertStringToTimestamp(String time){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        java.util.Date parsedTimeStamp;
		try {
			parsedTimeStamp = (java.util.Date) dateFormat.parse(time);
		} catch (ParseException e) {
			return null;
		}

        return new Timestamp(parsedTimeStamp.getTime());
    }
    
    public static int userVideoLikes(String userUrl){
		String finalUrl = "http://gdata.youtube.com/feeds/api/videos/"+userUrl+"?v=2&alt=jsonc";
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			URI uri = URI.create(finalUrl);
	   		if(uri == null) return -1;
	   		HttpUriRequest request = new HttpGet(uri);
	   		request.setHeader("Content-type", "application/json");
	
	   		HttpResponse response = null;
	   		 
	   		try {
	   			response = httpclient.execute(request);
	   		} catch (Exception e) {
	   			 Log.i("DEBUG", e.toString());
	   		}
	   		
	   		if (response == null) return -1;
	   		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	   		StringBuilder builder = new StringBuilder();
	   		for (String line = null; (line = reader.readLine()) != null;) {
	   		    builder.append(line).append("\n");
	   		}
	   		JSONTokener tokener = new JSONTokener(builder.toString());
	   		JSONObject json = new JSONObject(tokener);
	   		Log.i("DEBUG", json.getJSONObject("data").getString("viewCount"));
	   		return Integer.parseInt(json.getJSONObject("data").getString("viewCount"));
		}
		catch (Exception e) {}
		return -1;
	}
    
    public void showDetails(int position){
		if(position < 0 || userBets==null || position > userBets.size()) return;
		Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
		intent.putExtra("Bet", userBets.get(position));
		startActivity(intent);
    }
}
