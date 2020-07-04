package com.example.wguscheduler.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wguscheduler.dao.AssessmentDAO;
import com.example.wguscheduler.dao.CourseDAO;
import com.example.wguscheduler.dao.MentorDAO;
import com.example.wguscheduler.dao.TermDAO;
import com.example.wguscheduler.entities.AssessmentEntity;
import com.example.wguscheduler.entities.CourseEntity;
import com.example.wguscheduler.entities.MentorEntity;
import com.example.wguscheduler.entities.TermEntity;
import com.example.wguscheduler.utilities.Converters;

@Database(entities = {TermEntity.class,
        AssessmentEntity.class,
        CourseEntity.class,
        MentorEntity.class},
        version = 7)
@TypeConverters({Converters.class})
public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract CourseDAO courseDAO();
    public abstract MentorDAO mentorDAO();
    public abstract TermDAO termDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile SchedulerDatabase INSTANCE;

    static SchedulerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchedulerDatabase.class,
                            "scheduler_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //open the database
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            //new PopulateSchedulerDatabase(INSTANCE).execute();
        }
    };
}
