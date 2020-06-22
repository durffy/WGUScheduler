package com.example.wguscheduler.activities;

import android.os.Bundle;

import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.ui.CourseAdapter;
import com.example.wguscheduler.ui.TermAdapter;
import com.example.wguscheduler.viewmodel.CourseViewModel;
import com.example.wguscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.wguscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {
    private static final String TAG = "CourseActivity";
    private CourseViewModel mCourseViewModel;
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //Add toolbar action items
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // add the recycler view for displaying the terms and initialize the view model
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_course);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //observer changes to course_table
        //done: intent to load the related courses
        if(mCourseViewModel.getAllCourses() != null) {

            mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {

                @Override
                public void onChanged(@Nullable final List<CourseEntity> courses) {
                    // Update the cached copy of the words in the adapter.
                    //filter the list of courses to items that match the TermId
                    List<CourseEntity> filteredCourses = new ArrayList<>();
                    courseAdapter.setCourses(courses);
                    for (CourseEntity c : courses) {
                        Log.d(TAG, "onChanged(): c:course "+c.getCourse());
                        Log.d(TAG, "onChanged(): getIntent termId: " + getIntent().getIntExtra("termId", 0));
                        if(c.getTermId()== getIntent().getIntExtra("termId", 0)){
                            filteredCourses.add(c);
                        }
                    }
                    courseAdapter.setCourses(filteredCourses);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_terms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.action_add_term_sample_data){
            addSampleData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSampleData() {
        mCourseViewModel.addSampleData();
    }
}
