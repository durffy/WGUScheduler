package com.example.wguscheduler.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "term_table")
public class TermEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    private int mId;

    @ColumnInfo(name="title")
    private String mTitle;

    @ColumnInfo(name="start_date")
    private Date mStartDate;

    @ColumnInfo(name="end_date")
    private Date mEndDate;

    public TermEntity(int mId, String mTitle, Date mStartDate, Date mEndDate) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date mStartDate) {
        this.mStartDate = mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date mEndDate) {
        this.mEndDate = mEndDate;
    }
}
