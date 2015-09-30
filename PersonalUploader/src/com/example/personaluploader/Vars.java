package com.example.personaluploader;

import com.example.personaluploader.objects.UserObj;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

public class Vars extends Application {
	
	public static UserObj userData;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		userData = null;
	}
	public static void setUserObj(UserObj users){
		userData = users;
	}
	public static UserObj getUserObj(){
		return userData;
	}
	//--------------------------------------------------------------------------------
	public static void setLoginType(Context core, int type){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putInt("LoginType", type).commit();
	}
	public static int getLoginType(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getInt("LoginType", 0);
	}
	//
	public static void setUserName(Context core, String username){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putString("Username", username).commit();
	}
	public static String getUsername(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getString("Username", "null");
	}
	//
	public static void setUserPasswordNormal(Context core, String password){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putString("Password", password).commit();
	}
	public static String getUserPasswordNormal(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getString("Password", "null");
	}
}
