package com.madrassa.service;

import retrofit2.http.FormUrlEncoded;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.Path;

import io.reactivex.Single;
import java.util.Map;

import com.madrassa.response.AuthResponse;

public interface AuthService{
	@FormUrlEncoded
	@POST("/api/auth/login")
	Single<AuthResponse> login(@FieldMap Map<String, String> credentials);

}