package com.example.wguscheduler.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.wguscheduler.entities.MentorEntity;
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

import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
            //course textViews
    private TextView textViewCourseTitle, textViewStartDate, textViewEndDate, textViewStatus, textViewNotes,
            //mentor textViews
            textViewMentor, textViewMentorPhone, textViewMentorEmail;
    private MentorViewModel mMentorViewModel;
    private CourseViewModel mCourseViewModel;



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
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.item_term_delete){
            deleteCourse(getIntent().getIntExtra("courseId",0));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCourse(int courseId) {
        //build the alert message
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this);
        builder.setTitle("Term Delete");
        builder.setMessage("Deleting this term will delete all associated course data. Do you want to proceed with the delete?");
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

    public void loadCourseDetails(){
        textViewCourseTitle = findViewById(R.id.text_course_add_title);
        textViewStartDate = findViewById(R.id.text_course_start_date_output);
        textViewEndDate = findViewById(R.id.text_course_end_date_output);
        textViewStatus = findViewById(R.id.text_course_status_output);
        textViewNotes = findViewById(R.id.text_course_notes_output);

        if(getIntent().getStringExtra("title") != null){
            textViewCourseTitle.setText(getIntent().getStringExtra("title"));
            textViewStartDate.setText(getIntent().getStringExtra("startDate"));
            textViewEndDate.setText(getIntent().getStringExtra("endDate"));
            textViewStatus.setText(getIntent().getStringExtra("status"));
            textViewNotes.setText(getIntent().getStringExtra("notes"));
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

                        if(m.getId()== getIntent().getIntExtra("mentorId", 0)){
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

}
