package com.example.wguscheduler.activities;

import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.List;

public class TermEditActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private EditText mEditTermTitle,
            mEditStartDate,
            mEditEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadTermDetails();


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
                        if(t.getId()== getIntent().getIntExtra("termId", 0)){
                            mEditTermTitle.setText(t.getTitle());
                            mEditStartDate.setText(formatter.format(t.getStartDate()));
                            mEditEndDate.setText(formatter.format(t.getEndDate()));
                        }
                    }
                }
            });
        }
    }
    //update

    //delete
}

