package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wguscheduler.R;

public class AssessmentDetailActivity extends AppCompatActivity{
    private TextView textViewAssessmentTitle,
        textViewAssessmentNotes,
        textViewAssessmentScheduledDate;


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

    private void loadAssessmentDetails() {
        textViewAssessmentTitle = findViewById(R.id.text_assessment_title);
        textViewAssessmentScheduledDate = findViewById(R.id.text_assessment_scheduled_date_output);
        textViewAssessmentNotes = findViewById(R.id.text_assessment_notes_output);
        if(getIntent().getStringExtra("title") != null){
            textViewAssessmentTitle.setText(getIntent().getStringExtra("title"));
            textViewAssessmentScheduledDate.setText(getIntent().getStringExtra("scheduledDate"));
            textViewAssessmentNotes.setText(getIntent().getStringExtra("notes"));
        }
    }


}
