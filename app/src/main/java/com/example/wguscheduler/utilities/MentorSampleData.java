package com.example.wguscheduler.utilities;

import com.example.wguscheduler.entities.MentorEntity;

import java.util.ArrayList;
import java.util.List;

public class MentorSampleData {
    public static List<MentorEntity> getMentors(){
        List<MentorEntity> mentors = new ArrayList<>();
        MentorEntity mentor;

        mentor = new MentorEntity(1, "James", "Franco", "111-111-1111", "jfranco@wgu.edu");
        mentors.add(mentor);
        mentor = new MentorEntity(2, "Tim", "Moti", "222-222-2222", "tmoti@wgu.edu");
        mentors.add(mentor);

        return mentors;
    }
}
