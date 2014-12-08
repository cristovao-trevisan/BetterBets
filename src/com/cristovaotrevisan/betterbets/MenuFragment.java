package com.cristovaotrevisan.betterbets;

import java.util.ArrayList;

import android.R.anim;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment {
	private static final String TAG = "SelectionFragment";
	private ListView listView;
	ArrayAdapter<String> adapter;
	ArrayList<String> betsArray=new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.menu, 
	            container, false);
	    setHasOptionsMenu(true);
	    listView = (ListView) view.findViewById(R.id.listView1);
	    adapter= new ArrayAdapter<String>(getActivity(), R.layout.simple_text_view_item, betsArray);
//	    adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, betsArray);
	    listView.setAdapter(adapter);
	    return view;
	}
	
	public void update(ArrayList<Bet> bets){
		betsArray.clear();
		for (int i=0; i<bets.size(); i++){
			String id = bets.get(i).getDaredUserID();
			betsArray.add(((MainActivity) getActivity()).userNameForUserID(id));
		}
		Log.i("DEBUG", bets.toString());
		Log.i("DEBUG","PASSS");
        adapter.notifyDataSetChanged();
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
        default:
            return super.onOptionsItemSelected(item);
		}

	}
}
