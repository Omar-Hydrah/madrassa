package com.madrassa;


public class User{
	private String username;
	private String password;
	private UserRole role;
	private String firstName;
	private String lastName;
	public static final String USERNAME_KEY = "username_key";
	public static final String PASSWORD_KEY = "password_key";

	public User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, UserRole role){
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public void setUsername(String username ){
		this.username = username;
	}

	public String getUsername(String username){
		return this.username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setRole(UserRole role){
		this.role = role;
	}

	public UserRole getRole(){
		return role;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return this.lastName;
	}
}