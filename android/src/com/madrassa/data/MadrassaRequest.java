package  com.madrassa.data;

import com.madrassa.service.MadrassaService;
import com.madrassa.model.Student;
import com.madrassa.model.Course;
import com.madrassa.response.CourseListResponse;
import com.madrassa.response.CourseResponse;
import com.madrassa.AppRepository;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import android.util.Log;

public class MadrassaRequest{

	private Retrofit retrofit;
	private MadrassaService service;
	private OkHttpClient clien;
	private final int BASE_URL = "http://192.168.1.103/api";
	private String authHeader;
	public static final String TAG = "madrassa";
	private AppRepository repo;

	public MadrassaRequest(){
		repo       = AppRepository.getInstance(getApplicationContext());
		authHeader = repo.getAuthHeader();

		httpClient = buildClient();		

		retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(httpClient)
			.addConverterFactory(GsonConverterFactory.create()) 
			.build();

		service = retrofit.create(MadrassaService.class);

		getCourse(7);
	}

	// Intercepts the OkHttpClient requests to add authorization headers. 
	private OkHttpClient buildClient(){
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		clientBuilder.addInterceptor(new Interceptor(){
			@Override
			public Response intercept(Interceptor.Chain chain){
				Request originalRequest = chain.request();

				Request.Builder requestBuilder = originalRequest.newBuilder()
					.header("x-auth-header", authHeader)
					.header("User-Agent", "Madrassa-Client");

				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		});

		OkHttpClient client = clientBuilder.build();
		return client;
	}

	public void getCourse(int id){
		Call<CourseResponse> call = service.getCourse(id);
		call.enqueue(new Callback<CourseResponse>(){
			@Override
			public void onResponse(Call<CourseResponse> call, 
				Response<CourseResponse> response)
			{
				CourseResponse courseResponse = response.body();
				Log.i(TAG, courseResponse.getCourse().getTitle());
			}

			@Override
			public void onFailure(Call<CourseResponse> call, Throwable t){
				Log.i(TAG, "Grave error occurred");
			}
		});
	}

	public void getCourses(){

	}
}