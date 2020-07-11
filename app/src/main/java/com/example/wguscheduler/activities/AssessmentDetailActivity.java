package com.example.wguscheduler.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wguscheduler.R;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.utilities.NotificationReceiver;
import com.example.wguscheduler.viewmodel.AssessmentViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AssessmentDetailActivity extends AppCompatActivity{

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private AssessmentViewModel mAssessmentViewModel;
    private AssessmentEntity mAssessment;
    private TextView textViewAssessmentTitle,
        textViewAssessmentNotes,
        textViewAssessmentScheduledDate;
    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAssessmentDetails();

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
        inflater.inflate(R.menu.menu_sharing, menu);
        MenuItem item = menu.findItem(R.id.item_share);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.item_delete){
            deleteAssessment(getIntent().getIntExtra("assessmentId",0));
            return true;
        }else if( id == R.id.item_edit){
            editAssessment();
        }else if(id == R.id.item_notification){
            notification();
        }

        return super.onOptionsItemSelected(item);
    }

    private void notification() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(AssessmentDetailActivity.this, NotificationReceiver.class);
        intent.putExtra("key", "Scheduled Assessment: "+ formatter.format(mAssessment.getScheduledDate()));
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        cal.setTime(mAssessment.getScheduledDate());
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , sender);
    }

    //C.R.U.D.
    //create
    //read
    private void loadAssessmentDetails() {
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        textViewAssessmentTitle = findViewById(R.id.text_assessment_title);
        textViewAssessmentScheduledDate = findViewById(R.id.text_assessment_scheduled_date_output);
        textViewAssessmentNotes = findViewById(R.id.text_assessment_notes_output);

        if(mAssessmentViewModel.getAllAssessments() != null) {
            mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    for (AssessmentEntity a : assessments) {
                        if(a.getId()== getIntent().getIntExtra("assessmentId", 0)){
                            textViewAssessmentTitle.setText(a.getTitle());
                            textViewAssessmentScheduledDate.setText(formatter.format(a.getScheduledDate()));
                            textViewAssessmentNotes.setText(a.getNotes());
                            mAssessment = a;
                        }
                    }
                }
            });
        }
    }
    //update
    private void editAssessment() {
        Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentEditActivity.class);
        intent.putExtra("assessmentId", getIntent().getIntExtra("assessmentId", 0));
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        onSupportNavigateUp();
    }
    //delete
    private void deleteAssessment(int assessmentId) {
        //build the alert message
        AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentDetailActivity.this);
        builder.setTitle("Assessment Delete");
        builder.setMessage("Deleting this Assessment, Do you want to proceed with the delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAssessmentViewModel.deleteAssessment(assessmentId);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
