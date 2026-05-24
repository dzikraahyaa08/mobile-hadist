package com.example.mobilehadist.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.mobilehadist.dao.HadithDao;
import com.example.mobilehadist.dao.UserScoreDao;
import com.example.mobilehadist.dao.UserProgressDao;
import com.example.mobilehadist.model.Hadith;
import com.example.mobilehadist.model.UserScore;
import com.example.mobilehadist.model.UserProgress;

@Database(entities = {Hadith.class, UserScore.class, UserProgress.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract HadithDao hadithDao();
    public abstract UserScoreDao userScoreDao();
    public abstract UserProgressDao userProgressDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hadith_db")
                            .createFromAsset("databases/data.sqlite")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
