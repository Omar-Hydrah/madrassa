package  com.madrassa.data;

import com.madrassa.AppRepository;
import com.madrassa.MadrassaApplication;
import com.madrassa.service.MadrassaService;
import com.madrassa.model.Student;
import com.madrassa.model.Course;
import com.madrassa.response.CourseListResponse;
import com.madrassa.response.CourseResponse;
import com.madrassa.util.Constants;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;
import java.io.IOException;

import android.util.Log;

public class MadrassaRequest{

	public static final String TAG = "madrassa";
	private final String BASE_URL   = Constants.BASE_URL;
	private final String USER_AGENT = Constants.USER_AGENT;
	private Retrofit        retrofit;
	private MadrassaService service;
	private OkHttpClient    httpClient;
	private String          authHeader;
	// private AppRepository   repo;

	public MadrassaRequest(String authHeader){
		// repo       = AppRepository.getInstance(
		// 	MadrassaApplication.getContext());

		// authHeader = repo.getAuthHeader();
		this.authHeader = authHeader;
		httpClient = buildClient();

		retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(httpClient)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create()) 
			.build();

		service = retrofit.create(MadrassaService.class);

		// getCourse(7);
	}

	// Intercepts the OkHttpClient requests to add authorization headers. 
	private OkHttpClient buildClient(){
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		clientBuilder.addInterceptor(new Interceptor(){
			@Override
			public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException{
				Request originalRequest = chain.request();

				Request.Builder requestBuilder = originalRequest.newBuilder()
					.header("x-auth-token", authHeader)
					.header("User-Agent", USER_AGENT);

				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		});

		OkHttpClient client = clientBuilder.build();
		return client;
	}

	public Single<CourseResponse> getCourse(int id){
		return service.getCourse(id);
	}

	public Single<CourseListResponse> getCourseList(){
		return service.getCourseList();
	}
}