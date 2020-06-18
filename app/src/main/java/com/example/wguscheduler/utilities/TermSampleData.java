package com.example.wguscheduler.utilities;

import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.TermEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class TermSampleData {

    public static List<TermEntity> getTerms() throws ParseException {
        List<TermEntity> terms = new ArrayList<>();

        TermEntity term;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");;

        term = new TermEntity(1, "Spring 2020",  formatter.parse("01/01/2020"), formatter.parse("06/30/2020"));
        terms.add(term);

        term = new TermEntity(2, "Fall 2020", formatter.parse("07/1/2020"), formatter.parse("12/31/2020"));
        terms.add(term);

        term = new TermEntity(3, "Spring 2021", formatter.parse("01/01/2021"), formatter.parse("06/30/2021"));
        terms.add(term);

        term = new TermEntity(4, "Fall 2021", formatter.parse("07/01/2021"), formatter.parse("12/31/2021"));
        terms.add(term);

        return terms;
    }

}
