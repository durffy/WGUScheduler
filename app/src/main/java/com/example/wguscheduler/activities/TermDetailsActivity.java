package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wguscheduler.R;

public class TermDetailsActivity extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "TermDetailsActivity";
    private TextView textViewTermTitle,
            textViewStartDate,
            textViewEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadTermDetails();

        Button buttonCourses = findViewById(R.id.button_courses);
        buttonCourses.setOnClickListener(new View.OnClickListener() {
            //on click pass set the intent for the term ID and start the activity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetailsActivity.this, CoursesActivity.class);
                Log.d(TAG, "onClick(): getIntent termId: " + getIntent().getIntExtra("termId", 0));
                intent.putExtra("termId", getIntent().getIntExtra("termId", 0));
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    /*
        set the fields from the intent extras
     */
    public void loadTermDetails(){
        textViewTermTitle = findViewById(R.id.text_term_title);
        textViewStartDate = findViewById(R.id.text_term_start_date_output);
        textViewEndDate = findViewById(R.id.text_term_end_date_output);
        if(getIntent().getStringExtra("title") != null){
            textViewTermTitle.setText(getIntent().getStringExtra("title"));
            textViewStartDate.setText(getIntent().getStringExtra("startDate"));
            textViewEndDate.setText(getIntent().getStringExtra("endDate"));
        }
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
