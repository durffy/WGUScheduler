package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguscheduler.database.SchedulerRepository;
import com.example.wguscheduler.entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private LiveData<List<TermEntity>> mAllTerms;
    private SchedulerRepository mSchedulerRepository;

    public TermViewModel(Application application){
        super(application);
        mSchedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAllTerms = mSchedulerRepository.getmAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    public void addSampleData() {
        mSchedulerRepository.addSampleData();
    }
}
