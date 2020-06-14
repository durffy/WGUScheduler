package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.MentorEntity;

@Dao
public interface MentorDAO {

    //CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MentorEntity mentor);

    //READ
    @Query("SELECT * FROM mentor_table")
    LiveData<MentorEntity> getAll();

    @Query("SELECT * FROM mentor_table WHERE id MATCH :mentorId")
    MentorEntity getMentor(int mentorId);

    //UPDATE
    @Update
    void update(MentorEntity mentor);

    //DELETE
    @Delete
    void delete(MentorEntity mentor);

}
