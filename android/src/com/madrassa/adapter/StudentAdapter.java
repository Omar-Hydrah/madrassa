package com.madrassa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

import com.madrassa.model.User;
import com.madrassa.util.Constants;
import com.madrassa.R;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{

	private List<User> students;
	// private Context context;
	public StudentAdapter(List<User> students){
		this.students = students;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.student_row, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position){
		User student = this.students.get(position);
		holder.textName.setText(student.getName());
	}

	@Override
	public int getItemCount(){
		return students.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		public TextView textName;
		public ViewHolder(View item){
			super(item);
			textName = (TextView) item.findViewById(R.id.text_name);
		}
	}


}