package com.example.personaluploader.utils;

import java.io.File;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

import com.example.personaluploader.objects.UserObj;

public enum KSOUP {
	me;

	public UserObj ksoupLogin(String username, String password) {
		//
		SoapObject request = new SoapObject(Sets.NAMESPACE, Sets.METHOD_NAME_GETCREDENTIALS);
		//
		request.addProperty("username", username);
		request.addProperty("password", password);
		// ----------------------
		SoapSerializationEnvelope envelope = setEnvelope(request);
		//
		SoapObject response = transportHTTPrequest(envelope, Sets.METHOD_NAME_GETCREDENTIALS);
		//
		UserObj user = setAllUserObj(response);
		//
		return user;
	}
	
	public UserObj fbKsoupLogin(String username, String firstname, String middlename, String lastname){
		//
		SoapObject request = new SoapObject(Sets.NAMESPACE, Sets.METHOD_FACEBOOK_LINK);
		//
		request.addProperty("username", username);
		request.addProperty("firstname", firstname);
		request.addProperty("middlename", middlename);
		request.addProperty("lastname", lastname);
		// -----------------
		SoapSerializationEnvelope envelope = setEnvelope(request);
		//
		SoapObject response = transportHTTPrequest(envelope, Sets.METHOD_FACEBOOK_LINK);
		//
		UserObj user = setAllUserObj(response);
		
		return user;
	}
	
	public String profileUpdate(String fname, String mname, String lname, int age, String email
			, String address, String notes, String imagepath, String username, String password){
		//
		SoapObject request = new SoapObject(Sets.NAMESPACE, Sets.METHOD_UPDATE_ACCOUNT);
		//
		byte[] imageByte = Utils.me.imageToBytes(imagepath);
		String fileName = new File(imagepath).getName(); 
		//
		request.addProperty("fname", fname);
		request.addProperty("mname", mname);
		request.addProperty("lname", lname);
		request.addProperty("age", age);
		request.addProperty("emailadd", email);
		
		request.addProperty("address", address);
		request.addProperty("notes", notes);
		request.addProperty("image", imageByte);
		request.addProperty("uname", username);
		request.addProperty("pword", password);
		request.addProperty("filename", fileName);
		// -----------------
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		new MarshalBase64().register(envelope);
		envelope.encodingStyle = SoapEnvelope.ENC;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		// ---
		SoapPrimitive response = null;
		String result = "";
		
		HttpTransportSE ht = new HttpTransportSE(Sets.WEBPOST_URL);
		try {
			ht.call(Sets.NAMESPACE + Sets.METHOD_UPDATE_ACCOUNT, envelope);
			response = (SoapPrimitive) envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		if(response != null){
			result = response.toString();
		} else {
			result = "null";
		}
		
		return result;
	}
	// =========================================================================
	// TODO Sub functions
	// =========================================================================
	private SoapSerializationEnvelope setEnvelope(SoapObject request){
		//
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		return envelope;
	}
	
	private SoapObject transportHTTPrequest(SoapSerializationEnvelope envelope, String Methods){
		//
		SoapObject response = null;
		//
		HttpTransportSE ht = new HttpTransportSE(Sets.WEBPOST_URL);
		try {
			//
			ht.call(Sets.NAMESPACE + Methods, envelope);
			response = (SoapObject)envelope.getResponse();
		} catch (Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	private UserObj setAllUserObj(SoapObject response){
		//
		UserObj user = null;
		if(response != null){
			user = new UserObj();
			//
			user.ID = Integer.parseInt(response.getProperty("ID").toString());
			user.FirstName = response.getProperty("FirstName").toString();
			user.MiddleName = response.getProperty("MiddleName").toString();
			user.LastName = response.getProperty("LastName").toString();
			user.Age = Integer.parseInt(response.getProperty("Age").toString());
			
			user.EmailAddress = response.getProperty("EmailAddress").toString();
			user.Address = response.getProperty("Address").toString();
			user.Notes = response.getProperty("Notes").toString();
			//
			user.ImageUrl = response.getProperty("ImageURL").toString();
			//
			user.DateCreate = response.getProperty("DateCreate").toString();
			user.DateUpdate = response.getProperty("DateUpdate").toString();
			user.UserName = response.getProperty("UserName").toString();
			user.Password = response.getProperty("Password").toString();
			Log.i("Monitor", "Meron laman");
		} else {
			Log.i("Monitor", "null");
		}
		
		return user;
	}
}
