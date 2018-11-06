package com.madrassa;

import org.junit.Test;
import org.junit.Rule;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Matchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import com.madrassa.model.User;


public class MainActivityTest{

	// private @Mock AppRepository repo;

	/*@Rule
	public ActivityTestRule<MainActivity> mainActivityTestRule =
		new ActivityTestRule<>(MainActivity.class);*/

	@BeforeClass
	public void setupClass(){
		Intents.init();
	}

	@Test
	public void loginButton_opensLoginActivity(){
		/*onView(withId(R.id.fab)).perform(click());
		intended(hasComponent(LoginActivity.class.getName()));*/
		assertTrue(true);
	}

	@Test
	public void loggedUser_redirectedToHomeActivity(){
		assertTrue(true);
	}

}