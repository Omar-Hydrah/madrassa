package com.madrassa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity{
	
	private SharedPreferences sharedPrefs;
	private SharedPreferences.Editor editor; 

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPrefs.edit();
	}

	// Log-in the user to the home activity.
	public void login(View view){
		EditText inputUsername = (EditText) findViewById(R.id.username);
		EditText inputPassword = (EditText) findViewById(R.id.password);

		String username = inputUsername.getText().toString();
		String password = inputPassword.getText().toString();

		if(username.equals("omar") && password.equals("1234")){
			editor.putString(User.USERNAME_KEY, username);
			editor.putString(User.PASSWORD_KEY, password);
			editor.commit();

			// Verify login and open Home Activity
			Intent homeIntent = new Intent(this, HomeActivity.class);
			
			homeIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | 
				Intent.FLAG_ACTIVITY_NEW_TASK);

			startActivity(homeIntent);

		}else{
			// Wrong credentials
			Toast.makeText(this, "Wrong username/password" ,
				Toast.LENGTH_SHORT).show();
		}
	}
}