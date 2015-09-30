package com.example.personaluploader.fragments;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.BaseActivity;
import com.example.personaluploader.MainContent;
import com.example.personaluploader.R;
import com.example.personaluploader.implementation.BaseBackPressedListener;
import com.example.personaluploader.utils.Sets;
import com.example.personaluploader.utils.Utils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class EventPageFragment extends SherlockFragment{

	public final static int EVENTS_PAGE = Sets.EVENTS;
	//
	private SherlockFragmentActivity act;
	private MainContent core;
	//
	private ViewPager pager;
	private PageIndicator indicator;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.frame_events, container, false);
	}
//
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//
		act = getSherlockActivity();
		core = (MainContent)act;
		act.setTitle("Event Viewer");
		//
		((BaseActivity)act).setOnBackPressedListener(new BaseBackPressedListener(act, EVENTS_PAGE));
		core.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		//
		pager = (ViewPager)act.findViewById(R.id.homePager);
		pager.setAdapter(new EventPageAdapter(act));
		//
		indicator = (TitlePageIndicator)getView().findViewById(R.id.homeIndicator);
		indicator.setViewPager(pager);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		core.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	 // =========================================================================
	 // TODO Implementation
	 // =========================================================================
	
	 // =========================================================================
	 // TODO Inner Class
	 // =========================================================================
}
