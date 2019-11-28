package com.example.myrestaurant.Model.Response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SizeModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("result")
	private List<Size> result;

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

	public List<Size> getResult() {
		return result;
	}

	public void setResult(List<Size> result) {
		this.result = result;
	}

	@Override
 	public String toString(){
		return 
			"SizeModel{" + 
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}