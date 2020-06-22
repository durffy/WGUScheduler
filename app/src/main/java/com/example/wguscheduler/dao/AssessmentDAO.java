package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {

    //create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessment);

    //read
    @Query("SELECT * FROM assessment_table")
    LiveData<List<AssessmentEntity>> getAssessments();

    //update
    @Update
    void updateAssessment(AssessmentEntity assessment);

    //delete
    @Delete
    void deleteAssessment(AssessmentEntity assessment);
}
