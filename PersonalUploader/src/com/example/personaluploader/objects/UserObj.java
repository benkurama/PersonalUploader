package com.example.personaluploader.objects;

import android.graphics.Bitmap;

public class UserObj {
//
	public int ID;
	public String FirstName;
	public String MiddleName;
	public String LastName;
	public int Age;
	public String EmailAddress;
	public String Address;
	public String Notes;
	public String DateCreate;
	public String UserName;
	public String Password;
	public String PasswordNormal;
	
	public String ProfileID;
	public String BaseImage;
	
	public Bitmap Image;
	public String ImageUrl;
	public String DateUpdate;

	public UserObj(){
		
		this.ID = 0;
		this.FirstName = "";
		this.MiddleName = "";
		this.LastName = "";
		this.Age = 0;
		this.EmailAddress ="";
		this.Address = "";
		this.Notes = "";
		this.DateCreate ="";
		this.UserName = "";
		this.Password = "";
		this.PasswordNormal = "";
		
		this.Image = null;
		this.DateUpdate = "";
		
		this.ProfileID = "";
		this.BaseImage = "";
		this.ImageUrl = "";
	}
}
