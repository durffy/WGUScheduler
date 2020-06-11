package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wguscheduler.entities.TermEntity;

import java.util.List;

public class TermViewModel extends ViewModel {
    private LiveData<List<TermEntity>> mAllTerms;

    public TermViewModel(Application application){
        super(application);
    }

    public LiveData<Object> getAllTerms() {
        return mAllTerms;
    }
}
