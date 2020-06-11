package com.example.wguscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "id")
    private int mCourseId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "type")
    private String mType;

}
