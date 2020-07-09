package com.example.wguscheduler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsSeekBar;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentAddActivity extends AppCompatActivity {

    AssessmentViewModel mAssessmentViewModel;
    private EditText mEditTitle, mEditNotes, mScheduledDate;
    private Calendar mScheduleCalendar = Calendar.getInstance();

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

        loadScheduleDatePicker();

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

    private void loadScheduleDatePicker() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mScheduleCalendar.set(Calendar.YEAR, year);
                mScheduleCalendar.set(Calendar.MONTH, monthOfYear);
                mScheduleCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateScheduleLabel();

            }
        };

        mScheduledDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentAddActivity.this, date, mScheduleCalendar.get(Calendar.YEAR), mScheduleCalendar.get(Calendar.MONTH),
                        mScheduleCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateScheduleLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mScheduledDate.setText(sdf.format(mScheduleCalendar.getTime()));
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
