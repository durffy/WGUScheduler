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

    public AssessmentEntity(int mId, int mCourseId, String mTitle, String mDescription, String mType) {
        this.mId = mId;
        this.mCourseId = mCourseId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mType = mType;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getCourseId() {
        return mCourseId;
    }

    public void setCourseId(int mCourseId) {
        this.mCourseId = mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
