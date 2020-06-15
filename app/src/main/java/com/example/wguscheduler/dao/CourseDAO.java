package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> courses);

    @Query("SELECT * FROM course_table")
    LiveData<List<CourseEntity>> getCourses();

    @Update
    void updateCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);

}
