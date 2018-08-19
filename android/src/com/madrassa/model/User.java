package com.madrassa.model;

import java.lang.IllegalArgumentException;

public class User{
	private int    id;
	private String username;
	private String password; // Not encrypted (plain text)
	private String role; // student - teacher - admin
	private String firstName;
	private String lastName;
	private String token; // jsonwebtoken - to be send with every request.
	public static final String ID_KEY         = "id_key";
	public static final String USERNAME_KEY   = "username_key";
	public static final String PASSWORD_KEY   = "password_key";
	public static final String FIRST_NAME_KEY = "first_name_key";
	public static final String LAST_NAME_KEY  = "lsat_name_key";
	public static final String ROLE_KEY       = "role_key";
	public static final String TOKEN_KEY      = "auth_token";

	public User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String role){
		this.username = username;
		this.password = password;
		if(isValidRole(role)){
			this.role     = role;
		}else{
			throw new IllegalArgumentException("Not an allowed user role");
		}
	}

	public void setId(int id){ this.id = id; }
	public int getId(){ return this.id; }

	public void setUsername(String username ){ this.username = username; }
	public String getUsername(){ return this.username; }

	public void setPassword(String password){ this.password = password; }
	public String getPassword(){ return this.password; }

	public void setRole(String role){ 
		// Checking if the role is accepted
		if(isValidRole(role)){
			this.role = role; 
		}else{
			throw new IllegalArgumentException("Not an allowed user role");
		}
	}
	public String getRole(){ return role; }

	public void setFirstName(String firstName){ this.firstName = firstName; }
	public String getFirstName(){ return this.firstName; }

	public void setLastName(String lastName){ this.lastName = lastName;}
	public String getLastName(){ return this.lastName;}

	public void setToken(String token){this.token = token;}
	public String getToken(){return this.token;}

	@Override
	public String toString(){
		return "{id: " + id + ",username: " + username + ",role:" + role +
			",firstName: " + firstName + ",lastName:" + lastName  + "}";
	}

	private boolean isValidRole(String role){
		switch(role){
			case "admin":
				return true;

			case "teacher":
				return true;
			
			case "student":
				return true;

			default:
				return false;
		}

	}
}