package com.example.wguscheduler.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentEditActivity extends AppCompatActivity {

    private AssessmentViewModel mAssessmentViewModel;
    private AssessmentEntity mAssessment;
    private EditText mEditAssessmentTitle,
            mEditAssessmentNotes,
            mEditAssessmentScheduledDate;
    private Calendar mScheduleCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAssessmentDetails();
        loadScheduleDatePicker();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveAssessment();
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

    private void loadScheduleDatePicker() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mScheduleCalendar.set(Calendar.YEAR, year);
                mScheduleCalendar.set(Calendar.MONTH, monthOfYear);
                mScheduleCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateScheduleLabel();

            }
        };

        mEditAssessmentScheduledDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentEditActivity.this, date, mScheduleCalendar.get(Calendar.YEAR), mScheduleCalendar.get(Calendar.MONTH),
                        mScheduleCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateScheduleLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEditAssessmentScheduledDate.setText(sdf.format(mScheduleCalendar.getTime()));
    }

    //C.R.U.D.
    //create
    //read
    private void loadAssessmentDetails() {
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        mEditAssessmentTitle = findViewById(R.id.edit_assessment_edit_title);
        mEditAssessmentScheduledDate = findViewById(R.id.edit_assessment_edit_scheduled_date);
        mEditAssessmentNotes = findViewById(R.id.edit_assessment_edit_notes);

        if(mAssessmentViewModel.getAllAssessments() != null) {
            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    for (AssessmentEntity a : assessments) {
                        if(a.getId()== getIntent().getIntExtra("assessmentId", 0)){
                            mEditAssessmentTitle.setText(a.getTitle());
                            mEditAssessmentScheduledDate.setText(formatter.format(a.getScheduledDate()));
                            mEditAssessmentNotes.setText(a.getNotes());
                            mAssessment = a;
                            mScheduleCalendar.setTime(mAssessment.getScheduledDate());
                        }
                    }
                }
            });
        }
    }
    //update
    private void saveAssessment() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        String title = mEditAssessmentTitle.getText().toString();
        Date scheduledDate = formatter.parse(mEditAssessmentScheduledDate.getText().toString());
        String notes = mEditAssessmentNotes.getText().toString();

        mAssessment.setTitle(title);
        mAssessment.setScheduledDate(scheduledDate);
        mAssessment.setNotes(notes);

        mAssessmentViewModel.saveAssessment(mAssessment);
        onSupportNavigateUp();
    }
    //delete
}
