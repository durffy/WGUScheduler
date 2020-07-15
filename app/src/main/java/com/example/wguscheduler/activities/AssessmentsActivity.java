package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.ui.AssessmentAdapter;
import com.example.wguscheduler.ui.CourseAdapter;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;
import com.example.wguscheduler.viewmodel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.example.wguscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentsActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private AssessmentViewModel mAssessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        //prep the toolbar and set the return activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize the view model
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        // add the recycler view for displaying the terms and initialize the view model
        RecyclerView recyclerView = findViewById(R.id.recycler_assessment);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentsActivity.this, AssessmentAddActivity.class);
                intent.putExtra("courseId", getIntent().getIntExtra("courseId", 0));
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        //observer changes to course_table
        if(mAssessmentViewModel.getAllAssessments() != null) {

            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {

                @Override
                public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                    // Update the cached copy of the words in the adapter.
                    //filter the list of courses to items that match the TermId
                    List<AssessmentEntity> filteredAssessments = new ArrayList<>();
                    assessmentAdapter.setCourses(assessments);

                    for (AssessmentEntity a : assessments) {
                        if(a.getCourseId()== getIntent().getIntExtra("courseId", 0)){
                            filteredAssessments.add(a);
                        }
                    }

                    if(filteredAssessments.size() == 5){
                        fab.hide();
                    }else{
                        fab.show();
                    }
                    assessmentAdapter.setCourses(filteredAssessments);

                }
            });
        }

    }


    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
