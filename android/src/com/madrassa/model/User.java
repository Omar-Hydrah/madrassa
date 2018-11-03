package com.madrassa.model;

import java.lang.IllegalArgumentException;

public class User{
	private int    id;
	private String username;
	private String password; // Not encrypted (plain text)
	private String role; // student - teacher - admin
	private String firstName;
	private String lastName;
	private String name;
	private String token; // jsonwebtoken - to be send with every request.
	public static final String ID_KEY         = "id_key";
	public static final String USERNAME_KEY   = "username_key";
	public static final String PASSWORD_KEY   = "password_key";
	public static final String FIRST_NAME_KEY = "first_name_key";
	public static final String LAST_NAME_KEY  = "last_name_key";
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

	public User(String username, String firstName, String lastName, String role){
		this(username, null, role);
		this.firstName = firstName;
		this.lastName  = lastName;
		this.name      = firstName + " " + lastName;
	}

	public User(int id, String username, String firstName, String lastName,
		String role)
	{
		this(username, firstName, lastName, role);
		this.id = id;
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

	public String getName(){return this.firstName + " " + this.lastName;}

	public void setToken(String token){this.token = token;}
	public String getToken(){return this.token;}

	@Override
	public String toString(){
		return "{" +
			"id: " + id + ", " + 
			"username: " + username + ", "+
			"name: " + name + ", " +
			"firstName: " + firstName + ", " +
			"lastName: " + lastName  + ", " +
			"role: " + role + ", " +
		"}";
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