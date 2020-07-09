package com.example.wguscheduler.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseAddActivity extends AppCompatActivity {

    private static final String TAG = "CourseAddActivity";
    private CourseViewModel mCourseViewModel;
    private MentorViewModel mMentorViewModel;
    private EditText mCourseTitle, mCourseStartDate, mCourseEndDate,mCourseNotes,
        mMentorFirstName, mMentorLastName, mMentorEmail, mMentorPhone;
    private MentorEntity mMentor;
    private Calendar mEndCalendar = Calendar.getInstance();
    private Calendar mStartCalendar = Calendar.getInstance();

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
        mCourseNotes = findViewById(R.id.edit_course_add_notes);
        mMentorFirstName = findViewById(R.id.edit_mentor_add_first);
        mMentorLastName = findViewById(R.id.edit_mentor_add_last);
        mMentorEmail = findViewById(R.id.edit_mentor_add_email);
        mMentorPhone = findViewById(R.id.edit_mentor_add_phone);

        try {
            loadStartDatePicker();
            loadEndDatePicker();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        mCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseAddActivity.this,date, mStartCalendar.get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                        mStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mCourseStartDate.setText(sdf.format(mStartCalendar.getTime()));

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

        mCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseAddActivity.this,date, mEndCalendar.get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                        mEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mCourseEndDate.setText(sdf.format(mEndCalendar.getTime()));
    }

    private void saveCourse() throws ParseException{

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        verifyMentor();
        int termId = getIntent().getIntExtra("termId", 0);
        //int mentorId = mMentor.getId();
        String title = mCourseTitle.getText().toString();
        String status = "Plan to Take";
        Date start = formatter.parse(mCourseStartDate.getText().toString());
        Date end = formatter.parse(mCourseEndDate.getText().toString());
        String notes = mCourseNotes.getText().toString();

        CourseEntity course = new CourseEntity(termId, mMentor.getId(), title, status, start, end, notes);
        Log.d(TAG, "saveCourse: " + course.getCourse());
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
