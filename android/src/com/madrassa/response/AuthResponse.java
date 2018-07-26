package com.madrassa.response;

import android.util.Log;

import com.madrassa.model.User;

// A response class that matches the expected response from: 
// POST: http://site.com/api/auth/login
public class AuthResponse{
	private boolean success;
	private String message;
	private String token;
	private User   user;

	public AuthResponse(boolean success, String message, String token){
		this.success = success;
		this.message = message;
		this.token   = token;
	}

	public AuthResponse(boolean success, String message, String token, 
		User user)
	{
		this(success, message, token);
		this.user = user;
	}

	public boolean isSuccess() { return this.success; }
	public void setSuccess(boolean success) { this.success = success; }

	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = this.message; }

	public String getToken() { return this.token; }
	public void setToken(String token) { this.token = token; }

	public User getUser(){return this.user;}
	public void setUser(User user){this.user = user;}

	public String toString(){
		return "{success: " + success + ", message: '" + message + "',"
			+ " token: '" + token + "'";
	}
}