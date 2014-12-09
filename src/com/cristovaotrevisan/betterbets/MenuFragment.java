package com.cristovaotrevisan.betterbets;

import java.util.ArrayList;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuFragment extends Fragment {
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> betsArray=new ArrayList<String>();
	private ProgressDialog loading =null;;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.menu, 
	            container, false);
	    setHasOptionsMenu(true);
	    listView = (ListView) view.findViewById(R.id.listView1);
	    
	   listView.setOnItemClickListener(new OnItemClickListener()
	    {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Log.i("DEBUG","Clicked");
	        	((MainActivity)getActivity()).showDetails(position);
				
			}
	    });
	    
	    adapter= new ArrayAdapter<String>(getActivity(), R.layout.simple_text_view_item, betsArray);
	    showLoading();
	    listView.setAdapter(adapter);
	    return view;
	}
	
	public void showLoading(){
		if(loading == null)
		loading = ProgressDialog.show(getActivity(), getResources().getString(R.string.loading), "",true);
	}
	
	public void hideLoading(){
		if(loading != null){
			loading.dismiss();
			loading = null;
		}
	}
	
	@SuppressLint("UseValueOf")
	public void update(ArrayList<Bet> bets){
		betsArray.clear();
		for (int i=0; i<bets.size(); i++){
			Bet bet = bets.get(i);
			String id = bet.getDaredUserID();
			betsArray.add(((MainActivity) getActivity()).userNameForUserID(id)+ " " + (new Integer(bet.getUserLikes())).toString() +"x"+ (new Integer(bet.getDaredUserLikes())).toString());
		}
		listView.post(new Runnable() {                  
		    @Override
		    public void run() {
		    	hideLoading();
		       adapter.notifyDataSetChanged();

		    }
		});
	}
	
	@Override
	public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		switch (item.getItemId()) {
        case R.id.action_logout:
        	((MainActivity) getActivity()).callFacebookLogout();
            return true;
        case R.id.action_new_bet:
        	
        	return true;
        default:
            return super.onOptionsItemSelected(item);
		}

	}
}
