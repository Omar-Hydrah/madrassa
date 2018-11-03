package com.madrassa.response;

public class BooleanResponse{
	private String  message;
	private boolean success;

	public BooleanResponse(){

	}

	public BooleanResponse(String message, boolean success){
		this.message = message;
		this.success = success;
	}
	public boolean isSuccess() { return this.success; }
	public void setSuccess(boolean uccess) { this.success = success; }
	public String getMessage(){return this.message;}
	public void setMessage(String message){this.message = message;}

	public String toString(){
		return "{ message: " + message + ", succcess" + success + " }";
	}
}