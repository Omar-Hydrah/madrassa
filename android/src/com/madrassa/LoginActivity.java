package com.madrassa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.util.Log;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

import com.madrassa.response.AuthResponse;
import com.madrassa.response.ResponseUtil;
import com.madrassa.model.User;
import com.madrassa.AppRepository;

public class LoginActivity extends AppCompatActivity{
	
	public static final String TAG = "madrassa";
	private String baseUrl = "http://192.168.1.103/"; 
	private final String userAgent = "Madrassa-Application";
	private AppRepository repo;
	private Button buttonLogin;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		repo = AppRepository.getInstance(getApplicationContext());

		buttonLogin = (Button) findViewById(R.id.button_login);
	}

	// Log-in the user to the home activity.
	public void login(View view){

		EditText inputUsername = (EditText) findViewById(R.id.username);
		EditText inputPassword = (EditText) findViewById(R.id.password);
		buttonLogin.setEnabled(false);

		final String username = inputUsername.getText().toString().trim();
		final String password = inputPassword.getText().toString().trim();
		
		Map<String, String> credentials = new HashMap<>();
		credentials.put("username", username);
		credentials.put("password", password);

		repo.login(credentials)
		.subscribeOn(Schedulers.computation())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(authResponse -> {
			String  message = authResponse.getMessage();
			boolean success = authResponse.isSuccess();

			if(authResponse == null || message == null){
				failOnUnexpectedResponse();
				return;
			}
			if(!success){
				displayToastMessage(message);
				return;
			}
			Map<String, String> loginPreferences
				= ResponseUtil.extractLoginPreferences(authResponse);

			repo.saveLoginPreferences(loginPreferences);

			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			startHomeActivity();

		}, throwable -> {
			// Log.i(TAG, throwable.getMessage());
			Toast.makeText(LoginActivity.this, "Error occurred", 
				Toast.LENGTH_SHORT).show();
			buttonLogin.setEnabled(true);
		});

	}

	// To be called, if an unexpected response is returned from the server.
	// Server response must include: 
	// 		{success: boolean, message: string, token: string/null}
	public void failOnUnexpectedResponse(){
		Toast.makeText(LoginActivity.this, 
			"Unexpected login error occurred. Please Try again later",
			Toast.LENGTH_SHORT).show();
	}

	public void displayToastMessage(String message){
		Toast.makeText(this, message , Toast.LENGTH_SHORT).show();
	}

	public void startHomeActivity(){
		Intent homeIntent = new Intent(this, HomeActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK 
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(homeIntent);
	}
}