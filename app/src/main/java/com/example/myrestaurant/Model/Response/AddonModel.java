package com.example.myrestaurant.Model.Response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AddonModel{

	@SerializedName("result")
	private List<Addon> result;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<Addon> getResult() {
		return result;
	}

	public void setResult(List<Addon> result) {
		this.result = result;
	}

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

	@Override
 	public String toString(){
		return 
			"AddonModel{" + 
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}