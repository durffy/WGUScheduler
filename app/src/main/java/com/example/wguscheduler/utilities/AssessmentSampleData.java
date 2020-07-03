package com.example.wguscheduler.utilities;

import com.example.wguscheduler.entities.AssessmentEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AssessmentSampleData {
    public static List<AssessmentEntity> getAssessments() throws ParseException {
        List<AssessmentEntity> assessments = new ArrayList<>();
        AssessmentEntity assessment;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        assessment = new AssessmentEntity(1, 1, "Assessment",
                "Performance Based Exam", formatter.parse("01/30/2020"));
        assessments.add(assessment);

        assessment = new AssessmentEntity(2, 1, "Assessment",
                "Objective Based Exam", formatter.parse("02/28/2020"));
        assessments.add(assessment);

        assessment = new AssessmentEntity(3, 2, "Assessment",
                "Performance Based Exam", formatter.parse("07/30/2020"));
        assessments.add(assessment);

        return assessments;
    }
}
