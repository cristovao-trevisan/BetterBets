package com.cristovaotrevisan.betterbets;

import java.util.Arrays;

import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;

public class LoginFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.login, container, false);
	    ((LoginButton) view.findViewById(R.id.login_button)).setReadPermissions(Arrays.asList("friend_list", "public_profile"));;
	    return view;
	}
}
