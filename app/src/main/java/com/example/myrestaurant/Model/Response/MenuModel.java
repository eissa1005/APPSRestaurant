package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuModel{

	@SerializedName("result")
	private List<Category> result;

	@SerializedName("success")
	private boolean success;

	public List<Category> getResult() {
		return result;
	}

	public void setResult(List<Category> result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
 	public String toString(){
		return 
			"MenuModel{" + 
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}