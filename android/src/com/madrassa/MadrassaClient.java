package com.madrassa;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Header;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;

import com.madrassa.response.AuthResponse;

public interface MadrassaClient{

	@FormUrlEncoded
	@POST("/api/auth/login")
	Call<AuthResponse> login(@Header("User-Agent") String userAgent,
		@FieldMap Map<String, String> credentials);
}