package com.example.wguscheduler.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "course_table")
public class CourseEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @ForeignKey(entity = TermEntity.class, parentColumns = "id", childColumns = "id")
    @ColumnInfo(name = "term_id")
    private int mTermId;

    @ForeignKey(entity = MentorEntity.class, parentColumns = "id", childColumns = "id")
    @ColumnInfo(name = "mentor_id")
    private int mMentorId;

    @ColumnInfo(name = "start_date")
    private Date mStartDate;

    @ColumnInfo(name = "end_date")
    private Date mEndDate;



}
