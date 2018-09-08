package com.madrassa.data;

import android.widget.Toast;
import android.util.Log;

import com.madrassa.response.AuthResponse;
import com.madrassa.service.AuthService;
import com.madrassa.model.User;
import com.madrassa.util.Constants;

import okhttp3.ResponseBody;
import okhttp3.OkHttpClient;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

// Used as a layer to to perform authentication using service.AuthService.
public class AuthRequest{

	private Retrofit retrofit;
	private String BASE_URL = Constants.BASE_URL;
	private String USER_AGENT = Constants.USER_AGENT;
	public static final String TAG = "madrassa";


	public AuthRequest(){


		retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build();
	}

	// Returns a Single, to be used in the AppRepository.
	public Single<AuthResponse> requestLogin(Map<String, String> credentials){
		AuthService service = retrofit.create(AuthService.class);
		/*Call<AuthResponse> call = service.login(USER_AGENT, credentials);
		call.enqueue(new Callback<AuthResponse>(){

			@Override
			public void onResponse(Call<AuthResponse> call, 
				Response<AuthResponse> response)
			{
				AuthResponse authResponse = response.body();
				
			}

			@Override
			public void onFailure(Call<AuthResponse> call, Throwable t){
				Log.i(TAG, "Login error");
			}
		});*/
		return service.login(credentials);
	}

	public OkHttpClient buildClient(){
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder(); 
		clientBuilder.addInterceptor(new Interceptor(){
			@Override
			public okhttp3.Response intercept(Interceptor.Chain chain) 
				throws IOException
			{
				Request originalRequest = chain.request();
				Request.Builder requestBuilder = originalRequest.newBuilder()
					.header("User-Agent", USER_AGENT);
				Request request = requestBuilder.build();

				return chain.proceed(request);
			}
		});

		HttpLoggingInterceptor loggingInterceptor 
			= new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		clientBuilder.addInterceptor(loggingInterceptor);

		OkHttpClient client = clientBuilder.build();
		return client;
	}
}