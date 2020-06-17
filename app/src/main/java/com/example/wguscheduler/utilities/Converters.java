package com.example.wguscheduler.utilities;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    private static final String TAG = "Converters";

    @TypeConverter
    public static Date fromTimestamp(Long value) {

        Log.d(TAG, String.format("fromTimestamp(): \n\r" +
                "Date: %s\n\r" +
                "TimeStamp: %s", new Date(value), value));
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        Log.d(TAG, String.format("dateToTimestamp():\n\r" +
                "Date: %s\n\r" +
                "TimeStamp: %s", date,  date.getTime()));
        return date == null ? null : date.getTime();
    }
}
