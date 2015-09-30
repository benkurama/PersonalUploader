package com.example.personaluploader.utils;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.R;

public enum Frag {
//
	me;
	//
	@SuppressWarnings("unchecked")
	public <X extends SherlockFragment> X get(X frag, SherlockFragmentActivity act, int container){
		//
		X fragment = frag;
		fragment = (X)act.getSupportFragmentManager().findFragmentById(container);
		
		return fragment;
	}
	
	public SherlockFragment getCurrentFragment(SherlockFragmentActivity act, int container){
		//
		SherlockFragment frag = (SherlockFragment) act.getSupportFragmentManager().findFragmentById(container);
		return frag;
	}
	
	public <X extends SherlockFragment> void set(X frag, SherlockFragmentActivity act, int container, boolean slide){
		//
		if(slide){
			act.getSupportFragmentManager().beginTransaction()
			.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
			.replace(container, frag)
			.commit();
		} else {
			act.getSupportFragmentManager().beginTransaction()
			.replace(container, frag)
			.commit();
		}
	}
}
