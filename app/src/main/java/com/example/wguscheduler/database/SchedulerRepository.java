package com.example.wguscheduler.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.utilities.TermSampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SchedulerRepository {

    private static SchedulerRepository mSchedulerRepository;

    private LiveData<List<TermEntity>> mAllTerms;
    private SchedulerDatabase mSchedulerDatabase;
    //executor to run only one thread, in order
    private Executor executor = Executors.newSingleThreadExecutor();

    //singleton creation of the scheduler repository
    public static SchedulerRepository getInstance(Context context){
        if(mSchedulerRepository == null){
            synchronized (SchedulerRepository.class) {
                if(mSchedulerRepository == null) {
                    mSchedulerRepository = new SchedulerRepository(context);
                }
            }
        }
        return mSchedulerRepository;
    }


    private SchedulerRepository(Context context){
        mAllTerms = mSchedulerDatabase.termDAO().getTerms();
        mSchedulerDatabase = SchedulerDatabase.getDatabase(context);
    }

    public LiveData<List<TermEntity>> getmAllTerms() {
        return mAllTerms;
    }

    public void addSampleData() {

        executor.execute(new Runnable(){
            @Override
            public void run(){

                mSchedulerDatabase.termDAO().insertAll(TermSampleData.getTerms());
            }
        });

    }
}
