package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguscheduler.entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private LiveData<List<TermEntity>> mAllTerms;

    public TermViewModel(Application application){
        super(application);
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }
}
