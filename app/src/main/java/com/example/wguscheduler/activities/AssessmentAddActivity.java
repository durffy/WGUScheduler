package com.example.wguscheduler.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wguscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAddActivity extends AppCompatActivity {

    private EditText mEditTitle, mEditNotes, mScheduledDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);

        //prep the toolbar and set the return activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init the elements
        mEditTitle = findViewById(R.id.edit_assessment_add_title);
        mEditNotes = findViewById(R.id.edit_assessment_add_notes);

    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
