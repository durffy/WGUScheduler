package com.example.wguscheduler.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;
import com.example.wguscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermEditActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private TermEntity mTerm;
    private EditText mEditTermTitle,
            mEditStartDate,
            mEditEndDate;
    private Calendar mEndCalendar = Calendar.getInstance();
    private Calendar mStartCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadTermDetails();

        try {
            loadStartDatePicker();
            loadEndDatePicker();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
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

        mEditStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermEditActivity.this,date, mStartCalendar.get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                        mStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEditStartDate.setText(sdf.format(mStartCalendar.getTime()));

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

        mEditEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermEditActivity.this,date, mEndCalendar.get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                        mEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEditEndDate.setText(sdf.format(mEndCalendar.getTime()));
    }

    //CRUD
    //create
    //read
    private void loadTermDetails() {
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        mEditTermTitle = findViewById(R.id.edit_term_edit_title);
        mEditStartDate = findViewById(R.id.edit_term_edit_start);
        mEditEndDate = findViewById(R.id.edit_term_edit_end);

        if(mTermViewModel.getAllTerms() != null) {
            mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
                @Override
                public void onChanged(@Nullable final List<TermEntity> terms) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    for (TermEntity t : terms) {
                        if(t.getId() == getIntent().getIntExtra("termId", 0)){
                            mEditTermTitle.setText(t.getTitle());
                            mEditStartDate.setText(formatter.format(t.getStartDate()));
                            mEditEndDate.setText(formatter.format(t.getEndDate()));
                            mTerm = t;
                            mStartCalendar.setTime(mTerm.getStartDate());
                            mEndCalendar.setTime(mTerm.getEndDate());
                        }
                    }
                }
            });
        }
    }
    //update
    private void saveTerm() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        String title = mEditTermTitle.getText().toString();
        Date start = formatter.parse(mEditStartDate.getText().toString());
        Date end = formatter.parse(mEditEndDate.getText().toString());

        mTerm.setTitle(title);
        mTerm.setStartDate(start);
        mTerm.setEndDate(end);

        mTermViewModel.saveTerm(mTerm);
        finish();
        onSupportNavigateUp();
    }
    //delete
}

