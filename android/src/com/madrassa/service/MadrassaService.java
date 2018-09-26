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

import io.reactivex.Single;
import io.reactivex.Observable;

import com.madrassa.response.CourseResponse;
import com.madrassa.response.CourseListResponse;

/* Request should be intercepted, and altered with the proper token */
public interface MadrassaService{

	@GET("/api/course")
	Single<CourseListResponse> getCourseList();

	@GET("/api/course/{id}")
	Single<CourseResponse> getCourse(@Path("id") int id);
}