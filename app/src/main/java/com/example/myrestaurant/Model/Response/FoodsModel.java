package com.example.myrestaurant.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodsModel {

	@SerializedName("result")
	private List<Foods> result;

	@SerializedName("success")
	private boolean success;

	public void setResult(List<Foods> result){
		this.result = result;
	}

	public List<Foods> getResult(){
		return result;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"FoodsModel{" +
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}