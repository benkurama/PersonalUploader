package com.example.personaluploader.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.MainContent;
import com.example.personaluploader.R;
import com.example.personaluploader.Vars;
import com.example.personaluploader.objects.UserObj;
import com.example.personaluploader.utils.Frag;
import com.example.personaluploader.utils.Sets;
import com.example.personaluploader.utils.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
//import android.support.v4.app.Fragment;

public class MainMenuFragment extends SherlockFragment{
	// =========================================================================
	// TODO Variables
	// =========================================================================
	public Button btnLogout, btnEvents;
	public LoginButton fbLogoutButton;
	private ProfilePictureView profilePicView;
	private TextView tvUsername;
	
	private MainContent core;
	private SherlockFragmentActivity act;
	// =========================================================================
	// TODO Overrides
	// =========================================================================
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//
		View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//
		act = getSherlockActivity();
		core = (MainContent)act;
		
		btnLogout = (Button)getView().findViewById(R.id.btnLogout);
		btnEvents = (Button)getView().findViewById(R.id.BtnMenuEvent);
		//
		btnLogout.setOnClickListener(logoutOnClick);
		btnEvents.setOnClickListener(eventsOnClick);
		//
		btnLogout.setVisibility(View.GONE);
		fbLogoutButton = (LoginButton)getView().findViewById(R.id.btnFacebookLogout);
		fbLogoutButton.setVisibility(View.GONE);
		//
		profilePicView = (ProfilePictureView)getView().findViewById(R.id.facebook_profile_pic);
		profilePicView.setCropped(true);
		profilePicView.setVisibility(View.GONE);
		//
		tvUsername = (TextView)getView().findViewById(R.id.tvUserName);
		
		//
		setLogoutLayout();  
	}
	// =========================================================================
	// TODO Outside Function
	// =========================================================================
	public void setLogoutLayout(){
		//
		switch (Vars.getLoginType(act)) {
		case Sets.NORMAL_LOG:
			//
			btnLogout.setVisibility(View.VISIBLE);
			fbLogoutButton.setVisibility(View.GONE);
			profilePicView.setVisibility(View.GONE);
			UserObj user = Vars.getUserObj();
			if(user != null){
				tvUsername.setText(user.FirstName + " "+ user.LastName);
			}else{
				tvUsername.setText("Null");
			}
			break;
		case Sets.FACEBOOK_LOG:
			//
			btnLogout.setVisibility(View.GONE);
			fbLogoutButton.setVisibility(View.VISIBLE);
			profilePicView.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public void setFBProfilePic(final Session session){
		//
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				//
				if (session == Session.getActiveSession()){
					//
					if(user != null){
						profilePicView.setProfileId(user.getId());
						tvUsername.setText(user.getName());
					}
				}
				//
				if(response.getError() != null){
					// code in later
				}
			}
		});
		request.executeAsync();
	}
	// =========================================================================
	// TODO Implementation
	// =========================================================================
	private OnClickListener logoutOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			logoutConfirmation();
			//
		}
	};
	private OnClickListener eventsOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openEventPage();
		}
	};
	// =========================================================================
	// TODO Sub Functions
	// =========================================================================
	private void logoutConfirmation(){
		//
		new AlertDialog.Builder(getSherlockActivity())
		.setTitle("Are you sure you wanna logout?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				core.showLogin();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	private void openEventPage(){
		//
		Frag.me.set(new EventPageFragment(), act, R.id.frame_content, false);
		core.showContent();
	}
}
