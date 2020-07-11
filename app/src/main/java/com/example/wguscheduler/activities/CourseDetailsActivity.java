package com.example.wguscheduler.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.MentorEntity;
import com.example.wguscheduler.utilities.NotificationReceiver;
import com.example.wguscheduler.viewmodel.CourseViewModel;
import com.example.wguscheduler.viewmodel.MentorViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wguscheduler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "CourseDetailsActivity";
    //course textViews
    private TextView textViewCourseTitle, textViewStartDate, textViewEndDate, textViewStatus, textViewNotes,
            //mentor textViews
            textViewMentor, textViewMentorPhone, textViewMentorEmail;
    private MentorViewModel mMentorViewModel;
    private CourseViewModel mCourseViewModel;
    private CourseEntity mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        //prep the toolbar and set the return activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);;

        loadCourseDetails();
        loadMentorDetails();

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sharing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.item_delete) {
            deleteCourse(getIntent().getIntExtra("courseId", 0));
            return true;
        }else if (id == R.id.item_share){
            share();
            return true;
        }else if( id == R.id.item_edit){
            editCourse();
            return true;
        }else if( id == R.id.item_notification) {
            notification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void notification() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(CourseDetailsActivity.this, NotificationReceiver.class);
        intent.putExtra("key", "Test Short Message");
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetailsActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        cal.setTime(mCourse.getEndDate());
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , sender);
    }

    private void share() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, mCourse.getCourse());
        intent.putExtra(Intent.EXTRA_TITLE, mCourse.getTitle());
        intent.setType("text/plain");

        Intent share = Intent.createChooser(intent, null);
        startActivity(share);
    }

    //CRUD
    //create
    //read
    public void loadCourseDetails(){
        textViewCourseTitle = findViewById(R.id.text_course_add_title);
        textViewStartDate = findViewById(R.id.text_course_start_date_output);
        textViewEndDate = findViewById(R.id.text_course_end_date_output);
        textViewStatus = findViewById(R.id.text_course_status_output);
        textViewNotes = findViewById(R.id.text_course_notes_output);

        if(mCourseViewModel.getAllCourses() != null) {

            mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {

                @Override
                public void onChanged(@Nullable final List<CourseEntity> courses) {
                    // Update the cached copy of the words in the adapter.
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    for (CourseEntity c : courses) {
                        if(c.getId()== getIntent().getIntExtra("courseId", 0)){
                            textViewCourseTitle.setText(c.getTitle());
                            textViewStartDate.setText(formatter.format(c.getStartDate()));
                            textViewEndDate.setText(formatter.format(c.getEndDate()));
                            textViewStatus.setText(c.getStatus());
                            textViewNotes.setText(c.getNotes());

                            mCourse = c;
                        }
                    }
                }
            });
        }

    }

    private void loadMentorDetails() {
        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        textViewMentor = findViewById(R.id.text_mentor_name_output);
        textViewMentorPhone =findViewById(R.id.text_mentor_phone_output);
        textViewMentorEmail =findViewById(R.id.text_mentor_email_output);
        textViewNotes = findViewById(R.id.text_course_notes_output);

        if(mMentorViewModel.getAllMentors() != null) {

            mMentorViewModel.getAllMentors().observe(this, new Observer<List<MentorEntity>>() {

                @Override
                public void onChanged(@Nullable final List<MentorEntity> mentors) {
                    // Update the cached copy of the words in the adapter.

                    for (MentorEntity m : mentors) {

                        if(m.getId()== getIntent().getLongExtra("mentorId", 0)){
                            //todo: split into first and last name
                            String name = m.getFirstName() + " " + m.getLastName();
                            textViewMentor.setText(name);
                            textViewMentorPhone.setText(m.getPhone());
                            textViewMentorEmail.setText(m.getEmail());

                        }
                    }
                }
            });
        }
    }
    //update
    private void editCourse() {
        Intent intent = new Intent(CourseDetailsActivity.this, CourseEditActivity.class);
        intent.putExtra("courseId", getIntent().getIntExtra("courseId",0));
        intent.putExtra("mentorId", getIntent().getLongExtra("mentorId",0));

        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }
    //delete
    private void deleteCourse(int courseId) {
        //build the alert message
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this);
        builder.setTitle("Course Delete");
        builder.setMessage("Deleting this Course will delete all associated Assessment data. Do you want to proceed with the delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCourseViewModel.deleteCourse(courseId);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
