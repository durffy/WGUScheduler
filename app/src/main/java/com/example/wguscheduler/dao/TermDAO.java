package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.TermEntity;

@Dao
public interface TermDAO {
    //CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    //READ
    @Query("SELECT * FROM term_table ORDER BY start_date")
    LiveData<TermEntity> getTerms();

    //UPDATE
    @Update
    void updateTerm(TermEntity term);

    //DELETE
    @Delete
    void deleteTerm(TermEntity term);

    @Query("DELETE FROM term_table")
    void deleteAll();
}
