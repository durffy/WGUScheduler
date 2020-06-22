package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wguscheduler.R;

public class AssessmentDetailActivity extends AppCompatActivity{
    private TextView textViewAssessmentTitle,
        textViewAssessmentDetails,
        textViewAssessmentType;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAssessmentDetails();
    }

    private void loadAssessmentDetails() {
        textViewAssessmentTitle = findViewById(R.id.text_assessment_title);
        textViewAssessmentDetails = findViewById(R.id.text_assessment_type_output);
        textViewAssessmentDetails = findViewById(R.id.text_assessment_description_output);
        if(getIntent().getStringExtra("title") != null){
            textViewAssessmentTitle.setText(getIntent().getStringExtra("title"));
            textViewAssessmentDetails.setText(getIntent().getStringExtra("type"));
            textViewAssessmentDetails.setText(getIntent().getStringExtra("description"));
        }
    }

}
