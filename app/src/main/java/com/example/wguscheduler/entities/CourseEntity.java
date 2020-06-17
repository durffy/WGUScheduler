package com.example.wguscheduler.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

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

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "status")
    private String mStatus;

    @ColumnInfo(name = "start_date")
    private Date mStartDate;

    @ColumnInfo(name = "end_date")
    private Date mEndDate;

    public CourseEntity(int mId, int mTermId, int mMentorId, String mTitle, String mStatus, Date mStartDate, Date mEndDate) {
        this.mId = mId;
        this.mTermId = mTermId;
        this.mMentorId = mMentorId;
        this.mTitle = mTitle;
        this.mStatus = mStatus;
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
    }

    public String getCourse() {

        String course = String.format("\n" +
                        "\nCourse ID: %s\n\r" +
                        "TermId: %s\n\r" +
                        "Mentor Id: %s\n\r" +
                        "Title: %s\n\r" +
                        "Status: %s\n\r" +
                        "Start Date: %s\n\r" +
                        "End Date: %s\n\r",
                mId, mTermId, mMentorId, mTitle, mStatus, mStartDate, mEndDate);
        return course;
    }

    public int getId() {
        return mId;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public int getTermId() {
        return mTermId;
    }

    public void setTermId(int TermId) {
        this.mTermId = TermId;
    }

    public int getMentorId() {
        return mMentorId;
    }

    public void setMentorId(int MentorId) {
        this.mMentorId = MentorId;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date StartDate) {
        this.mStartDate = StartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date EndDate) {
        this.mEndDate = EndDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
