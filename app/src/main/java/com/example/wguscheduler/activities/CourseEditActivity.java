package com.example.wguscheduler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.MentorEntity;
import com.example.wguscheduler.viewmodel.CourseViewModel;
import com.example.wguscheduler.viewmodel.MentorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseEditActivity extends AppCompatActivity {

    private static final String TAG = "CourseEditActivity";
    private CourseViewModel mCourseViewModel;
    private MentorViewModel mMentorViewModel;

    private MentorEntity mMentor;
    private CourseEntity mCourse;

    private EditText mEditCourseName, mEditCourseStatus, mEditCourseStart, mEditCourseEnd, mEditCourseNotes;
    private EditText mEditMentorFirstName, mEditMentorLastName, mEditMentorEmail, mEditMentorPhone;
    private Calendar mEndCalendar = Calendar.getInstance();
    private Calendar mStartCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);

        //prep the toolbar and set the return activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadCourseDetails();
        loadMentorDetails();


        try {
            loadStartDatePicker();
            loadEndDatePicker();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    updateMentor();
                    updateCourse();
                    onSupportNavigateUp();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    private void loadStartDatePicker() throws ParseException {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mStartCalendar.set(Calendar.YEAR, year);
                mStartCalendar.set(Calendar.MONTH, monthOfYear);
                mStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();

            }
        };

        mEditCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseEditActivity.this,date, mStartCalendar.get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                        mStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEditCourseStart.setText(sdf.format(mStartCalendar.getTime()));

    }

    private void loadEndDatePicker() throws ParseException {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mEndCalendar.set(Calendar.YEAR, year);
                mEndCalendar.set(Calendar.MONTH, monthOfYear);
                mEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        mEditCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseEditActivity.this,date, mEndCalendar.get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                        mEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEditCourseEnd.setText(sdf.format(mEndCalendar.getTime()));
        Log.d(TAG, "updateEndLabel: " + sdf.format(mEndCalendar.getTime()));
    }


    private void loadCourseDetails() {
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mEditCourseName = findViewById(R.id.edit_course_edit_name);
        mEditCourseStatus = findViewById(R.id.edit_course_edit_status);
        mEditCourseStart = findViewById(R.id.edit_course_edit_start);
        mEditCourseEnd = findViewById(R.id.edit_course_edit_end);
        mEditCourseNotes = findViewById(R.id.edit_course_edit_notes);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if(mCourseViewModel.getAllCourses() != null) {
            mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
                @Override
                public void onChanged(@Nullable final List<CourseEntity> courses) {
                    for (CourseEntity c: courses) {
                        if(c.getId()== getIntent().getIntExtra("courseId", 0)){

                            mEditCourseName.setText(c.getTitle());
                            mEditCourseStatus.setText(c.getStatus());
                            mEditCourseStart.setText(formatter.format(c.getStartDate()));
                            mEditCourseEnd.setText(formatter.format(c.getEndDate()));
                            mEditCourseNotes.setText(c.getNotes());
                            mCourse = c;
                            mStartCalendar.setTime(mCourse.getStartDate());
                            mEndCalendar.setTime(mCourse.getEndDate());
                        }
                    }
                }
            });
        }
    }

    private void loadMentorDetails() {
        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        mEditMentorFirstName = findViewById(R.id.edit_mentor_edit_first);
        mEditMentorLastName = findViewById(R.id.edit_mentor_edit_last);
        mEditMentorEmail = findViewById(R.id.edit_mentor_edit_email);
        mEditMentorPhone  = findViewById(R.id.edit_mentor_edit_phone);

        if(mMentorViewModel.getAllMentors() != null) {
            mMentorViewModel.getAllMentors().observe(this, new Observer<List<MentorEntity>>() {
                @Override
                public void onChanged(@Nullable final List<MentorEntity> mentors) {
                    for (MentorEntity m : mentors) {

                        if(m.getId()== getIntent().getLongExtra("mentorId", 0)){
                            mMentor = m;
                            mEditMentorFirstName.setText(m.getFirstName());
                            mEditMentorLastName.setText(m.getLastName());
                            mEditMentorPhone.setText(m.getPhone());
                            mEditMentorEmail.setText(m.getEmail());
                        }
                    }
                }
            });
        }
    }

    private void updateMentor() {

        String first = mEditMentorFirstName.getText().toString();
        String last = mEditMentorLastName.getText().toString();
        String email = mEditMentorEmail.getText().toString();
        String phone = mEditMentorPhone.getText().toString();

        mMentor.setFirstName(first);
        mMentor.setLastName(last);
        mMentor.setEmail(email);
        mMentor.setPhone(phone);

        mMentorViewModel.saveMentor(mMentor);

    }

    private void updateCourse() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String name = mEditCourseName.getText().toString();
        String status = mEditCourseStatus.getText().toString();
        Date start = formatter.parse(mEditCourseStart.getText().toString());
        Date end = formatter.parse(mEditCourseEnd.getText().toString());
        String notes = mEditCourseNotes.getText().toString();

        Log.d(TAG, "updateCourse: "+ formatter.parse(mEditCourseStart.getText().toString()));

        mCourse.setTitle(name);
        mCourse.setStatus(status);
        mCourse.setStartDate(start);
        mCourse.setEndDate(end);
        mCourse.setNotes(notes);

        mCourseViewModel.saveCourse(mCourse);
    }

}
