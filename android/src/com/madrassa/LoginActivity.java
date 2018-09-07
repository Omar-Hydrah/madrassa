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
import android.util.Log;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.madrassa.response.AuthResponse;
import com.madrassa.model.User;
import com.madrassa.service.AuthService;

public class LoginActivity extends AppCompatActivity{
	
	private SharedPreferences sharedPrefs;
	private SharedPreferences.Editor editor; 
	public static final String TAG = "madrassa";
	private String baseUrl = "http://192.168.1.103/api"; 
	private final String userAgent = "Madrassa-Application";
	private Retrofit retrofit = new Retrofit.Builder()
		.baseUrl(baseUrl)
		.addConverterFactory(GsonConverterFactory.create())
		.build();

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

		final String username = inputUsername.getText().toString().trim();
		final String password = inputPassword.getText().toString().trim();

		AuthService service = retrofit.create(AuthService.class);
		Map<String, String> credentials = new HashMap<>();
		credentials.put("username", username);
		credentials.put("password", password);

		Call<AuthResponse> call = service.login(userAgent, credentials);

		call.enqueue(new Callback<AuthResponse>(){
			@Override
			public void onResponse(Call<AuthResponse> call,
				Response<AuthResponse> response)
			{

				AuthResponse authResponse = response.body();

				String  message = authResponse.getMessage();
				boolean success = authResponse.isSuccess();
				String  token   = authResponse.getToken();
				User    user    = authResponse.getUser();

				// Unexpected response from server.
				if(message == null){
					// Exit the login process
					failOnUnexpectedResponse();
					return;
				}
				// Probably a fail case (wrong username/password)
				if(!success && message != null){
					displayToastMessage(message);

				}else if(success && token != null && 
					message.equals("Token created"))
				{
					displayToastMessage(message);
					// Store the token and important user attributes 
					// 		in shared preferences:
					editor.putString(User.USERNAME_KEY, user.getUsername());
					editor.putString(User.TOKEN_KEY, token);
					editor.putString(User.ROLE_KEY, user.getRole());
					editor.putString(User.ID_KEY, String.valueOf(user.getId()));
					editor.putString(User.FIRST_NAME_KEY, user.getFirstName());
					editor.putString(User.LAST_NAME_KEY, user.getLastName());
					editor.commit();

					startHomeActivity();

				}else{
					// Exit the login process.
					failOnUnexpectedResponse();
					return;
				}
			}

			@Override
			public void onFailure(Call<AuthResponse> call, Throwable t){
				displayToastMessage("Error occurred");
			}
		});

		/*if(username.equals("omar") && password.equals("1234")){
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
		}*/
	}

	// To be called, if an unexpected response is returned from the server.
	// Server response must include: 
	// 		{success: boolean, message: string, token: string/null}
	public void failOnUnexpectedResponse(){
		Toast.makeText(LoginActivity.this, 
			"Unexpected login error occurred. Please Try again later",
			Toast.LENGTH_SHORT).show();
	}

	// To display a message passed as a toast (LENGTH_SHORT).
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