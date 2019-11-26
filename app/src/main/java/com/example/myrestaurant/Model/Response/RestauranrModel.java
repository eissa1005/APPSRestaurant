package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestauranrModel{

	@SerializedName("result")
	private List<Restaurant> result;

	@SerializedName("success")
	private boolean success;

	public List<Restaurant> getResult() {
		return result;
	}

	public void setResult(List<Restaurant> result) {
		this.result = result;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
 	public String toString(){
		return 
			"RestauranrModel{" + 
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}