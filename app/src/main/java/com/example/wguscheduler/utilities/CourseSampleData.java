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
    private static final String DEFAULT_NOTES = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit. Maecenas tincidunt lectus ex, " + "at gravida lorem mattis in. Quisque nec aliquam " +
            "risus. Integer non erat ultrices, blandit sem at, convallis nulla. Donec eget maximus metus. " +
            "Quisque imperdiet, eros sit amet varius placerat, dolor sem semper massa, at placerat " +
            "felis dui at arcu. Nunc sed justo neque. Cras massa nunc, semper ac risus id, imperdiet " +
            "convallis turpis. Nullam turpis massa, consequat a gravida sed, mollis vel enim. Sed " +
            "eget sollicitudin elit. Phasellus egestas hendrerit tellus sed imperdiet. Praesent " +
            "lectus quam, pretium placerat metus nec, sodales semper ligula. Ut vel facilisis leo, " +
            "gravida congue eros. Nunc elementum lobortis lacus vel ultrices. Suspendisse luctus " +
            "laoreet consectetur. Duis tempor ipsum sit amet ante vestibulum, sed tristique massa " +
            "lacinia. Etiam sit amet magna sem.";

    public static List<CourseEntity> getCourses() throws ParseException {

        List<CourseEntity> courses = new ArrayList<>();

        CourseEntity course;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        course = new CourseEntity(1,1, "Mobile Application Development – C196", "Completed", formatter.parse("01/02/2020"), formatter.parse("01/30/2020"), DEFAULT_NOTES);
        courses.add(course);
        Log.d(TAG, "getCourses(): "+ formatter.parse("01/02/2020"));
        Log.d(TAG, "getCourses(): "+ course.getCourse());


        course = new CourseEntity(1,2, "Data Management for Programmers – C192", "In Progress",formatter.parse("02/01/2020"), formatter.parse("02/30/2020"), DEFAULT_NOTES);
        courses.add(course);
        Log.d(TAG, "getCourses(): "+ course.getCourse());

        course = new CourseEntity(2,2, "IT Capstone Written Project – C769", "Plan to Take",formatter.parse("07/01/2021"), formatter.parse("08/30/2020"), DEFAULT_NOTES);
        courses.add(course);
        Log.d(TAG, "getCourses(): "+ course.getCourse());

        return courses;
    }
}
