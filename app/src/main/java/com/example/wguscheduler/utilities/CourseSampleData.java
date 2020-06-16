package com.example.wguscheduler.utilities;

import com.example.wguscheduler.entities.CourseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseSampleData {
    public static List<CourseEntity> getCourses() throws ParseException {
        List<CourseEntity> courses = new ArrayList<>();

        CourseEntity course;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        course = new CourseEntity(1,1,1, "Mobile Application Development – C196", formatter.parse("01/01/2020"), formatter.parse("01/30/2020"));
        courses.add(course);

        course = new CourseEntity(2,1,2, "Data Management for Programmers – C192", formatter.parse("02/01/2020"), formatter.parse("02/30/2020"));
        courses.add(course);

        return courses;
    }
}
