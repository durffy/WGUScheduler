package com.example.wguscheduler.utilities;

import android.util.Log;

import com.example.wguscheduler.entities.CourseEntity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseSampleData {

    private static final String TAG = "CourseSampleData";

    public static List<CourseEntity> getCourses() throws ParseException {

        List<CourseEntity> courses = new ArrayList<>();

        CourseEntity course;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        course = new CourseEntity(1,1,1, "Mobile Application Development – C196", "Completed", formatter.parse("01/02/2020"), formatter.parse("01/30/2020"));
        courses.add(course);
        Log.i(TAG, "getCourses(): "+ course.getCourse());


        course = new CourseEntity(2,1,2, "Data Management for Programmers – C192", "In Progress",formatter.parse("02/01/2020"), formatter.parse("02/30/2020"));
        courses.add(course);
        Log.i(TAG, "getCourses(): "+ course.getCourse());

        course = new CourseEntity(3,2,2, "IT Capstone Written Project – C769", "Plan to Take",formatter.parse("07/01/2021"), formatter.parse("08/30/2020"));
        courses.add(course);
        Log.i(TAG, "getCourses(): "+ course.getCourse());

        return courses;
    }
}
