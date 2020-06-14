package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.AssessmentEntity;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(AssessmentEntity assessment);

    @Query("SELECT * FROM assessment_table")
    LiveData<AssessmentEntity> getAssessments();

    @Update
    void updateAssessment(AssessmentEntity assessment);

    @Delete
    void deleteAssessment(AssessmentEntity assessment);
}
