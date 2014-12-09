package com.cristovaotrevisan.betterbets;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateBetFragment extends Fragment{
	private DatePickerDialog startDateDialog, endDateDialog;
	private EditText startDateEdit, endDateEdit;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.create_bet, 
	            container, false);
	    startDateEdit = (EditText)view.findViewById(R.id.create_start_date);
	    startDateEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDateDialog.show();
				
			}
		});
	    endDateEdit = (EditText)view.findViewById(R.id.create_end_date);
	    endDateEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				endDateDialog.show();
				
			}
		});
	    setDateTimeField();
	    ((Button) view.findViewById(R.id.create_bet_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createBet();
				getActivity().getSupportFragmentManager().popBackStack();
				((MainActivity) getActivity()).showFragment(MainActivity.MENU, false);
			}
		});;
	    return view;
	}
	
	private void setDateTimeField(){
		Calendar newCalendar = Calendar.getInstance();
		startDateDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            startDateEdit.setText(dateFormatter.format(newDate.getTime()));
	        }

	    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
		endDateDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            endDateEdit.setText(dateFormatter.format(newDate.getTime()));
	        }

	    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}
	
	public void updateFriends(){
		Spinner friendPicker = (Spinner) getView().findViewById(R.id.create_friend_picker);
	    ArrayList<String> friendsIDs = ((MainActivity)getActivity()).getFriendsIDs();
	    ArrayList<String> friendsArray = new ArrayList<String>();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, friendsArray);
	    for (int i=0; i<friendsIDs.size(); i++){
	    	friendsArray.add(((MainActivity)getActivity()).userNameForUserID(friendsIDs.get(i)));
	    }
	    friendPicker.setAdapter(adapter);
	}
	
	private void createBet(){ 
		try{
			String daredUserName = ((Spinner) getView().findViewById(R.id.create_friend_picker)).getSelectedItem().toString();
			String daredUserId = ((MainActivity) getActivity()).userIDForUserName(daredUserName);
			String description = ((EditText)getView().findViewById(R.id.create_description)).getText().toString();
			String prize = ((EditText)getView().findViewById(R.id.create_prize)).getText().toString();
			String userUrl = ((EditText)getView().findViewById(R.id.create_user_url)).getText().toString();
			String daredUserUrl = ((EditText)getView().findViewById(R.id.create_dared_user_url)).getText().toString();
			String startDate = startDateEdit.getText().toString();
			String endDate = endDateEdit.getText().toString(); 
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (daredUserName == null || daredUserId== null || description ==null || prize ==null
					|| userUrl ==null|| daredUserUrl==null || startDate==null || endDate==null)
				return;
			Bet bet = new Bet(daredUserId, daredUserName, new Timestamp(format.parse(startDate).getTime()), new Timestamp(format.parse(endDate).getTime()), description, prize, userUrl, daredUserUrl, 0, 0);
			((MainActivity)getActivity()).createBet(bet);
		}
		catch (Exception e) {}
	}
}
