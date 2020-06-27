package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguscheduler.database.SchedulerRepository;
import com.example.wguscheduler.entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private SchedulerRepository mSchedulerRepository;
    private LiveData<List<TermEntity>> mAllTerms;

    public TermViewModel(Application application){
        super(application);
        mSchedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAllTerms = mSchedulerRepository.getAllTerms();
    }

    //create
    public void saveTerm(TermEntity term){
        mSchedulerRepository.saveTerm(term);
    }

    //read
    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    public void addSampleData() {
        mSchedulerRepository.addSampleData();
    }
}
