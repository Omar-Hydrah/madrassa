package com.madrassa.service;

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
import com.madrassa.response.CourseResponse;
import com.madrassa.response.CourseListResponse;

public interface MadrassaClient{

	@FormUrlEncoded
	@POST("/auth/login")
	Call<AuthResponse> login(@Header("User-Agent") String userAgent,
		@FieldMap Map<String, String> credentials);

	@GET("/course")
	Call<CourseListResponse> getCourses(@Header("User-Agent") String userAgent);

	@GET("/course/{id}")
	Call<CourseResponse> getCourse(@Header("User-Agent") String userAgent, 
		@Path("id") int id);
}