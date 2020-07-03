package com.example.wguscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

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

    @ColumnInfo(name = "notes")
    private String mNotes;

    @ColumnInfo(name = "scheduled_date")
    private Date mScheduledDate;


    public AssessmentEntity(int mId, int mCourseId, String mTitle, String mNotes, Date mScheduledDate) {
        this.mId = mId;
        this.mCourseId = mCourseId;
        this.mTitle = mTitle;
        this.mNotes = mNotes;
        this.mScheduledDate = mScheduledDate;
    }

    @Ignore
    public AssessmentEntity(int mCourseId, String mTitle, String mNotes, Date mScheduledDate) {
        this.mCourseId = mCourseId;
        this.mTitle = mTitle;
        this.mNotes = mNotes;
        this.mScheduledDate = mScheduledDate;
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

    public Date getScheduledDate() {
        return mScheduledDate;
    }

    public void setScheduledDate(Date mScheduledDate) {
        this.mScheduledDate = mScheduledDate;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }
}
