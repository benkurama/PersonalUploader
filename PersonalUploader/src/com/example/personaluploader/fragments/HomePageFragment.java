package com.example.personaluploader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.personaluploader.BaseActivity;
import com.example.personaluploader.R;
import com.example.personaluploader.Vars;
import com.example.personaluploader.implementation.BaseBackPressedListener;
import com.example.personaluploader.objects.ImageLoader;
import com.example.personaluploader.objects.UserObj;
import com.example.personaluploader.utils.Sets;

public class HomePageFragment extends SherlockFragment{

	public final static int HOMEPAGE = Sets.HOME;
	
	private ImageView ivHomeImage;
	private ImageLoader img;
	private TextView tvWelcomeUser;
	private final static int IMAGE_SIZE = 120;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// 
		super.onActivityCreated(savedInstanceState);
		//
		((BaseActivity)getSherlockActivity()).setOnBackPressedListener(
				new BaseBackPressedListener(getSherlockActivity(), HOMEPAGE));
		getSherlockActivity().setTitle("Home Page");
		//
		ivHomeImage = (ImageView)getView().findViewById(R.id.ivHomeImage);
		img = new ImageLoader(getSherlockActivity(), IMAGE_SIZE);
		tvWelcomeUser = (TextView)getView().findViewById(R.id.tvHomepageUser);
		
		callingImageSet();
	}
	// =========================================================================
	// TODO Outside Function
	// =========================================================================
	public void setWelcomePage(){
		//
		callingImageSet();
	}
	// =========================================================================
	// TODO Sub Function
	// =========================================================================
	private void callingImageSet(){
		//
		UserObj user = Vars.getUserObj();
		//
		if(user != null){
			img.DisplayImage(Sets.IMAGE_URL+user.ImageUrl, ivHomeImage);
			tvWelcomeUser.setText("Welcome "+ user.FirstName);
		}
	}
}
