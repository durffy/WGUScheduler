package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguscheduler.database.SchedulerRepository;
import com.example.wguscheduler.entities.MentorEntity;

import java.util.ArrayList;
import java.util.List;

public class MentorViewModel extends AndroidViewModel {

    private SchedulerRepository mSchedulerRepository;
    private LiveData<List<MentorEntity>> mAllMentors;

    public MentorViewModel(@NonNull Application application) {
        super(application);
        mSchedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAllMentors = mSchedulerRepository.getAllMentors();
    }

    public LiveData<List<MentorEntity>> getAllMentors(){
        return mAllMentors;
    }

    public void addSampleData(){
        mSchedulerRepository.addSampleData();
    }

    public void saveMentor(MentorEntity mMentor) {
        mSchedulerRepository.saveMentor(mMentor);
    }
}
