package com.cristovaotrevisan.betterbets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;

public class LoginFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.login, 
	            container, false);
	    return view;
	}
}
