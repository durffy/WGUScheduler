package com.example.wguscheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguscheduler.R;
import com.example.wguscheduler.activities.CourseDetailsActivity;
import com.example.wguscheduler.entities.CourseEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder {

        //create the variables to be referenced latter here, these should be all of the views that are in the recycler list
        private final TextView mCourseTextTitle;
        private final CardView mCourseCardItem;

        private ViewHolder(View itemView){
            super(itemView);

            mCourseTextTitle = itemView.findViewById(R.id.text_course_title_output);
            mCourseCardItem = itemView.findViewById(R.id.card_course_item);

            //onclick event for handling clicking course in the course activity
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final CourseEntity current = mCourses.get(position);
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                //details of the course class to the intent
                intent.putExtra("courseId", current.getId());
                intent.putExtra("mentorId", current.getMentorId());
                intent.putExtra("termId", current.getTermId());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("title",current.getTitle());
                intent.putExtra("startDate",formatter.format(current.getStartDate()));
                intent.putExtra("endDate",formatter.format(current.getEndDate()));

                context.startActivity(intent);

            });

        }
    }
    // declare the inflater, an context and the terms
    private final Context context;
    private final LayoutInflater mInflater;
    private List<CourseEntity> mCourses = new ArrayList<>();

    // inflate the context
    public CourseAdapter(Context context, List<CourseEntity> courses) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mCourses = courses;
    }

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //called each time a viewholder has to be created
    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View courseView = inflater.inflate(R.layout.course_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(courseView);

        return viewHolder;
    }

    // Bind the Textview to the viewholder, e.g. display elements.
    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder courseViewHolder, int position) {

        if(mCourses != null){
            CourseEntity course = mCourses.get(position);
            CardView cardView = courseViewHolder.mCourseCardItem;
            TextView textViewTitle = courseViewHolder.mCourseTextTitle;
            textViewTitle.setText(course.getTitle());
        }else {

            courseViewHolder.mCourseTextTitle.setText("No Courses!");

        }

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    //update the data in the course list
    public void setCourses(List<CourseEntity> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }


}
