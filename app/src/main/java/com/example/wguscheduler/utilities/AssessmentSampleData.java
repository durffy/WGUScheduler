package com.example.wguscheduler.utilities;

import com.example.wguscheduler.entities.AssessmentEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AssessmentSampleData {
    public static List<AssessmentEntity> getAssessments() throws ParseException {
        List<AssessmentEntity> assessments = new ArrayList<>();
        AssessmentEntity assessment;

        assessment = new AssessmentEntity(1, 1, "Assessment", "Performance Based Exam", "Performance");
        assessments.add(assessment);

        assessment = new AssessmentEntity(2, 1, "Assessment", "Objective Based Exam", "Objective");
        assessments.add(assessment);

        assessment = new AssessmentEntity(3, 2, "Assessment", "Performance Based Exam", "Performance");
        assessments.add(assessment);

        return assessments;
    }
}
