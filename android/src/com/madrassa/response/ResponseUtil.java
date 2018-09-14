package com.madrassa.response;

import java.util.Map;
import java.util.HashMap;

import com.madrassa.model.User;

public class ResponseUtil{
	public ResponseUtil(){

	}

	public static Map<String, String> extractLoginPreferences(
		AuthResponse authResponse)
	{

		String  token   = authResponse.getToken();
		User    user    = authResponse.getUser();

		Map<String, String> loginPreferences = new HashMap<>();
		loginPreferences.put(User.USERNAME_KEY, user.getUsername());
		loginPreferences.put(User.TOKEN_KEY, token);
		loginPreferences.put(User.ROLE_KEY, user.getRole());
		loginPreferences.put(User.ID_KEY, String.valueOf(user.getId()));
		loginPreferences.put(User.FIRST_NAME_KEY, user.getFirstName());
		loginPreferences.put(User.LAST_NAME_KEY, user.getLastName());

		return loginPreferences;
	} 
}