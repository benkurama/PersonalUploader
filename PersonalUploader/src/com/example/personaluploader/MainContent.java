package com.example.personaluploader;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.example.personaluploader.fragments.HomePageFragment;
import com.example.personaluploader.fragments.LoginPageFragment;
import com.example.personaluploader.fragments.MainMenuFragment;
import com.example.personaluploader.fragments.ProfileEditFragment;
import com.example.personaluploader.fragments.SubMenuFragment;
import com.example.personaluploader.implementation.ProcessDialog;
import com.example.personaluploader.objects.UserObj;
import com.example.personaluploader.utils.Frag;
import com.example.personaluploader.utils.KSOUP;
import com.example.personaluploader.utils.Sets;
import com.example.personaluploader.utils.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainContent extends BaseActivity implements ProcessDialog{
	// =========================================================================
	// TODO Variables
	// =========================================================================
	private UiLifecycleHelper uiHelper;
	private boolean isResumed = false;
	private GraphUser userFBData;
	//
	private final static int FB_KSOUP_LOGIN = 1;
	private final static int KSOUP_LOGIN = 2;
	private int SELECTED_QUERY = FB_KSOUP_LOGIN;
	//
	public MainContent() {
		super("Personal Uploader");
	}
	// =========================================================================
	// TODO Life Cycles
	// =========================================================================
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
		
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setShadowWidth(5);
		getSlidingMenu().setBehindScrollScale(0);
		getSlidingMenu().setFadeEnabled(false);
		//
		setContentView(R.layout.frame_content);
		showHome();
		//
		getSlidingMenu().setSecondaryMenu(R.layout.frame_sub);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSlidingMenu().setRightBehindOffset(120);
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.frame_sub, new SubMenuFragment()).commit();
		//
		uiHelper = new UiLifecycleHelper(this, facebookCallback);
		uiHelper.onCreate(savedInstanceState);
		//
		userFBData = null;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		isResumed = false;
		uiHelper.onPause();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// 
		switch (item.getItemId()) {
		case android.R.id.home:
			//
			if(!(Frag.me.getCurrentFragment(this, R.id.frame_content) instanceof HomePageFragment)){
				//
				backToHome();
			} 
			break;
		}
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
		isResumed = true;
		uiHelper.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy(); 
	}
	// =========================================================================
	// TODO Overrides
	// =========================================================================
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		//
		//Fragment frag = getSupportFragmentManager().findFragmentById(R.id.frame_content);
		//
		if (!(Frag.me.getCurrentFragment(this, R.id.frame_content) instanceof ProfileEditFragment)) {
			//
			//
			switch (Vars.getLoginType(getBaseContext())) {
			case Sets.NORMAL_LOG:
				//
				UserObj user = Vars.getUserObj();
				if (user == null) {
					//showLogin();
					userNormalLoadData(); 
				}
				break;
			default :
				//
				Session session = Session.getActiveSession();
				if (session != null && session.isOpened()) {
					Vars.setLoginType(getBaseContext(), Sets.FACEBOOK_LOG);
					//
					Frag.me.get(new MainMenuFragment(), this, R.id.frame_menu).setLogoutLayout();
					Frag.me.get(new MainMenuFragment(), this, R.id.frame_menu).setFBProfilePic(session);
					//
					fbKsoupRelation(session);
					//
					showHome();
				} else {
					showLogin();
				}
				break;
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	// =========================================================================
	// TODO Main Functions
	// =========================================================================
	public void onSessionStateChange(Session session, SessionState state, Exception exception) {
		//
		if (isResumed) {
			//
			FragmentManager manager = getSupportFragmentManager();
			int backStackSize = manager.getBackStackEntryCount();

			for (int i = 0; i < backStackSize; i++) {
				//
				manager.popBackStack();
			}

			if (state.isOpened()) {
				Vars.setLoginType(getBaseContext(), Sets.FACEBOOK_LOG);
				showHome();
				Frag.me.get(new MainMenuFragment(), this, R.id.frame_menu).setFBProfilePic(session);
				//
			} else {
				switch (Vars.getLoginType(this)) {
				case Sets.NORMAL_LOG:
					showHome();
					break;
				case Sets.FACEBOOK_LOG:
					showLogin();
					break;
				}
			}
		}
	}
	
	public void fbKsoupRelation(final Session session){
		//
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if(session == Session.getActiveSession()){
					//
					if(user != null){
						userFBData = user;
						callingKsoup();
					}
				}
			}
		});
		request.executeAsync(); 
	}
	// =========================================================================
	// TODO Implementation
	// =========================================================================
	private Session.StatusCallback facebookCallback =  new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			//
			onSessionStateChange(session, state, exception);
		}
	};
	@Override
	public void startProcess() {
		//
		switch (SELECTED_QUERY) {
		case FB_KSOUP_LOGIN:
			//
			Vars.setUserObj( KSOUP.me.fbKsoupLogin(userFBData.getProperty("email").toString()
					, userFBData.getFirstName(), userFBData.getMiddleName(), userFBData.getLastName()));
			break;
		case KSOUP_LOGIN:
			//
			//Vars.setUserObj(KSOUP.me.ksoupLogin(Vars.getUserObj().UserName, Vars.getUserObj().PasswordNormal));
			Vars.setUserObj(KSOUP.me.ksoupLogin(Vars.getUsername(getBaseContext()), Vars.getUserPasswordNormal(getBaseContext())));
			break;
		}
			
	}
	@Override
	public void finishProcess() {
		//
		switch (SELECTED_QUERY) {
		case FB_KSOUP_LOGIN:
			//
			setupUserData();
			break;
		case KSOUP_LOGIN:
			//
			resetProfileData();
			break;
		}
	}
	// =========================================================================
	// TODO Sub Functions
	// =========================================================================
	public void showLogin(){
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.frame_content, new LoginPageFragment()).commit();
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		//
		showContent();
		Vars.setLoginType(getBaseContext(), Sets.NULL_LOG);
	}
	
	public void showHome(){
		getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
		.replace(R.id.frame_content, new HomePageFragment()).commit();
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	public void loadHome(){
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.frame_content, new HomePageFragment()).commit();
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	public void backToHome(){
		//
		getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(R.anim.slide_right_inback, R.anim.slide_right_outback)
		.replace(R.id.frame_content, new HomePageFragment()).commit();
		//
	}
	
	@SuppressLint("HandlerLeak")
	public void callingKsoup(){
		//
		Utils.me.ProgressDialog(this, this, "Connecting to Server");
	}
	
	private void setupUserData(){
		//
		Frag.me.get(new SubMenuFragment(), this, R.id.frame_sub).setProfileData();
		Frag.me.get(new HomePageFragment(), this, R.id.frame_content).setWelcomePage();
	}
	// ------------------------
	@SuppressLint("HandlerLeak")
	public void userNormalLoadData(){
		//
		//
		SELECTED_QUERY = KSOUP_LOGIN;
		Utils.me.ProgressDialog(this, this, "Updating Data");
	}
	
	private void resetProfileData(){
		//
		SELECTED_QUERY = FB_KSOUP_LOGIN;
		Vars.getUserObj().PasswordNormal = Vars.getUserPasswordNormal(this);
		//
		if (!(Frag.me.getCurrentFragment(this, R.id.frame_content) instanceof HomePageFragment)) {
			backToHome();
		} else {
			loadHome();
		}
		//
		Frag.me.get(new SubMenuFragment(), this, R.id.frame_sub).setProfileData();
		Frag.me.get(new MainMenuFragment(), this, R.id.frame_menu).setLogoutLayout();
	} 
	
	public void userFBLoadData(){
		//
		backToHome();
		//
		Session session = Session.getActiveSession();
		if(session != null && session.isOpened()){
			fbKsoupRelation(session);
		}
	}
	// =========================================================================
	// TODO Final
	
}
