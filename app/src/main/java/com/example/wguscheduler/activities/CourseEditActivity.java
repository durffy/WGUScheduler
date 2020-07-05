package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.widget.EditText;

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

import java.text.SimpleDateFormat;
import java.util.List;

public class CourseEditActivity extends AppCompatActivity {

    private CourseViewModel mCourseViewModel;
    private MentorViewModel mMentorViewModel;

    private EditText mEditCourseName, mEditCourseStatus, mEditCourseStart, mEditCourseEnd, mEditCourseNotes;
    private EditText mEditMentorFirstName, mEditMentorLastName, mEditMentorEmail, mEditMentorPhone;

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

    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
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

                        if(m.getId()== getIntent().getIntExtra("mentorId", 0)){
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



}
