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
import com.example.wguscheduler.activities.CoursesActivity;
import com.example.wguscheduler.entities.CourseEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder {

        //create the variables to be referenced latter here, these should be all of the views that are in the recycler list
        private final TextView mCourseTextTitle;
        private final TextView mCourseStartDate;
        private final TextView mCourseEndDate;
        private final CardView mCourseCardItem;

        private ViewHolder(View itemView){
            super(itemView);

            mCourseTextTitle = itemView.findViewById(R.id.text_course_title_output);
            mCourseCardItem = itemView.findViewById(R.id.card_course_item);
            mCourseStartDate = itemView.findViewById(R.id.text_course_start_date_output);
            mCourseEndDate = itemView.findViewById(R.id.text_course_end_date_output);;

            //onclick event for handling clicking terms in the terms activity
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final CourseEntity current = mCourses.get(position);
                Intent intent = new Intent(context, CoursesActivity.class);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                //details of the term class to the intent
                intent.putExtra("courseId", current.getId());
                intent.putExtra("mentorId", current.getMentorId());
                intent.putExtra("termId", current.getTermId());
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
    public void onBindViewHolder(CourseAdapter.ViewHolder termViewHolder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        if(mCourses != null){
            CourseEntity course = mCourses.get(position);
            CardView cardView = termViewHolder.mCourseCardItem;
            TextView textViewTitle = termViewHolder.mCourseTextTitle;
            TextView textViewStart = termViewHolder.mCourseStartDate;
            TextView textViewEnd = termViewHolder.mCourseEndDate;

            textViewTitle.setText(course.getTitle());
            textViewStart.setText(formatter.format(course.getStartDate()));
            textViewEnd.setText(formatter.format(course.getEndDate()));


        }else {

            termViewHolder.mCourseTextTitle.setText("No Terms!");

        }

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    //update the data in the terms list
    public void setCourses(List<CourseEntity> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }


}
