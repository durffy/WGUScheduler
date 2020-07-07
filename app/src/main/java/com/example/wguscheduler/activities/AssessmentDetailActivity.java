package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class AssessmentDetailActivity extends AppCompatActivity{

    private AssessmentViewModel mAssessmentViewModel;
    private TextView textViewAssessmentTitle,
        textViewAssessmentNotes,
        textViewAssessmentScheduledDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAssessmentDetails();

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
        if( id == R.id.item_delete){
            deleteCourse(getIntent().getIntExtra("assessmentId",0));
            return true;
        }else if( id == R.id.item_edit){
            editCourse();
        }

        return super.onOptionsItemSelected(item);
    }

    //C.R.U.D.
    //create
    //read
    private void loadAssessmentDetails() {
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        textViewAssessmentTitle = findViewById(R.id.text_assessment_title);
        textViewAssessmentScheduledDate = findViewById(R.id.text_assessment_scheduled_date_output);
        textViewAssessmentNotes = findViewById(R.id.text_assessment_notes_output);

        if(mAssessmentViewModel.getAllAssessments() != null) {
            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    for (AssessmentEntity a : assessments) {
                        if(a.getId()== getIntent().getIntExtra("assessmentId", 0)){
                            textViewAssessmentTitle.setText(a.getTitle());
                            textViewAssessmentScheduledDate.setText(formatter.format(a.getScheduledDate()));
                            textViewAssessmentNotes.setText(a.getNotes());
                        }
                    }
                }
            });
        }
    }
    //update
    private void editCourse() {
        onSupportNavigateUp();
    }
    //delete
    private void deleteCourse(int assessmentId) {
        mAssessmentViewModel.deleteAssessment(assessmentId);
        onSupportNavigateUp();
    }




}
