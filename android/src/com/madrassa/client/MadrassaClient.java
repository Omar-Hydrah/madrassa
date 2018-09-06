package com.madrassa.client;

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

import com.madrassa.response.CourseResponse;
import com.madrassa.response.CourseListResponse;

/* Request should be intercepted, and altered with the proper token */
public interface MadrassaClient{

	@GET("/course")
	Call<CourseListResponse> getCourses();

	@GET("/course/{id}")
	Call<CourseResponse> getCourse(@Path("id") int id);
}