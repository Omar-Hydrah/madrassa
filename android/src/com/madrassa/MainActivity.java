package com.madrassa;

import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import android.widget.Toast;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.util.Log;

import com.madrassa.model.User;
import com.madrassa.AppRepository;
import com.madrassa.util.Constants;

public class MainActivity extends AppCompatActivity
{
	private CoordinatorLayout mainLayout;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private AppRepository repo;
    public static final String TAG = Constants.LOG_TAG;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = AppRepository.getInstance(getApplicationContext());
        // Assume the user is logged-in
        if(repo.getAuthHeader()!= null && repo.getUserName() != null ){
            startActivity(new Intent(this, HomeActivity.class));
        }

        mainLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent loginIntent = 
                    new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	getMenuInflater().inflate(R.menu.menu_main, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int id = item.getItemId();

    	switch(id){
    		case R.id.action_settings:
    			Snackbar.make(mainLayout, "Settings clicked", 
    				Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    			break;
    	}

    	return true;
    }
}
