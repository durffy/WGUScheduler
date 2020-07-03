package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguscheduler.database.SchedulerRepository;
import com.example.wguscheduler.entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private SchedulerRepository mSchedulerRepository;
    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        mSchedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAllAssessments = mSchedulerRepository.getAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }

    public void saveAssessment(AssessmentEntity assessment) {
        mSchedulerRepository.saveAssessment(assessment);
    }
}
