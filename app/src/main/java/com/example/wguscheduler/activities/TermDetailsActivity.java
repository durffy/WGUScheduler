package com.example.wguscheduler.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.viewmodel.CourseViewModel;
import com.example.wguscheduler.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private TermViewModel mTermViewModel;
    private CourseViewModel mCourseViewModel;

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

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.item_term_delete){
            deleteTerm(getIntent().getIntExtra("termId",0));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteTerm(int termId) {

        //build the alert message
        AlertDialog.Builder builder = new AlertDialog.Builder(TermDetailsActivity.this);
        builder.setTitle("Term Delete");
        builder.setMessage("Deleting this term will delete all associated course data. Do you want to proceed with the delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTermViewModel.deleteTerm(termId);
                finish();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        //process the delete verification
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {

                @Override
                public void onChanged(@Nullable final List<CourseEntity> courses) {
                    // Update the cached copy of the words in the adapter.
                    //filter the list of courses to items that match the TermId
                    List<CourseEntity> filteredCourses = new ArrayList<>();

                    for (CourseEntity c : courses) {
                        Log.d(TAG, "onChanged(): c:course "+c.getCourse());
                        Log.d(TAG, "onChanged(): getIntent termId: " + getIntent().getIntExtra("termId", 0));
                        if(c.getTermId()== getIntent().getIntExtra("termId", 0)){
                            filteredCourses.add(c);
                        }
                    }

                    if(filteredCourses.isEmpty()){
                        builder.setMessage("Do you want to proceed with the delete?");
                    }
                }
         });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
