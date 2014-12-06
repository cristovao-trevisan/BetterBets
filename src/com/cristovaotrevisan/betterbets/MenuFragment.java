package com.cristovaotrevisan.betterbets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MenuFragment extends Fragment {
	private static final String TAG = "SelectionFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);

	    View view = inflater.inflate(R.layout.menu, 
	            container, false);
	    setHasOptionsMenu(true);
	    return view;
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
