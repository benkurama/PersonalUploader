package com.example.personaluploader.fragments;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.R;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventPageAdapter extends PagerAdapter{
	
	private int PAGE_COUNT = 2;
	private String[] titles = new String[]{"Event List", "Event Add"};
	private SherlockFragmentActivity act;

	public EventPageAdapter(SherlockFragmentActivity act){
		this.act = act;
	}
	
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		//return "Hello";
		return titles[position];
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View new_view = null;
		LayoutInflater inflater = act.getLayoutInflater();

		switch (position) {
		case 0:
			new_view = inflater.inflate(R.layout.pager_events_list, null);
			//
			break;
		case 1:
			new_view = inflater.inflate(R.layout.pager_events_add, null);
			//
			break;
		}
		//
		container.addView(new_view);
		return new_view;
	}
}

