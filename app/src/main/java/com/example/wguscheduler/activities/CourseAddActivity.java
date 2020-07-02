package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
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
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.viewmodel.CourseViewModel;
import com.example.wguscheduler.viewmodel.MentorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseAddActivity extends AppCompatActivity {

    private static final String TAG = "CourseAddActivity";
    private CourseViewModel mCourseViewModel;
    private MentorViewModel mMentorViewModel;
    private EditText mCourseTitle, mCourseStartDate, mCourseEndDate,
        mMentorFirstName, mMentorLastName, mMentorEmail, mMentorPhone;
    private MentorEntity mMentor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_add);

        //initialize the tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize the view model
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);

        //initialize EditText views
        mCourseTitle = findViewById(R.id.edit_course_add_title);
        mCourseStartDate = findViewById(R.id.edit_course_add_start);
        mCourseEndDate = findViewById(R.id.edit_course_add_end);
        mMentorFirstName = findViewById(R.id.edit_mentor_first);
        mMentorLastName = findViewById(R.id.edit_mentor_last);
        mMentorEmail = findViewById(R.id.edit_mentor_email);
        mMentorPhone = findViewById(R.id.edit_mentor_phone);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveCourse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveCourse() throws ParseException{

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        verifyMentor();
        int termId = getIntent().getIntExtra("termId", 0);
        //int mentorId = mentor.getMentorId();
        int mentorId = mMentor.getId();
        String title = mCourseTitle.getText().toString();
        String status = "Plan to Take";
        Date start = formatter.parse(mCourseStartDate.getText().toString());
        Date end = formatter.parse(mCourseEndDate.getText().toString());

        CourseEntity course = new CourseEntity(termId, mentorId, title, status, start, end);
        mCourseViewModel.saveCourse(course);
        onSupportNavigateUp();
    }


    private void verifyMentor() {

        mMentorViewModel.getAllMentors().observe(this, new Observer<List<MentorEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MentorEntity> mentors) {
                String first = mMentorFirstName.getText().toString();
                String last = mMentorLastName.getText().toString();
                String phone = mMentorPhone.getText().toString();
                String email = mMentorEmail.getText().toString();

                //filter by email, emails should be unique to each person
                mMentor = mentors.stream().filter(m -> m.getEmail().contains(email))
                        .findFirst().orElse(mMentor = new MentorEntity(mentors.size() + 1, first, last, phone, email));
                Log.d(TAG, "VerifyMentor().onChanged(): "+ mMentor.getId());
                saveMentor(mMentor);
            }
        });

    }

    private void saveMentor(MentorEntity mentorEntity) {
        mMentorViewModel.saveMentor(mentorEntity);
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
