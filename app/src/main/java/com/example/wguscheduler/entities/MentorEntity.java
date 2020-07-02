package com.example.wguscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "mentor_table")
public class MentorEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "first_name")
    private String mFirstName;

    @ColumnInfo(name = "last_name")
    private String mLastName;

    @ColumnInfo(name = "phone")
    private String mPhone;

    @ColumnInfo(name = "email")
    private String mEmail;

    public MentorEntity(int mId, String mFirstName, String mLastName, String mPhone, String mEmail) {
        this.mId = mId;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
    }

    @Ignore
    public MentorEntity(String mFirstName, String mLastName, String mPhone, String mEmail) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }


    public String getMentor() {
        String mentor = String.format("\n" +
                        "\nMentorId: %s\n\r" +
                        "FirstName: %s\n\r" +
                        "LastName Id: %s\n\r" +
                        "Phone: %s\n\r" +
                        "Email: %s\n\r",
                        mId, mFirstName, mLastName, mPhone, mEmail);
        return mentor;
    }
}
