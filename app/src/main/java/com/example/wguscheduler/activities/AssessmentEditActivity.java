package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssessmentEditActivity extends AppCompatActivity {

    private AssessmentViewModel mAssessmentViewModel;
    private AssessmentEntity mAssessment;
    private EditText editAssessmentTitle,
            editAssessmentNotes,
            editAssessmentScheduledDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAssessmentDetails();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
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

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    //C.R.U.D.
    //create
    //read
    private void loadAssessmentDetails() {
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        editAssessmentTitle = findViewById(R.id.edit_assessment_edit_title);
        editAssessmentScheduledDate = findViewById(R.id.edit_assessment_edit_scheduled_date);
        editAssessmentNotes = findViewById(R.id.edit_assessment_edit_notes);

        if(mAssessmentViewModel.getAllAssessments() != null) {
            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    for (AssessmentEntity a : assessments) {
                        if(a.getId()== getIntent().getIntExtra("assessmentId", 0)){
                            editAssessmentTitle.setText(a.getTitle());
                            editAssessmentScheduledDate.setText(formatter.format(a.getScheduledDate()));
                            editAssessmentNotes.setText(a.getNotes());
                            mAssessment = a;
                        }
                    }
                }
            });
        }
    }
    //update
    private void saveAssessment() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        String title = editAssessmentTitle.getText().toString();
        Date scheduledDate = formatter.parse(editAssessmentScheduledDate.getText().toString());
        String notes = editAssessmentNotes.getText().toString();

        mAssessment.setTitle(title);
        mAssessment.setScheduledDate(scheduledDate);
        mAssessment.setNotes(notes);

        mAssessmentViewModel.saveAssessment(mAssessment);
        onSupportNavigateUp();
    }
    //delete
}
