package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseEditActivity extends AppCompatActivity {

    private CourseViewModel mCourseViewModel;
    private MentorViewModel mMentorViewModel;

    private MentorEntity mMentor;
    private CourseEntity mCourse;

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
                            mCourse = c;
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

        mCourse.setTitle(name);
        mCourse.setStatus(status);
        mCourse.setStartDate(start);
        mCourse.setEndDate(end);
        mCourse.setNotes(notes);

        mCourseViewModel.saveCourse(mCourse);
    }

}
