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

public class TermDetailsActivity extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private TextView textViewTermTitle,
            textViewStartDate,
            textViewEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: intent for Course
        //TODO: get all courses and filter down the courses by the intent extras
        loadTermDetails();

        Button buttonCourses = findViewById(R.id.button_courses);
        buttonCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetailsActivity.this, CoursesActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    /*
    set the fields from the intent extras

     */
    public void loadTermDetails(){
        textViewTermTitle = findViewById(R.id.label_term_title);
        textViewStartDate = findViewById(R.id.label_term_start_date_output);
        textViewEndDate = findViewById(R.id.label_term_end_date_output);
        if(getIntent().getStringExtra("title") != null){
            textViewTermTitle.setText(getIntent().getStringExtra("title"));
            textViewStartDate.setText(getIntent().getStringExtra("startDate"));
            textViewEndDate.setText(getIntent().getStringExtra("endDate"));
        }
    }

}
