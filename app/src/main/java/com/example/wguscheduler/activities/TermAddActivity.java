package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.database.SchedulerRepository;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TermAddActivity extends AppCompatActivity {

    private EditText mTermTitle, mTermStart, mTermEnd;
    private TermViewModel mTermViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        //setup the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);

        //initialize the input fields
        mTermTitle = findViewById(R.id.edit_term_add_title);
        mTermStart = findViewById(R.id.edit_term_add_start);
        mTermEnd = findViewById(R.id.edit_term_add_end);

        //initialize the view model
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveTerm() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        int id = getIntent().getIntExtra("lastTermId", 0);
        String title = mTermTitle.getText().toString();
        Date start = formatter.parse(mTermStart.getText().toString());
        Date end = formatter.parse(mTermEnd.getText().toString());

        TermEntity term = new TermEntity(id, title, start, end);
        mTermViewModel.saveTerm(term);
        onSupportNavigateUp();
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
