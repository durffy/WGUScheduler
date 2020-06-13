package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {
    //CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    //READ
    @Query("SELECT * FROM term_table ORDER BY start_date")
    LiveData<List<TermEntity>> getTerms();

    //UPDATE
    @Update
    void updateTerm(TermEntity term);

    //DELETE
    @Delete
    void deleteTerm(TermEntity term);

    @Query("DELETE FROM term_table")
    void deleteAll();


}
