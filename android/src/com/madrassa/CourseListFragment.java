package com.madrassa;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class CourseListFragment extends Fragment{

	public CourseListFragment(){

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
		Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_course_list, container, 
			false);
	}
}