package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("FBID")
	private String fBID;

	@SerializedName("userName")
	private String userName;
	@SerializedName("address")
	private String address;

	@SerializedName("userPhone")
	private String userPhone;

	@SerializedName("FaceId")
	private String faceId;

	@SerializedName("userPhoto")
	private String userPhoto;

	@SerializedName("userEmail")
	private String userEmail;

	@SerializedName("TwitterId")
	private String twitterId;


	public String getfBID() {
		return fBID;
	}

	public void setfBID(String fBID) {
		this.fBID = fBID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	@Override
 	public String toString(){
		return 
			"UserItem{" +
			"faceId = '" + faceId + '\'' + 
			",address = '" + address + '\'' + 
			",userPhoto = '" + userPhoto + '\'' + 
			",fBID = '" + fBID + '\'' + 
			",userPhone = '" + userPhone + '\'' + 
			",userEmail = '" + userEmail + '\'' + 
			",userName = '" + userName + '\'' + 
			",twitterId = '" + twitterId + '\'' + 
			"}";
		}
}