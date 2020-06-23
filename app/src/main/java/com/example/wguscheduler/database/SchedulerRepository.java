package com.example.wguscheduler.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.MentorEntity;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.utilities.CourseSampleData;
import com.example.wguscheduler.utilities.MentorSampleData;
import com.example.wguscheduler.utilities.TermSampleData;
import com.example.wguscheduler.utilities.AssessmentSampleData;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SchedulerRepository {

    private static final String TAG = "SchedulerRepository";
    private static SchedulerRepository mSchedulerRepository;

    //data lists
    private LiveData<List<TermEntity>> mAllTerms;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<MentorEntity>> mAllMentors;

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
        mSchedulerDatabase = SchedulerDatabase.getDatabase(context);
        mAllTerms = mSchedulerDatabase.termDAO().getTerms();
        mAllCourses = mSchedulerDatabase.courseDAO().getCourses();
        mAllAssessments = mSchedulerDatabase.assessmentDAO().getAssessments();
        mAllMentors = mSchedulerDatabase.mentorDAO().getMentors();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }
    public LiveData<List<CourseEntity>> getAllCourses(){ return mAllCourses;}
    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }
    public LiveData<List<MentorEntity>> getAllMentors() {
        return mAllMentors;
    }

    public void addSampleData() {

        executor.execute(new Runnable(){
            @Override
            public void run(){

                try {
                    mSchedulerDatabase.termDAO().insertAll(TermSampleData.getTerms());
                    mSchedulerDatabase.courseDAO().insertAll(CourseSampleData.getCourses());
                    mSchedulerDatabase.assessmentDAO().insertAll(AssessmentSampleData.getAssessments());
                    mSchedulerDatabase.mentorDAO().insertAll(MentorSampleData.getMentors());
                    Log.i(TAG, "addSampleData().run()" + CourseSampleData.getCourses());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
