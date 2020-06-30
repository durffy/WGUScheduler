package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class CourseAddActivity extends AppCompatActivity {

    private CourseViewModel mCourseViewModel;
    private MentorViewModel mMentorViewModel;
    private EditText mCourseTitle, mCourseStartDate, mCourseEndDate,
        mMentorFirstName, mMentorLastName, mMentorEmail, mMentorPhone;

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

    //(int mTermId, int mMentorId, String mTitle, String mStatus, Date mStartDate, Date mEndDate)
    private void saveCourse() throws ParseException{

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        MentorEntity mentor = verifyMentor();
        int termId = getIntent().getIntExtra("termId", 0);
        //int mentorId = mentor.getMentorId();
        int mentorId = 1;
        String title = mCourseTitle.getText().toString();
        String status = "Plan to Take";
        Date start = formatter.parse(mCourseStartDate.getText().toString());
        Date end = formatter.parse(mCourseEndDate.getText().toString());

        CourseEntity course = new CourseEntity(termId, mentorId, title, status, start, end);
        mCourseViewModel.saveCourse(course);
        onSupportNavigateUp();
    }

    private MentorEntity verifyMentor() {
        MentorEntity mentor = null;
        return mentor;
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
