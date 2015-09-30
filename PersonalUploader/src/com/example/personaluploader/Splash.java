package com.example.personaluploader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockActivity;

public class Splash extends SherlockActivity{
	
	private static int TIMEDELAYED = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		new Handler().postDelayed(runner, TIMEDELAYED);
	}
	
	private void diplayMainView(){
		startActivity(new Intent(this, MainContent.class));
	}
	
	private Runnable runner = new Runnable() {
		@Override
		public void run() {
			diplayMainView();
			finish();
		}
	};

}
