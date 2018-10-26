package com.madrassa;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.os.Bundle;

public class CourseDetailFragment extends Fragment {

	public CourseDetailFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_course_detail, container,
			false);
	}
}