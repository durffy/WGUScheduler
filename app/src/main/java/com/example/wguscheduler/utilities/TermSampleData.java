package com.example.wguscheduler.utilities;

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
        List<TermEntity> mTerms = new ArrayList<>();

        TermEntity term;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        term = new TermEntity(1, "Spring 2020",  formatter.parse("1/1/2020"), formatter.parse("6/30/2020"));
        mTerms.add(term);

        term = new TermEntity(2, "Fall 2020", formatter.parse("7/1/2020"), formatter.parse("12/31/2020"));
        mTerms.add(term);

        term = new TermEntity(3, "Spring 2021", formatter.parse("1/1/2020"), formatter.parse("6/30/2020"));
        mTerms.add(term);

        term = new TermEntity(4, "Fall 2021", formatter.parse("7/1/2020"), formatter.parse("12/31/2020"));
        mTerms.add(term);

        return mTerms;
    }

}
