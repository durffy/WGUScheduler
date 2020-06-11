package com.example.wguscheduler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler.entities.CourseEntity;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Query("SELECT * FROM course_table WHERE term_id LIKE :termId")
    LiveData<CourseEntity> getTermCourses(int termId);

    @Update
    void updateCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);

}
