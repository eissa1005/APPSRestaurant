package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("result")
	private List<Users> result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Users> getResult() {
		return result;
	}

	public void setResult(List<Users> result) {
		this.result = result;
	}

	@Override
 	public String toString(){
		return 
			"User{" +
			"success = '" + success + '\'' +
			",success = '" + message + '\'' +
			",result = '" + result + '\'' +
			"}";
		}
}