package com.example.personaluploader;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.MenuItem;
import com.example.personaluploader.fragments.MainMenuFragment;
import com.example.personaluploader.implementation.OnBackPressedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity{
	 // =========================================================================
	 // TODO Variables
	 // =========================================================================
	private String PageTitles;
	protected Fragment oFrag;
	protected OnBackPressedListener onBackPressedListener;
	
	public BaseActivity(String title){
		PageTitles = title; 
	}
	// =========================================================================
	// TODO Overrides
	// =========================================================================
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//
		super.onCreate(savedInstanceState);
		setTitle(PageTitles);
		setBehindContentView(R.layout.frame_menu);
		//
		if(savedInstanceState == null){
			//
			FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
			oFrag = new MainMenuFragment();
			ft.replace(R.id.frame_menu, oFrag);
			ft.commit();
		} else {
			oFrag = getSupportFragmentManager().findFragmentById(R.id.frame_menu);
		}
		// customize the Sliding Menu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//
		getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	@Override
	public void onBackPressed() {
		//
		if(onBackPressedListener != null){
			onBackPressedListener.doBack();
		} else {
			super.onBackPressed();
		}
	}
	
	// =========================================================================
	// TODO Main Function
	// =========================================================================
	public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener){
		this.onBackPressedListener = onBackPressedListener;
	}
}
