package com.example.personaluploader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.MainContent;
import com.example.personaluploader.R;
import com.example.personaluploader.Vars;
import com.example.personaluploader.objects.UserObj;
import com.example.personaluploader.utils.Sets;
import com.example.personaluploader.utils.Utils;

public class SubMenuFragment extends SherlockFragment{
	// =========================================================================
	// TODO Variables
	// =========================================================================
	private TextView tvProfileFirstname, tvProfileMiddlename, tvProfileLastname, tvProfileAge,
		tvProfileEmailAd, tvProfileAddress, tvProfileNotes, tvProfileDateCreate;
	private Button btnProfileEdit;
	private MainContent core;
	private SherlockFragmentActivity act;
	// =========================================================================
	// TODO Overrides
	// =========================================================================
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		//
		View view = inflater.inflate(R.layout.fragment_sub_menu, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		act = getSherlockActivity();
		core = (MainContent)act;
		
		tvProfileFirstname = (TextView)getView().findViewById(R.id.tvProfileFirstname);
		tvProfileMiddlename = (TextView)getView().findViewById(R.id.tvProfileMiddlename);
		tvProfileLastname = (TextView)getView().findViewById(R.id.tvProfileLastname);
		tvProfileAge = (TextView)getView().findViewById(R.id.tvProfileAge);
		tvProfileEmailAd = (TextView)getView().findViewById(R.id.tvProfileEmailAd);
		tvProfileAddress = (TextView)getView().findViewById(R.id.tvProfileAddress);
		tvProfileNotes = (TextView)getView().findViewById(R.id.tvProfileNotes);
		tvProfileDateCreate = (TextView)getView().findViewById(R.id.tvProfileDateCreate);
		
		btnProfileEdit = (Button)getView().findViewById(R.id.btnProfileEdit);
		//
		btnProfileEdit.setOnClickListener(profileEditOnClick);
	}
	// =========================================================================
	// TODO Outside Function
	// =========================================================================
	public void setProfileData() {
		//
		UserObj user = Vars.getUserObj();
		if (user != null) {
			//
			tvProfileFirstname.setText(user.FirstName);
			tvProfileMiddlename.setText(user.MiddleName);
			tvProfileLastname.setText(user.LastName);
			tvProfileAge.setText(String.valueOf(user.Age));
			tvProfileEmailAd.setText(user.EmailAddress);
			tvProfileAddress.setText(user.Address);
			tvProfileNotes.setText(user.Notes);
			tvProfileDateCreate.setText(user.DateCreate);
		} else {
			
		}
	}
	// =========================================================================
	// TODO Implementation
	// =========================================================================
	private OnClickListener profileEditOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			getSherlockActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_content, new ProfileEditFragment()).commit();
			//
			core.showContent();
		}
	};
}
