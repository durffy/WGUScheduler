package com.example.wguscheduler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TermAddActivity extends AppCompatActivity {

    private EditText mTermTitle, mTermStart, mTermEnd;
    private TermViewModel mTermViewModel;
    private Calendar mEndCalendar = Calendar.getInstance();
    private Calendar mStartCalendar = Calendar.getInstance();


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

        try {
            loadStartDatePicker();
            loadEndDatePicker();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        String title = mTermTitle.getText().toString();
        Date start = formatter.parse(mTermStart.getText().toString());
        Date end = formatter.parse(mTermEnd.getText().toString());

        TermEntity term = new TermEntity(title, start, end);
        mTermViewModel.saveTerm(term);
        onSupportNavigateUp();
    }

    // add support for going back a screen
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    private void loadStartDatePicker() throws ParseException {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mStartCalendar.set(Calendar.YEAR, year);
                mStartCalendar.set(Calendar.MONTH, monthOfYear);
                mStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();

            }
        };

        mTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermAddActivity.this,date, mStartCalendar.get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                        mStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mTermStart.setText(sdf.format(mStartCalendar.getTime()));

    }

    private void loadEndDatePicker() throws ParseException {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mEndCalendar.set(Calendar.YEAR, year);
                mEndCalendar.set(Calendar.MONTH, monthOfYear);
                mEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        mTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermAddActivity.this,date, mEndCalendar.get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                        mEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mTermEnd.setText(sdf.format(mEndCalendar.getTime()));
    }

}
