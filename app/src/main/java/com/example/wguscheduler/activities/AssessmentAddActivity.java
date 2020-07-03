package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsSeekBar;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;
import com.example.wguscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssessmentAddActivity extends AppCompatActivity {

    AssessmentViewModel mAssessmentViewModel;
    private EditText mEditTitle, mEditNotes, mScheduledDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);

        //prep the toolbar and set the return activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init the elements
        mEditTitle = findViewById(R.id.edit_assessment_add_title);
        mEditNotes = findViewById(R.id.edit_assessment_add_notes);
        mScheduledDate = findViewById(R.id.edit_assessment_add_scheduled_date);

        //init the view model
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    saveAssessment();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    //int mCourseId, String mTitle, String mNotes, Date mScheduledDate
    private void saveAssessment() throws ParseException {
        int courseId = getIntent().getIntExtra("courseId", 0);
        String title = mEditTitle.getText().toString();
        String notes = mEditNotes.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date scheduledDate = formatter.parse(mScheduledDate.getText().toString());

        AssessmentEntity assessment = new AssessmentEntity(courseId, title, notes, scheduledDate);
        mAssessmentViewModel.saveAssessment(assessment);
        onSupportNavigateUp();
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


}
