package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wguscheduler.R;

public class CourseDetailsActivity extends AppCompatActivity {
    private TextView textViewCourseTitle,
            textViewStartDate,
            textViewEndDate,
            textViewStatus;
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        //prep the toolbar and set the return activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadCourseDetails();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button buttonAssessments = findViewById(R.id.button_assessments);
        buttonAssessments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(CourseDetailsActivity.this, AssessmentsActivity.class);
                intent.putExtra("courseId", getIntent().getIntExtra("courseId",0));
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void loadCourseDetails(){
        textViewCourseTitle = findViewById(R.id.text_course_title);
        textViewStartDate = findViewById(R.id.text_course_start_date_output);
        textViewEndDate = findViewById(R.id.text_course_end_date_output);
        textViewStatus = findViewById(R.id.text_course_status_output);

        if(getIntent().getStringExtra("title") != null){
            textViewCourseTitle.setText(getIntent().getStringExtra("title"));
            textViewStartDate.setText(getIntent().getStringExtra("startDate"));
            textViewEndDate.setText(getIntent().getStringExtra("endDate"));
            textViewStatus.setText(getIntent().getStringExtra("status"));
        }
    }

}
