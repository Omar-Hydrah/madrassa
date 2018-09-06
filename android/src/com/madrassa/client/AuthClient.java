package com.madrassa.client;

import retrofit2.http.FormUrlEncoded;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.Map;

import com.madrassa.response.AuthResponse;

public interface AuthClient{
	@FormUrlEncoded
	@POST("/auth/login")
	Call<AuthResponse> login(@Header("User-Agent") String userAgent,
		@FieldMap Map<String, String> credentials);
}