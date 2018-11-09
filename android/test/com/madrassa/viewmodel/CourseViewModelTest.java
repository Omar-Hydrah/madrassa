package com.madrassa.viewmodel;

import com.madrassa.AppRepository;
import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.response.CourseResponse;
import com.madrassa.response.BooleanResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

import io.reactivex.subscribers.TestSubscriber;
import io.reactivex.observers.TestObserver;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Single;

public class CourseViewModelTest{
	@Mock
	private AppRepository repo;

	private CourseViewModel courseVM;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);

		courseVM = new CourseViewModel(repo);
	}


	@Test
	public void getCourse_returnsCourseResponseByCourseId(){
		Course course = new Course(1, 1, "Teacher", "Title", "Description");
		CourseResponse courseResponse = 
			new CourseResponse("message", true, course, null);

		Single<CourseResponse> response = Single.just(courseResponse);

		Mockito.when(repo.getCourse(course.getId())).thenReturn(response);

		TestObserver<CourseResponse> observer = response.test();
		// TestObserver<CourseResponse> observer = new TestObserver<>();
		// TestSubscriber<CourseResponse> subscriber = new TestSubscriber<>();

		// courseVM.getCourse(1).subscribe(observer);

		observer.assertNoErrors();
		observer.assertValue(courseResponse);
		// assertNotNull(response);
	}

	// API's tasks
	@Test
	public void joinCourse_succeedsForStudents(){
		User student = new User(1, "username", "first name", "last name", 
			"student");
		BooleanResponse booleanResponse = new BooleanResponse("success", true);
		Single<BooleanResponse> response = Single.just(booleanResponse);

		Mockito.when(repo.joinCourse(student.getId())).thenReturn(response);
		TestObserver<BooleanResponse> observer = response.test();

		observer.assertNoErrors();
		observer.assertValue(booleanResponse);
	}

	@Test
	public void joinCourse_failsForTeachers(){
		User teacher = new User(1, "username", "first", "last", "teacher");
		BooleanResponse booleanResponse = new BooleanResponse("fail", false);
		Single<BooleanResponse> response = Single.just(booleanResponse);

		Mockito.when(repo.joinCourse(teacher.getId())).thenReturn(response);
		TestObserver<BooleanResponse> observer = response.test();

		observer.assertNoErrors();
		observer.assertValue(booleanResponse);
	}

}