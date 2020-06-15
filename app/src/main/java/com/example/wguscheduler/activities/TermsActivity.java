package com.example.wguscheduler.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.wguscheduler.MainActivity;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.ui.TermAdapter;
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

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.wguscheduler.R;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private TermViewModel mTermViewModel;
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        //Add toolbar action items
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // add the recycler view for displaying the terms and initialize the view model
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_term);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //intent for activity switching
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, TermDetailsActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //observer changes to the terms
       if(mTermViewModel.getAllTerms() != null) {
           mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
               @Override
               public void onChanged(@Nullable final List<TermEntity> terms) {
                   // Update the cached copy of the words in the adapter.
                   termAdapter.setTerms(terms);
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
        mTermViewModel.addSampleData();
    }
}
