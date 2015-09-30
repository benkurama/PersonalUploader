package com.example.personaluploader.implementation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.MainContent;
import com.example.personaluploader.fragments.EventPageFragment;
import com.example.personaluploader.fragments.HomePageFragment;
import com.example.personaluploader.fragments.LoginPageFragment;
import com.example.personaluploader.fragments.ProfileEditFragment;


public class BaseBackPressedListener implements OnBackPressedListener{
	
	private final SherlockFragmentActivity activity;
	private final int title;

	public BaseBackPressedListener(SherlockFragmentActivity act, int title){
		this.activity = act;
		this.title = title;
	}
	
	@Override
	public void doBack() {
		//
		activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		
		switch(title){
		//
		case ProfileEditFragment.PROFILE_EDIT:
		case EventPageFragment.EVENTS_PAGE:
			backHome();
			break;
			
		case HomePageFragment.HOMEPAGE:
		case LoginPageFragment.LOGIN_PAGE:
			onLeaveApp();
			break;
		}
	}
	
	private void onLeaveApp(){
		//
		new AlertDialog.Builder(activity)
		.setTitle("Leave the App?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	private void backHome(){
		//
		MainContent act = (MainContent)activity;
		act.backToHome();
	}
}
