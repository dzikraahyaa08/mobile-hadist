package com.example.mobilehadist.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mobilehadist.model.UserProgress;

@Dao
public interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE kitab = :kitab AND nomor = :nomor LIMIT 1")
    UserProgress getProgress(String kitab, int nomor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveProgress(UserProgress progress);

    @Query("SELECT COUNT(*) FROM user_progress WHERE isPassed = 1")
    int getPassedCount();
}
