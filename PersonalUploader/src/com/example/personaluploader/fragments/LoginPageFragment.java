package com.example.personaluploader.fragments;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.BaseActivity;
import com.example.personaluploader.MainContent;
import com.example.personaluploader.R;
import com.example.personaluploader.Vars;
import com.example.personaluploader.implementation.BaseBackPressedListener;
import com.example.personaluploader.implementation.ProcessDialog;
import com.example.personaluploader.objects.UserObj;
import com.example.personaluploader.utils.Frag;
import com.example.personaluploader.utils.KSOUP;
import com.example.personaluploader.utils.Sets;
import com.example.personaluploader.utils.Utils;
import com.facebook.widget.LoginButton;

public class LoginPageFragment extends SherlockFragment implements ProcessDialog{
	// =========================================================================
	// TODO Variables
	// =========================================================================
	public final static int LOGIN_PAGE = Sets.LOGIN;
	
	private Button btnLogin;
	private MainContent core;
	private SherlockFragmentActivity act;
	
	private EditText etLoginUserID, etLoginPassword;
	public LoginButton btnFacebookLogin;
	
	public UserObj userTestObj;
	// =========================================================================
	// TODO Overrides
	// =========================================================================
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		//
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		return view; 
	}   

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//
		act = getSherlockActivity();
		core = (MainContent)act;
		((BaseActivity)act).setOnBackPressedListener( 
				new BaseBackPressedListener(getSherlockActivity(), LOGIN_PAGE));
		act.setTitle("Login Page"); 
		//
		btnLogin = (Button)getView().findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(loginOnClick);
		//
		etLoginUserID = (EditText)getView().findViewById(R.id.etLoginUserID);
		etLoginPassword = (EditText)getView().findViewById(R.id.etLoginPassword);
		//
		btnFacebookLogin = (LoginButton)getView().findViewById(R.id.btnFacebookLogin);
		btnFacebookLogin.setReadPermissions(Arrays.asList("email"));
	}
	// =========================================================================
	// TODO Implementation
	// =========================================================================
	private OnClickListener loginOnClick = new OnClickListener() { 
		//
		@SuppressLint("HandlerLeak")
		@Override
		public void onClick(View v) { 
			//
			onClickLogin();
		}
	};

	@Override
	public void startProcess() {
		// 
		userTestObj = KSOUP.me.ksoupLogin(etLoginUserID.getText().toString()
				, etLoginPassword.getText().toString());
	}

	@Override
	public void finishProcess() {
		// 
		setNormalHome(userTestObj);
	}
	// =========================================================================
	// TODO Sub Functions
	// =========================================================================
	public void onClickLogin(){
		//
		if(etLoginUserID.length() != 0 && etLoginPassword.length() != 0){
			//
			Utils.me.ProgressDialog(this, act, "Validate username...");
		} else {
			Utils.me.MessageBoxOK(getSherlockActivity(), "Fill all required fields");
		}
	}
	private void setNormalHome(UserObj user){ 
		//
		if(user != null){
			//
			user.PasswordNormal = etLoginPassword.getText().toString();
			Vars.setUserName(act, etLoginUserID.getText().toString());
			Vars.setUserPasswordNormal(act, etLoginPassword.getText().toString());
			//
			Vars.setUserObj(user);
			//
			Vars.setLoginType(core, Sets.NORMAL_LOG);
			//
			Frag.me.get(new MainMenuFragment(), act, R.id.frame_menu).setLogoutLayout();
			Frag.me.get(new SubMenuFragment(), act, R.id.frame_sub).setProfileData();
			//
			core.showHome();
		} else {
			//
			Utils.me.MessageBoxOK(getSherlockActivity(), "Your not Registered");
			etLoginUserID.setText("");
			etLoginPassword.setText("");
		}
	}
	// =========================================================================
	// TODO Final Code
}
