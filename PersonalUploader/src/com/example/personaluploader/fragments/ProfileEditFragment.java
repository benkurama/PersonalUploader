package com.example.personaluploader.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ProfileEditFragment extends SherlockFragment implements ProcessDialog{

	public final static int PROFILE_EDIT = Sets.PROFILEEDIT;
	
	private EditText etProfileFname, etProfileMname, etProfileLname, etProfileAge, etProfileEmail
		, etProfileAddress, etProfileNotes;
	private Button btnSelectImage, btnUpdate;
	private String ImagePath = "";
	private SherlockFragmentActivity act;
	private MainContent core;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//
		act = getSherlockActivity();
		core = (MainContent)act;
		//
		((BaseActivity)act).setOnBackPressedListener(
				new BaseBackPressedListener(act, PROFILE_EDIT));
		act.setTitle("Profile Edit Mode");
		//
		etProfileFname = (EditText)getView().findViewById(R.id.etProfileFirstname);
		etProfileMname = (EditText)getView().findViewById(R.id.etProfileMiddlename);
		etProfileLname = (EditText)getView().findViewById(R.id.etProfileLastname);
		etProfileAge = (EditText)getView().findViewById(R.id.etProfileAge);
		etProfileEmail = (EditText)getView().findViewById(R.id.etProfileEmail);
		etProfileAddress = (EditText)getView().findViewById(R.id.etProfileAddress);
		etProfileNotes = (EditText)getView().findViewById(R.id.etProfileNotes);
		
		btnSelectImage = (Button)getView().findViewById(R.id.btnSelectImage);
		btnUpdate = (Button)getView().findViewById(R.id.btnUpdateProfile);
		//
		btnSelectImage.setOnClickListener(SelectImageOnClick);
		btnUpdate.setOnClickListener(UpdateProfileOnClick);
		//
		UserObj user = Vars.getUserObj();
		
		if(user != null){
			etProfileFname.setText(user.FirstName);
			etProfileMname.setText(user.MiddleName);
			etProfileLname.setText(user.LastName);
			etProfileAge.setText(String.valueOf(user.Age));
			etProfileEmail.setText(user.EmailAddress);
			etProfileAddress.setText(user.Address);
			etProfileNotes.setText(user.Notes);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == 1 && data != null && data.getData() != null){
			//
			Uri uri = data.getData();
			
			Cursor cursor = getSherlockActivity().getContentResolver()
					.query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }
					, null, null, null);
			cursor.moveToFirst();
			//
			ImagePath = cursor.getString(0);
			btnSelectImage.setText(ImagePath);
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	// =========================================================================
	// TODO Implementation
	// =========================================================================
	private OnClickListener SelectImageOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			selectPics(); 
		}
	}; 
	private OnClickListener UpdateProfileOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			updateData();
		}
	};
	@Override
	public void startProcess() {
		// 
		res = KSOUP.me.profileUpdate(Str(etProfileFname), Str(etProfileMname), Str(etProfileLname)
				, Integer.valueOf(Str(etProfileAge)), Str(etProfileEmail), Str(etProfileAddress)
				, Str(etProfileNotes),ImagePath, Vars.getUserObj().UserName
				, Vars.getUserObj().PasswordNormal);
	}
	@Override
	public void finishProcess() {
		// 
		getUpdateResult(res);
	}
	// =========================================================================
	// TODO Sub Functions
	// =========================================================================
	private void selectPics(){
		//
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Image Select"), 1);
	}
	@SuppressLint("HandlerLeak")
	private String res = "";
	@SuppressLint("HandlerLeak")
	private void updateData(){
		//
		if (ImagePath.length() != 0) {
			//
			Utils.me.ProgressDialog(this, getSherlockActivity(), "Uploading Profile Data");
		} else {
			Utils.me.MessageBoxOK(getSherlockActivity(), "Select Image is required");
		}
	}
	private String Str(EditText et){
		//
		String val = et.getText().toString();
		return val;
	}
	private void getUpdateResult(String result){
		//
		if(!result.equals("null")){
			//
			Toast.makeText(getSherlockActivity(), "Update Successfull", Toast.LENGTH_LONG).show();
			//
//			if(Vars.getLoginType(act).equals("NormalLog")){
//				core.userNormalLoadData();
//			} else if (Vars.getLoginType(act).equals("FacebookLog")){
//				core.userFBLoadData();
//			}
			switch (Vars.getLoginType(act)) {
			case Sets.NORMAL_LOG:
				core.userNormalLoadData();
				break;
			case Sets.FACEBOOK_LOG:
				core.userFBLoadData();
				break;
			}
			//
		} else {
			Toast.makeText(getSherlockActivity(), "Update Failed", Toast.LENGTH_LONG).show();
		}
	}

	
}
