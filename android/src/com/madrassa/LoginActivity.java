package com.madrassa;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

	}

	// Log-in the user to the home activity.
	public void login(View view){
		EditText inputUsername = (EditText) findViewById(R.id.username);
		EditText inputPassword = (EditText) findViewById(R.id.password);

		String username = inputUsername.getText().toString();
		String password = inputPassword.getText().toString();

		if(username.equals("omar") && password.equals("1234")){
			// Verify login and open Home Activity
			Intent homeIntent = new Intent(this, HomeActivity.class);
			startActivity(homeIntent);

		}else{
			// Wrong credentials
			Toast.makeText(this, "Wrong username/password" ,
				Toast.LENGTH_SHORT).show();
		}
	}
}