package com.example.wguscheduler.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguscheduler.database.SchedulerRepository;
import com.example.wguscheduler.entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private SchedulerRepository mSchedulerRepository;
    private LiveData<List<CourseEntity>> mAllCourses;

    public CourseViewModel(Application application){
        super(application);
        mSchedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAllCourses = mSchedulerRepository.getAllCourses();
    }

    //create
    public void saveCourse(CourseEntity course){
        mSchedulerRepository.saveCourse(course);
    }

    //read
    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }



    public void addSampleData() {
        mSchedulerRepository.addSampleData();
    }

    public void deleteCourse(int courseId) {
        mSchedulerRepository.deleteCourse(courseId);
    }
}
