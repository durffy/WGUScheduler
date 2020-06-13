package com.example.wguscheduler.utilities;

import com.example.wguscheduler.entities.TermEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TermSampleData {

    public static List<TermEntity> getTerms(){
        List<TermEntity> mTerms = new ArrayList<>();
        TermEntity term;

        term = new TermEntity(1, "Spring 2020", Date.valueOf("1/1/2020"), Date.valueOf("6/30/2020"));
        mTerms.add(term);

        term = new TermEntity(2, "Fall 2020", Date.valueOf("7/1/2020"), Date.valueOf("12/31/2020"));
        mTerms.add(term);

        term = new TermEntity(3, "Spring 2021", Date.valueOf("1/1/2020"), Date.valueOf("6/30/2020"));
        mTerms.add(term);

        term = new TermEntity(4, "Fall 2021", Date.valueOf("7/1/2020"), Date.valueOf("12/31/2020"));
        mTerms.add(term);

        return mTerms;
    }

}
